package redmart.com.redmartsample.model;

import java.util.List;

/**
 * Created by pratim on 26/11/17.
 */
public class ProductModel {

    private List<Products> products;
    private int page;

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
