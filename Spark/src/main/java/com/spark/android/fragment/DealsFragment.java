package com.spark.android.fragment;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.spark.android.R;
import com.spark.android.activity.MainActivity;
import com.spark.android.model.MDeal;
import com.spark.android.model.MDealsPage;
import com.spark.android.model.MProduct;
import com.spark.android.model.MProductsPage;
import com.spark.android.util.UrlFormatter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by danielclayton on 6/3/14.
 */
public class DealsFragment extends ListFragment {
    public static DealsFragment newInstance(MDealsPage dealsPage, String pageType, String retailerName) {
        DealsFragment fragment = new DealsFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.ARG_PAGE_DATA, dealsPage);
        args.putString(MainActivity.ARG_PAGE_TYPE, pageType);
        args.putString(MainActivity.ARG_RETAILER_NAME, retailerName);
        fragment.setArguments(args);

        return fragment;
    }

    public static DealsFragment newInstance(MProductsPage productsPage, String pageType, String retailerName) {
        DealsFragment fragment = new DealsFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.ARG_PAGE_DATA, productsPage);
        args.putString(MainActivity.ARG_PAGE_TYPE, pageType);
        args.putString(MainActivity.ARG_RETAILER_NAME, retailerName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vCategories = inflater.inflate(R.layout.list_fragment, container, false);
        if (vCategories != null) {
            TextView vTitle = (TextView) vCategories.findViewById(R.id.title);
            vTitle.setText("Deals");
        }

        return vCategories;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<?> adapter;

        switch (getArguments().getString(MainActivity.ARG_PAGE_TYPE)) {
            case "deals":
                adapter = new DealsAdapter(getActivity(), R.layout.deal_item, ((MDealsPage) getArguments().getSerializable(MainActivity.ARG_PAGE_DATA)).getDeals());
                setListAdapter(adapter);
                break;
            case "products":
                adapter = new ProductsAdapter(getActivity(), R.layout.product_item, ((MProductsPage) getArguments().getSerializable(MainActivity.ARG_PAGE_DATA)).getProducts());
                setListAdapter(adapter);
        }
    }

    private class DealsAdapter extends ArrayAdapter<MDeal> {
        private final Context mContext;
        private final int mResource;
        private final List<MDeal> mDeals;

        public DealsAdapter(Context context, int resource, List<MDeal> deals) {
            super(context, resource, deals);
            this.mContext = context;
            this.mResource = resource;
            this.mDeals = deals;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DealsViewHolderItem dealsViewHolderItem;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResource, parent, false);

                dealsViewHolderItem = new DealsViewHolderItem();
                dealsViewHolderItem.dealImage = (ImageView) convertView.findViewById(R.id.deal_image);
                dealsViewHolderItem.dealBullets = (TextView) convertView.findViewById(R.id.deal_bullets);

                convertView.setTag(dealsViewHolderItem);
            } else {
                dealsViewHolderItem = (DealsViewHolderItem) convertView.getTag();
            }

            String dealImageUrl;
            String dealBullets = "";
            if (mDeals != null
                    && mDeals.get(position) != null) {
                if (mDeals.get(position).getImage() != null) {
                    dealImageUrl = mDeals.get(position).getImage();

                    if (dealImageUrl != null) {
                        Picasso.with(getActivity())
                                .load(dealImageUrl)
                                .into(dealsViewHolderItem.dealImage);
                    }
                }

                if (mDeals.get(position).getBullets() != null) {
                    for (int i = 0;
                         i < mDeals.get(position).getBullets().size();
                         i++) {
                        if (i < mDeals.get(position).getBullets().size() - 1) {
                            dealBullets += "• " + mDeals.get(position).getBullets().get(i) + "\r\n";
                        } else {
                            dealBullets += "• " + mDeals.get(position).getBullets().get(i);
                        }
                    }

                    if (!dealBullets.equals("")) {
                        dealsViewHolderItem.dealBullets.setText(dealBullets);
                    }
                }
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return mDeals.size();
        }
    }

    private class ProductsAdapter extends ArrayAdapter<MProduct> {
        private final Context mContext;
        private final int mResource;
        private final List<MProduct> mProducts;

        public ProductsAdapter(Context context, int resource, List<MProduct> products) {
            super(context, resource, products);
            this.mContext = context;
            this.mResource = resource;
            this.mProducts = products;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductsViewHolderItem productsViewHolderItem;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResource, parent, false);

                productsViewHolderItem = new ProductsViewHolderItem();
                productsViewHolderItem.productImage = (ImageView) convertView.findViewById(R.id.product_image);
                productsViewHolderItem.productName = (TextView) convertView.findViewById(R.id.product_name);

                convertView.setTag(productsViewHolderItem);
            } else {
                productsViewHolderItem = (ProductsViewHolderItem) convertView.getTag();
            }

            String productImageUrl;
            String productName;
            if (mProducts != null
                    && mProducts.get(position) != null) {
                if (mProducts.get(position).getImages() != null
                        && mProducts.get(position).getImages().get(0) != null
                        && mProducts.get(position).getImages().get(0).getThumb() != null) {
                    productImageUrl = mProducts.get(position).getImages().get(0).getThumb();

                    if (productImageUrl != null) {
                        Picasso.with(getActivity())
                                .load(UrlFormatter.formatProductImageUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME), productImageUrl))
                                .into(productsViewHolderItem.productImage);
                    }
                }

                if (mProducts.get(position).getTitle() != null) {
                    productName = mProducts.get(position).getTitle();

                    if (productName != null) {
                        productsViewHolderItem.productName.setText(productName);
                    }
                }
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return mProducts.size();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String url;
        String productId;
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (getArguments().getString(MainActivity.ARG_PAGE_TYPE)) {
            case "deals":
                url = ((MDeal) getListAdapter().getItem(position)).getHref();
                ft.replace(R.id.container, DealsWebViewFragment.newInstance(getArguments().getString(MainActivity.ARG_RETAILER_NAME), UrlFormatter.formatDealsPageUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME), url)), "webView");
                ft.addToBackStack("dealsToDealsWebView");
                ft.commitAllowingStateLoss();
                break;
            case "products":
                productId = ((MProduct) getListAdapter().getItem(position)).getProductId();
                ft.replace(R.id.container, DealsWebViewFragment.newInstance(getArguments().getString(MainActivity.ARG_RETAILER_NAME), UrlFormatter.formatProductPageUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME), productId)), "webView");
                ft.addToBackStack("productsToDealsWebView");
                ft.commitAllowingStateLoss();
                break;
        }
    }

    static class DealsViewHolderItem {
        ImageView dealImage;
        TextView dealBullets;
    }

    static class ProductsViewHolderItem {
        ImageView productImage;
        TextView productName;
    }

    public DealsFragment() {
    }
}
