package com.spark.android.fragment;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spark.android.R;
import com.spark.android.activity.MainActivity;
import com.spark.android.adapter.RetailersAdapter;
import com.spark.android.model.MCategoriesPage;
import com.spark.android.model.MCategory;
import com.spark.android.util.UrlFormatter;

import org.nicktate.projectile.JsonElementListener;
import org.nicktate.projectile.Projectile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by danielclayton on 6/3/14.
 */
public class MyDealsLandingFragment extends ListFragment {
    private ArrayList<String> mRetailers;

    public static MyDealsLandingFragment newInstance() {
        return new MyDealsLandingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vAllDeals = inflater.inflate(R.layout.list_fragment, container, false);
        if (vAllDeals != null) {
            TextView vTitle = (TextView) vAllDeals.findViewById(R.id.title);
            vTitle.setText("My Retailers");
        }

        return vAllDeals;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Set<String> defaultSet = new HashSet<>();
        Set<String> savedUserRetailers = getActivity().getPreferences(Context.MODE_PRIVATE).getStringSet(MainActivity.ARG_SAVED_USER_RETAILERS, defaultSet);

        mRetailers = new ArrayList<>(savedUserRetailers);
        Collections.sort(mRetailers);

        ArrayAdapter<String> adapter = new RetailersAdapter(getActivity(), R.layout.category_item, mRetailers);
        setListAdapter(adapter);
        registerForContextMenu(getListView());
    }

    @SuppressWarnings("ConstantConditions")
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

                            for (Iterator<MCategory> iterator = categories.listIterator();
                                 iterator.hasNext(); ) {
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        for (int i = 0;
             i < menu.size();
             i++) {
            if (menu.getItem(i).getItemId() == R.id.action_add_retailer) {
                menu.getItem(i).setVisible(true);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_retailer) {
            final String[] retailers = getResources().getStringArray(R.array.retailers);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Retailer")
                    .setItems(retailers, new DialogInterface.OnClickListener() {
                        @SuppressWarnings("ConstantConditions")
                        public void onClick(DialogInterface dialog, int which) {
                            if (mRetailers.contains(retailers[which])) {
                                Toast.makeText(getActivity(), retailers[which] + " is already in your list.", Toast.LENGTH_LONG).show();
                            } else {
                                mRetailers.add(retailers[which]);
                                Collections.sort(mRetailers);

                                Set<String> defaultSet = new HashSet<>();
                                Set<String> savedUserRetailers = getActivity().getPreferences(Context.MODE_PRIVATE).getStringSet(MainActivity.ARG_SAVED_USER_RETAILERS, defaultSet);
                                savedUserRetailers.add(retailers[which]);

                                updatePreferences(savedUserRetailers);

                                ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
                            }
                        }
                    });

            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Remove Retailer");
        menu.add("Remove");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Set<String> defaultSet = new HashSet<>();
        Set<String> savedUserRetailers = getActivity().getPreferences(Context.MODE_PRIVATE).getStringSet(MainActivity.ARG_SAVED_USER_RETAILERS, defaultSet);

        String retailerName = (String) getListAdapter().getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        savedUserRetailers.remove(retailerName);
        mRetailers.remove(retailerName);

        updatePreferences(savedUserRetailers);

        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();

        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void updatePreferences(Set<String> savedUserRetailers) {
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().remove(MainActivity.ARG_SAVED_USER_RETAILERS).commit();
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putStringSet(MainActivity.ARG_SAVED_USER_RETAILERS, savedUserRetailers).commit();
    }

    public MyDealsLandingFragment() {
    }
}
