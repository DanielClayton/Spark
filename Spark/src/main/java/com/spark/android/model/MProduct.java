package com.spark.android.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MProduct implements Serializable {
    private String title;
    private String id;
    private List<MImage> images;

    public List<MImage> getImages() {
        return images;
    }

    public void setImages(List<MImage> images) {
        this.images = images;
    }

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
