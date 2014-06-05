package com.spark.android.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MCategoriesPage implements Serializable {
    private List<MCategory> categories;

    public List<MCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MCategory> categories) {
        this.categories = categories;
    }
}
