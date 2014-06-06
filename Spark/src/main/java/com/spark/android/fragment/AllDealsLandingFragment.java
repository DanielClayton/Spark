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
import com.spark.android.util.UrlFormatter;

import org.nicktate.projectile.JsonElementListener;
import org.nicktate.projectile.Projectile;

import java.util.Iterator;
import java.util.List;

/**
 * Created by danielclayton on 6/3/14.
 */
public class AllDealsLandingFragment extends ListFragment {
    public static AllDealsLandingFragment newInstance() {
        return new AllDealsLandingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vAllDeals = inflater.inflate(R.layout.list_fragment, container, false);
        TextView vTitle = (TextView) vAllDeals.findViewById(R.id.title);
        vTitle.setText("Retailers");

        return vAllDeals;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new RetailersAdapter(getActivity(), R.layout.category_item, getResources().getStringArray(R.array.retailers));
        setListAdapter(adapter);
    }

    private class RetailersAdapter extends ArrayAdapter<String> {
        private final Context mContext;
        private final int mResource;
        private final String[] mRetailers;

        public RetailersAdapter(Context context, int resource, String[] retailers) {
            super(context, resource, retailers);
            this.mContext = context;
            this.mResource = resource;
            this.mRetailers = retailers;
        }

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
            if (mRetailers != null
                    && mRetailers[position] != null) {
                rowTitle = mRetailers[position];
            }

            if (rowTitle != null) {
                viewHolderItem.rowTitle.setText(rowTitle);
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return mRetailers.length;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        final String baseUrl = UrlFormatter.getRetailerApiUrl(String.valueOf(getListAdapter().getItem(position)));

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.progress_dialog_loading_categories_message));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        Projectile.draw(getActivity())
                .aim(baseUrl)
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
                            MCategoriesPage categoryPage = MainActivity.getGsonInstance().fromJson(responseObject, MCategoriesPage.class);
                            List<MCategory> categories = categoryPage.getCategories();

                            for (Iterator<MCategory> iterator = categories.listIterator(); iterator.hasNext(); ) {
                                String categoryTitle = iterator.next().getTitle().toLowerCase();
                                if (!categoryTitle.contains("deals")
                                        && !categoryTitle.contains("coupon")
                                        && !categoryTitle.contains("rebate")
                                        && !categoryTitle.contains("sale")
                                        && !categoryTitle.contains("special")
                                        && !categoryTitle.contains("factory")
                                        && !categoryTitle.contains("outlet")
                                        && !categoryTitle.contains("clearance")) {
                                    iterator.remove();
                                }
                            }

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.container, CategoriesFragment.newInstance(categoryPage, String.valueOf(getListAdapter().getItem(position))), "allDealsToCategories");
                            ft.addToBackStack("allDealsToCategories");
                            ft.commitAllowingStateLoss();
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

    public AllDealsLandingFragment() {
    }
}
