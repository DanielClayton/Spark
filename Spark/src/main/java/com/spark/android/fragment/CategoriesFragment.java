package com.spark.android.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.spark.android.R;
import com.spark.android.model.MCategory;
import com.spark.android.model.MRetailerHomePage;

/**
 * Created by danielclayton on 6/3/14.
 */
public class CategoriesFragment extends ListFragment {
    private static final String ARG_RETAILER_HOME_PAGE = "retailer_home_page";
    
    public static CategoriesFragment newInstance(MRetailerHomePage retailerHomePage) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RETAILER_HOME_PAGE, retailerHomePage);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<MCategory> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ((MRetailerHomePage) getArguments().getSerializable(ARG_RETAILER_HOME_PAGE)).getCategories());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    public CategoriesFragment() {
    }
}
