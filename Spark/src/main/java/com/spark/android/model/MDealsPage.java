package com.spark.android.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MDealsPage implements Serializable {
    private List<MDeal> deals;

    public List<MDeal> getDeals() {
        return deals;
    }

    public void setDeals(List<MDeal> deals) {
        this.deals = deals;
    }

    public MDealsPage() {
    }
}
