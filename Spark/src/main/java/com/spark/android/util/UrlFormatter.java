package com.spark.android.util;

/**
 * Created by danielclayton on 6/4/14.
 */
public class UrlFormatter {
    /**
     * Returns an API URL for a retailer to be used for requesting JSON
     * data from different retailers based on the retailer name.
     *
     * @param retailerName name of retailer
     * @return retailer API URL
     */
    public static String getRetailerApiUrl(String retailerName) {
        String url = "";

        switch (retailerName) {
            case "Ace Hardware":
                url = "https://acehardwareapi.uat.bbhosted.com";
                break;
            case "American Eagle":
                url = "https://aeapi.uat.bbhosted.com";
                break;
            case "Eastern Mountain Sports":
                url = "https://emsapi.uat.bbhosted.com";
                break;
            case "Sports Authority":
                url = "https://sportsauthorityapi.uat.bbhosted.com";
                break;
            case "Toys \"R\" Us":
                url = "https://toysrus-demo-api.herokuapp.com";
                break;
            case "Vitacost":
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
        }

        return url;
    }

    /**
     * Returns a base name of a retailer to be used by the WebView to
     * determine whether or not the URL loading should be handled by
     * the WebView or externally in a browser.
     *
     * @param retailerName name of retailer
     * @return retailer base name
     */
    public static String getRetailerBaseName(String retailerName) {
        String baseName = "";

        switch (retailerName) {
            case "Ace Hardware":
                baseName = "acehardware";
                break;
            case "American Eagle":
                baseName = "ae";
                break;
            case "Eastern Mountain Sports":
                baseName = "ems";
                break;
            case "Sports Authority":
                baseName = "sportsauthority";
                break;
            case "Toys \"R\" Us":
                baseName = "toysrus";
                break;
            case "Vitacost":
                baseName = "vitacost";
                break;
        }

        return baseName;
    }

    /**
     * Returns a formatted URL link for a retailer to be used to
     * present a deals page. It also handles times when the URL
     * provided is already a properly formatted link instead
     * of a relative href.
     *
     * @param retailerName name of retailer
     * @param url          link from JSON
     * @return formatted URL link for a retailer
     */
    public static String formatDealsPageUrl(String retailerName, String url) {
        if (url.contains("http") || url.contains("www.")) {
            return url;
        }

        switch (retailerName) {
            case "Vitacost":
                url = "http://m.vitacost.com" + url;
                break;
        }

        return url;
    }

    /**
     * Returns a formatted URL link for a retailer to be used to
     * present a product page.
     *
     * @param retailerName name of retailer
     * @param productId    product ID from JSON
     * @return formatted URL link for a retailer
     */
    public static String formatProductPageUrl(String retailerName, String productId) {
        String url = "";

        switch (retailerName) {
            case "Ace Hardware":
                url = "http://m.acehardware.com/product/index.jsp?productId=" + productId;
                break;
            case "American Eagle":
                if (productId.contains("web~")) {
                    productId = productId.replace("web~", "");
                    url = "http://m.ae.com/web/browse/product.jsp?productId=" + productId;
                } else if (productId.contains("factory~")) {
                    productId = productId.replace("factory~", "");
                    url = "http://m.ae.com/factory/browse/product.jsp?productId=" + productId;
                }

                break;
            case "Eastern Mountain Sports":
                url = "http://m.ems.com/products/" + productId;
                break;
            case "Sports Authority":
                url = "http://m.sportsauthority.com/products/" + productId;
                break;
            case "Toys \"R\" Us":
                url = "http://m.toysrus.com/product/index.jsp?productId=" + productId;
                break;
            case "Vitacost":
                url = "http://m.vitacost.com/products/" + productId;
                break;
        }

        return url;
    }

    /**
     * Returns a formatted URL link for a retailer to be used to
     * fetch images with relative path references instead of
     * absolute.
     *
     * @param retailerName name of retailer
     * @param url          link from JSON
     * @return formatted URL link for a retailer
     */
    public static String formatProductImageUrl(String retailerName, String url) {
        if (url.contains("http") || url.contains("www.")) {
            return url;
        }

        switch (retailerName) {
            case "Toys \"R\" Us":
                url = "http://m.toysrus.com" + url;
                break;
        }

        return url;
    }
}
