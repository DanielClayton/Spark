package com.spark.android.util;

/**
 * Created by danielclayton on 6/4/14.
 */
public class UrlFormatter {
    public static String getRetailerApiUrl(String retailerName) {
        String url = "";

        switch (retailerName) {
            case "Ace Hardware":
                url = "https://acehardwareapi.uat.bbhosted.com";
                break;
            case "American Eagle":
                url = "https://aeapi.uat.bbhosted.com";
                break;
            case "Vitacost":
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
            default:
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
        }

        return url;
    }

    public static String formatDealsPageUrl(String retailerName, String href) {
        String url = "";

        if (href.contains("http") || href.contains("www.")) {
            return href;
        }

        switch (retailerName) {
            case "Ace Hardware":
                url = "https://acehardwareapi.uat.bbhosted.com";
                break;
            case "American Eagle":
                url = "https://aeapi.uat.bbhosted.com";
                break;
            case "Vitacost":
                url = "http://m.vitacost.com" + href;
                break;
            default:
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
        }

        return url;
    }

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
            case "Vitacost":
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
            default:
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
        }

        return url;
    }
}
