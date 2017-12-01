package redmart.com.redmartsample.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import redmart.com.redmartsample.R;
import redmart.com.redmartsample.app.AppApplication;
import redmart.com.redmartsample.helper.VolleyRequestHelper;
import redmart.com.redmartsample.model.ProductModel;
import redmart.com.redmartsample.model.Products;

/**
 * Created by pratim on 26/11/17.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Context context;
    private List<Products> products;
    private onClickItem clickItem;

    public interface onClickItem{
        void onClick(int position);
    }

    public ProductAdapter(Context context, List<Products> productList, onClickItem clickItem) {
        this.products = productList;
        this.context = context;
        this.clickItem = clickItem;
    }

    public void setProductList(List<Products> productList) {
        this.products = productList;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView productPhotoImg;
        TextView productNameTxt;
        TextView productPriceTxt;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            productPhotoImg = (NetworkImageView) itemView.findViewById(R.id.product_img);
            productNameTxt = (TextView) itemView.findViewById(R.id.product_name);
            productPriceTxt = (TextView) itemView.findViewById(R.id.product_price);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int)v.getTag();
                clickItem.onClick(pos);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Products product = products.get(position);

        holder.productNameTxt.setText(product.getTitle());
        holder.productPriceTxt.setText(String.valueOf(product.getPricing().getPrice()));
        ImageLoader imageLoader = AppApplication.getInstance().getImageLoader();
        String imgUrl = context.getString(R.string.thumbnail_url) + product.getImg().getName();
        holder.productPhotoImg.setImageUrl(imgUrl, imageLoader);
        holder.cv.setTag(position);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
