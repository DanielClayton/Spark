package com.spark.android.model;

import java.io.Serializable;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MCategory implements Serializable {
    private String href;
    private String title;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
