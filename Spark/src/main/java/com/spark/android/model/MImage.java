package com.spark.android.model;

import java.io.Serializable;

/**
 * Created by danielclayton on 6/5/14.
 */
public class MImage implements Serializable {
    private String thumb;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
