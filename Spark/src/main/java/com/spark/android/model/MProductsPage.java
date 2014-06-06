package com.spark.android.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danielclayton on 6/4/14.
 */
public class MProductsPage implements Serializable {
    private List<MProduct> products;

    public List<MProduct> getProducts() {
        return products;
    }

    public void setProducts(List<MProduct> products) {
        this.products = products;
    }

    public MProductsPage() {
    }
}
