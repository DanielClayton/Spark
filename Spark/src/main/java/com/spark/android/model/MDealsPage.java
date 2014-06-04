package com.spark.android.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MDealsPage {
    private ArrayList<JsonObject> products;
    private ArrayList<MDeal> deals;

    public ArrayList<MDeal> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<MDeal> deals) {
        this.deals = deals;
    }

    public ArrayList<JsonObject> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<JsonObject> products) {
        this.products = products;
    }

    public MDealsPage() {
    }
}
