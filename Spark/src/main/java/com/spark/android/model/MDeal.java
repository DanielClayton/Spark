package com.spark.android.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MDeal implements Serializable {
    private String image;
    private List<String> bullets;
    private String href;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getBullets() {
        return bullets;
    }

    public void setBullets(List<String> bullets) {
        this.bullets = bullets;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
