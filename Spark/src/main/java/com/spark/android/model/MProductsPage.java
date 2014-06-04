package com.spark.android.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by danielclayton on 6/4/14.
 */
public class MProductsPage implements Serializable {
    private ArrayList<MProduct> products;

    public ArrayList<MProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<MProduct> products) {
        this.products = products;
    }

    public MProductsPage() {
    }
}
