package redmart.com.redmartsample.view.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import redmart.com.redmartsample.R;
import redmart.com.redmartsample.app.AppApplication;
import redmart.com.redmartsample.model.ProductDetail;
import redmart.com.redmartsample.presenter.DetailPresenter;

/**
 * Created by pratim on 26/11/17.
 */
public class DetailActivity extends AppCompatActivity implements DetailView {
    public static final String PRODUCT_ID = "product_id";

    TextView productNameTxt;
    TextView productDescTxt;
    NetworkImageView networkImageView;
    int productId;

    DetailPresenter detailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.product_detail));

        productId = getIntent().getIntExtra(PRODUCT_ID, 0);

        networkImageView = (NetworkImageView) findViewById(R.id.product_img);
        productNameTxt = (TextView) findViewById(R.id.product_name);
        productDescTxt = (TextView) findViewById(R.id.product_desc);

        detailPresenter = new DetailPresenter(this, this);
        detailPresenter.fetchData(productId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void updateData(final ProductDetail productDetail) {
        productNameTxt.setText(productDetail.getProduct().getTitle());
        productDescTxt.setText(productDetail.getProduct().getDesc());
        ImageLoader imageLoader = AppApplication.getInstance().getImageLoader();
        String imgUrl = getString(R.string.thumbnail_url) + productDetail.getProduct().getImg().getName();
        networkImageView.setImageUrl(imgUrl, imageLoader);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
