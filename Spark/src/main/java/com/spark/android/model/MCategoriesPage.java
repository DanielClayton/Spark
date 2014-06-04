package com.spark.android.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MCategoriesPage implements Serializable {
    private ArrayList<MCategory> categories;

    public ArrayList<MCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<MCategory> categories) {
        this.categories = categories;
    }
}
