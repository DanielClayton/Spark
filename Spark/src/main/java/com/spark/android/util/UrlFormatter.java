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

    public static String getRetailerBaseUrl(String retailerName) {
        String url = "";

        switch (retailerName) {
            case "Ace Hardware":
                url = "acehardware";
                break;
            case "American Eagle":
                url = "ae";
                break;
            case "Eastern Mountain Sports":
                url = "ems";
                break;
            case "Sports Authority":
                url = "sportsauthority";
                break;
            case "Toys \"R\" Us":
                url = "toysrus";
                break;
            case "Vitacost":
                url = "vitacost";
                break;
        }

        return url;
    }

    public static String formatDealsPageUrl(String retailerName, String href) {
        if (href.contains("http") || href.contains("www.")) {
            return href;
        }

        switch (retailerName) {
            case "Vitacost":
                href = "http://m.vitacost.com" + href;
                break;
        }

        return href;
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

    public static String formatProductImageUrl(String retailerName, String href) {
        if (href.contains("http") || href.contains("www.")) {
            return href;
        }

        switch (retailerName) {
            case "Toys \"R\" Us":
                href = "http://m.toysrus.com" + href;
                break;
        }

        return href;
    }
}
