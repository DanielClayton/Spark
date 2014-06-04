package com.spark.android.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MProduct implements Serializable {
    private String title;
    private String id;

    public String getProductId() {
        return id;
    }

    public void setProductId(String productId) {
        this.id = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
