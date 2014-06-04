package com.spark.android.fragment;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.spark.android.R;
import com.spark.android.activity.MainActivity;
import com.spark.android.model.MDeal;
import com.spark.android.model.MDealsPage;
import com.spark.android.model.MProduct;
import com.spark.android.model.MProductsPage;
import com.spark.android.util.UrlFormatter;

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
        TextView vTitle = (TextView) vCategories.findViewById(R.id.title);
        vTitle.setText("Deals");

        return vCategories;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<?> adapter;

        switch(getArguments().getString(MainActivity.ARG_PAGE_TYPE)) {
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
        private Context mContext;
        private int mResource;
        private List<MDeal> mDeals;

        public DealsAdapter(Context context, int resource, List<MDeal> deals) {
            super(context, resource, deals);
            this.mContext = context;
            this.mResource = resource;
            this.mDeals = deals;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            TextView vRowBullets = (TextView) convertView.findViewById(R.id.bullets);
            String bullets = "";

            for (int i = 0; i < mDeals.get(position).getBullets().size(); i++) {
                if (i < mDeals.get(position).getBullets().size() - 1) {
                    bullets += mDeals.get(position).getBullets().get(i) + "\r\n";
                } else {
                    bullets += mDeals.get(position).getBullets().get(i);
                }
            }

            vRowBullets.setText(bullets);

            return convertView;
        }

        @Override
        public int getCount() {
            return mDeals.size();
        }
    }

    private class ProductsAdapter extends ArrayAdapter<MProduct> {
        private Context mContext;
        private int mResource;
        private List<MProduct> mProducts;

        public ProductsAdapter(Context context, int resource, List<MProduct> products) {
            super(context, resource, products);
            this.mContext = context;
            this.mResource = resource;
            this.mProducts = products;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            TextView vRowTitle = (TextView) convertView.findViewById(R.id.product_name);
            vRowTitle.setText(mProducts.get(position).getTitle());

            return convertView;
        }

        @Override
        public int getCount() {
            return mProducts.size();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String href = "";
        String productId = "";
        Intent i = new Intent(Intent.ACTION_VIEW);

        switch(getArguments().getString(MainActivity.ARG_PAGE_TYPE)) {
            case "deals":
                href = ((MDeal) getListAdapter().getItem(position)).getHref();
                i.setData(Uri.parse(UrlFormatter.formatDealsPageUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME), href)));
                break;
            case "products":
                productId = ((MProduct) getListAdapter().getItem(position)).getProductId();
                i.setData(Uri.parse(UrlFormatter.formatProductPageUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME), productId)));
                break;
        }

        startActivity(i);
    }

    public DealsFragment() {
    }
}
