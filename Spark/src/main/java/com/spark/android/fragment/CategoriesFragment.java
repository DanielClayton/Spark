package com.spark.android.fragment;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
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
        if (vCategories != null) {
            TextView vTitle = (TextView) vCategories.findViewById(R.id.title);
            vTitle.setText(getString(R.string.categories));
        }

        return vCategories;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<MCategory> adapter = new CategoryAdapter(getActivity(),
                R.layout.category_item,
                ((MCategoriesPage) getArguments().getSerializable(MainActivity.ARG_CATEGORIES_PAGE)).getCategories());
        setListAdapter(adapter);
    }

    private class CategoryAdapter extends ArrayAdapter<MCategory> {
        private final Context mContext;
        private final int mResource;
        private final List<MCategory> mCategories;

        public CategoryAdapter(Context context, int resource, List<MCategory> categories) {
            super(context, resource, categories);
            this.mContext = context;
            this.mResource = resource;
            this.mCategories = categories;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderItem viewHolderItem;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResource, parent, false);

                viewHolderItem = new ViewHolderItem();
                viewHolderItem.rowTitle = (TextView) convertView.findViewById(R.id.category_title);

                convertView.setTag(viewHolderItem);
            } else {
                viewHolderItem = (ViewHolderItem) convertView.getTag();
            }

            String rowTitle = null;
            if (mCategories != null
                    && mCategories.get(position) != null) {
                rowTitle = mCategories.get(position).getTitle();
            }

            if (rowTitle != null) {
                viewHolderItem.rowTitle.setText(rowTitle);
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String fullUrl = UrlFormatter.getRetailerApiUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME))
                + ((MCategoriesPage) getArguments().getSerializable(MainActivity.ARG_CATEGORIES_PAGE)).getCategories().get(position).getHref();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.progress_dialog_default_message));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        Projectile.draw(getActivity())
                .aim(fullUrl)
                .retryCount(1)
                .fire(new JsonElementListener() {
                    @Override
                    public void onResponse(JsonElement jsonElement) {
                        if (progressDialog.isShowing()) {
                            progressDialog.hide();
                        }

                        if (jsonElement.isJsonObject()
                                && getFragmentManager() != null) {
                            JsonObject responseObject = jsonElement.getAsJsonObject();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();

                            if (responseObject.has("categories") && responseObject.get("categories").isJsonArray()) {
                                MCategoriesPage categoryPage = MainActivity.getGsonInstance().fromJson(responseObject, MCategoriesPage.class);

                                ft.replace(R.id.container, CategoriesFragment.newInstance(categoryPage,
                                                getArguments().getString(MainActivity.ARG_RETAILER_NAME)),
                                        "categoriesToCategories"
                                );
                                ft.addToBackStack("categoriesToCategories");
                                ft.commitAllowingStateLoss();
                            } else if (responseObject.has("deals") && responseObject.get("deals").isJsonArray()) {
                                MDealsPage dealsPage = MainActivity.getGsonInstance().fromJson(responseObject, MDealsPage.class);

                                ft.replace(R.id.container, DealsFragment.newInstance(dealsPage,
                                                "deals",
                                                getArguments().getString(MainActivity.ARG_RETAILER_NAME)),
                                        "categoriesToDeals"
                                );
                                ft.addToBackStack("categoriesToDeals");
                                ft.commitAllowingStateLoss();
                            } else if (responseObject.has("products") && responseObject.get("products").isJsonArray()) {
                                MProductsPage productsPage = MainActivity.getGsonInstance().fromJson(responseObject, MProductsPage.class);

                                ft.replace(R.id.container, DealsFragment.newInstance(productsPage,
                                                "products",
                                                getArguments().getString(MainActivity.ARG_RETAILER_NAME)),
                                        "categoriesToProducts"
                                );
                                ft.addToBackStack("categoriesToProducts");
                                ft.commitAllowingStateLoss();
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        if (progressDialog.isShowing()) {
                            progressDialog.hide();
                        }
                    }
                });
    }

    static class ViewHolderItem {
        TextView rowTitle;
    }

    public CategoriesFragment() {
    }
}
