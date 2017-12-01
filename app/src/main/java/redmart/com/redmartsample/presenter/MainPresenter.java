package redmart.com.redmartsample.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import redmart.com.redmartsample.R;
import redmart.com.redmartsample.app.AppApplication;
import redmart.com.redmartsample.app.AppConstants;
import redmart.com.redmartsample.helper.VolleyRequestHelper;
import redmart.com.redmartsample.model.ProductModel;
import redmart.com.redmartsample.view.activities.MainView;

/**
 * Created by pratim on 25/11/17.
 */
public class MainPresenter {
    private Context context;
    private MainView mainView;

    private VolleyRequestHelper volleyRequestHelper;

    private VolleyRequestHelper.OnRequestCompletedListener requestCompletedListener = new VolleyRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status,
                                       String response, String errorMessage) {
            if(mainView!=null) {
                mainView.hideProgress();
                if (status) {
                    Gson gson = new Gson();
                    ProductModel productModel = gson.fromJson(response, new TypeToken<ProductModel>() {
                    }.getType());
                    mainView.updateData(productModel);
                }
            }

        }
    };

    public MainPresenter(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
        volleyRequestHelper = new VolleyRequestHelper(context, requestCompletedListener);
    }

    public void fetchData(int pageNum) {
        mainView.showProgress();
        String webUrl = String.format(context.getString(R.string.get_products_list), pageNum);
        volleyRequestHelper.getJsonObject(AppConstants.REQUEST_PRODUCTS, webUrl, Request.Method.GET, null, false);
    }

    public void onDestroy() {
        mainView = null;
        AppApplication.getInstance().cancelPendingRequests(AppConstants.REQUEST_PRODUCTS);
    }
}
