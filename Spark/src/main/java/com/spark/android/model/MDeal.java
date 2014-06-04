package com.spark.android.model;

import java.util.ArrayList;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MDeal {
    private String image;
    private ArrayList<String> bullets;
    private String href;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<String> bullets) {
        this.bullets = bullets;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
