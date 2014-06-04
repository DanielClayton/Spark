package com.spark.android.fragment;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spark.android.R;
import com.spark.android.activity.MainActivity;
import com.spark.android.model.MCategoriesPage;
import com.spark.android.model.MCategory;
import com.spark.android.model.MDealsPage;
import com.spark.android.model.MProductsPage;
import com.spark.android.util.UrlFormatter;

import org.nicktate.projectile.JsonElementListener;
import org.nicktate.projectile.Projectile;

import java.util.List;

/**
 * Created by danielclayton on 6/3/14.
 */
public class CategoriesFragment extends ListFragment {
    public static CategoriesFragment newInstance(MCategoriesPage categoriesPage, String retailerName) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.ARG_CATEGORIES_PAGE, categoriesPage);
        args.putString(MainActivity.ARG_RETAILER_NAME, retailerName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vCategories = inflater.inflate(R.layout.list_fragment, container, false);
        TextView vTitle = (TextView) vCategories.findViewById(R.id.title);
        vTitle.setText("Categories");

        return vCategories;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<MCategory> adapter = new CategoryAdapter(getActivity(), android.R.layout.simple_list_item_1, ((MCategoriesPage) getArguments().getSerializable(MainActivity.ARG_CATEGORIES_PAGE)).getCategories());
        setListAdapter(adapter);
    }

    private class CategoryAdapter extends ArrayAdapter<MCategory> {
        private Context mContext;
        private int mResource;
        private List<MCategory> mCategories;

        public CategoryAdapter(Context context, int resource, List<MCategory> categories) {
            super(context, resource, categories);
            this.mContext = context;
            this.mResource = resource;
            this.mCategories = categories;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            TextView vRowTitle = (TextView) convertView.findViewById(android.R.id.text1);
            vRowTitle.setText(mCategories.get(position).getTitle());

            return convertView;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String fullUrl = UrlFormatter.getRetailerApiUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME))
                + ((MCategoriesPage) getArguments().getSerializable(MainActivity.ARG_CATEGORIES_PAGE)).getCategories().get(position).getHref();

        Projectile.draw(getActivity())
                .aim(fullUrl)
                .retryCount(1)
                .fire(new JsonElementListener() {
                    @Override
                    public void onResponse(JsonElement jsonElement) {
                        if (jsonElement.isJsonObject()) {
                            Gson gson = new Gson();
                            JsonObject responseObject = jsonElement.getAsJsonObject();

                            if (responseObject.has("categories") && responseObject.get("categories").isJsonArray()) {
                                MCategoriesPage categoryPage = gson.fromJson(responseObject, MCategoriesPage.class);

                                try {
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.container,
                                            CategoriesFragment.newInstance(categoryPage,
                                                    getArguments().getString(MainActivity.ARG_RETAILER_NAME)),
                                            "categoriesToCategories");
                                    ft.addToBackStack("categoriesToCategories");
                                    ft.commitAllowingStateLoss();
                                } catch (NullPointerException e) {
                                    // Do nothing right now
                                }

                            } else if (responseObject.has("deals") && responseObject.get("deals").isJsonArray()) {
                                MDealsPage dealsPage = gson.fromJson(responseObject, MDealsPage.class);

                                try {
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.container, DealsFragment.newInstance(dealsPage, "deals", getArguments().getString(MainActivity.ARG_RETAILER_NAME)), "categoriesToDeals");
                                    ft.addToBackStack("categoriesToDeals");
                                    ft.commitAllowingStateLoss();
                                } catch (NullPointerException e) {
                                    // Do nothing right now
                                }
                            } else if (responseObject.has("products") && responseObject.get("products").isJsonArray()) {
                                MProductsPage productsPage = gson.fromJson(responseObject, MProductsPage.class);

                                try {
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.container, DealsFragment.newInstance(productsPage, "products", getArguments().getString(MainActivity.ARG_RETAILER_NAME)), "categoriesToProducts");
                                    ft.addToBackStack("categoriesToProducts");
                                    ft.commitAllowingStateLoss();
                                } catch (NullPointerException e) {
                                    // Do nothing right now
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                    }
                });
    }

    public CategoriesFragment() {
    }
}
