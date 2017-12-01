package redmart.com.redmartsample.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redmart.com.redmartsample.R;
import redmart.com.redmartsample.app.AppApplication;
import redmart.com.redmartsample.app.AppConstants;
import redmart.com.redmartsample.helper.VolleyRequestHelper;
import redmart.com.redmartsample.model.ProductDetail;
import redmart.com.redmartsample.model.ProductModel;
import redmart.com.redmartsample.view.activities.DetailView;
import redmart.com.redmartsample.view.activities.MainView;

/**
 * Created by pratim on 26/11/17.
 */
public class DetailPresenter {
    private Context context;
    private DetailView detailView;

    private VolleyRequestHelper volleyRequestHelper;

    private VolleyRequestHelper.OnRequestCompletedListener requestCompletedListener = new VolleyRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status,
                                       String response, String errorMessage) {
            if (detailView != null) {
                detailView.hideProgress();
                if (status) {
                    Gson gson = new Gson();
                    ProductDetail productDetail = gson.fromJson(response, new TypeToken<ProductDetail>() {
                    }.getType());
                    detailView.updateData(productDetail);
                }
            }

        }
    };

    public DetailPresenter(Context context, DetailView detailView) {
        this.context = context;
        this.detailView = detailView;
        volleyRequestHelper = new VolleyRequestHelper(context, requestCompletedListener);
    }

    public void fetchData(int productId) {
        detailView.showProgress();
        String webUrl = String.format(context.getString(R.string.get_product_detail), productId);
        volleyRequestHelper.getJsonObject(AppConstants.REQUEST_PRODUCT_DETAIL, webUrl, Request.Method.GET, null, false);
    }

    public void onDestroy() {
        detailView = null;
        AppApplication.getInstance().cancelPendingRequests(AppConstants.REQUEST_PRODUCT_DETAIL);
    }
}