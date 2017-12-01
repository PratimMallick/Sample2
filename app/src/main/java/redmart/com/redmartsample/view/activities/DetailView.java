package redmart.com.redmartsample.view.activities;

import redmart.com.redmartsample.model.ProductDetail;
import redmart.com.redmartsample.model.ProductModel;

/**
 * Created by pratim on 26/11/17.
 */
public interface DetailView {
    void showProgress();
    void hideProgress();
    void updateData(ProductDetail productDetail);
}
