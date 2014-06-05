package com.spark.android.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MDealsPage implements Serializable {
    private ArrayList<MDeal> deals;

    public ArrayList<MDeal> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<MDeal> deals) {
        this.deals = deals;
    }

    public MDealsPage() {
    }
}
