package redmart.com.redmartsample.view.activities;

import java.util.List;

import redmart.com.redmartsample.model.ProductModel;

/**
 * Created by pratim on 25/11/17.
 */
public interface MainView {

    void showProgress();
    void hideProgress();
    void updateData(ProductModel followingModelList);
}
