package redmart.com.redmartsample.view.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import redmart.com.redmartsample.R;
import redmart.com.redmartsample.model.ProductModel;
import redmart.com.redmartsample.model.Products;
import redmart.com.redmartsample.presenter.MainPresenter;
import redmart.com.redmartsample.utils.VerticalSpaceItemDecoration;
import redmart.com.redmartsample.view.adapters.ProductAdapter;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final int VERTICAL_ITEM_SPACE = 8;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MainPresenter mainPresenter;
    List<Products> productsList;
    int pageNum;
    ProductAdapter productAdapter;

    private int visibleThreshold = 1;
    int totalItemCount;
    int lastVisibleItem;
    boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.home_following_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.fetchData(pageNum);
            }
        });
        // Setup the recycler view
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    mainPresenter.fetchData(++pageNum);
                }
                //isLoading = true;
            }
        });

        mainPresenter = new MainPresenter(this, this);

        productsList = new ArrayList<>();
        productAdapter = new ProductAdapter(MainActivity.this, productsList, new ProductAdapter.onClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.PRODUCT_ID, productsList.get(position).getId());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(productAdapter);

        mainPresenter.fetchData(pageNum);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void showProgress() {
        isLoading = true;
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        isLoading = false;
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void updateData(ProductModel productModel) {

        if (productModel != null) {
            //ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, productModel.getProducts());
            //recyclerView.setAdapter(productAdapter);
            int itemCount = productModel.getProducts().size();
            int startPosition = productsList.size();
            productsList.addAll(productModel.getProducts());
            pageNum = productModel.getPage();
            productAdapter.setProductList(productsList);
            productAdapter.notifyItemRangeInserted(startPosition, itemCount);
            //productAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        productsList = null;
        pageNum = 0;
        mainPresenter.onDestroy();
        super.onDestroy();
    }

}
