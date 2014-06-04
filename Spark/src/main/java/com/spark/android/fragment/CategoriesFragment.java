package com.spark.android.fragment;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.spark.android.R;
import com.spark.android.model.MCategory;
import com.spark.android.model.MRetailerHomePage;

import java.util.List;

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
        ArrayAdapter<MCategory> adapter = new CategoryAdapter(getActivity(), android.R.layout.simple_list_item_1, ((MRetailerHomePage) getArguments().getSerializable(ARG_RETAILER_HOME_PAGE)).getCategories());
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
        super.onListItemClick(l, v, position, id);
    }

    public CategoriesFragment() {
    }
}
