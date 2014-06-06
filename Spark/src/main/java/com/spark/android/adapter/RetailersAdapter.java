package com.spark.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spark.android.R;

import java.util.List;

/**
 * Created by Daniel on 6/6/14.
 */
public class RetailersAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final int mResource;
    private final List<String> mRetailers;

    public RetailersAdapter(Context context, int resource, List<String> retailers) {
        super(context, resource, retailers);
        this.mContext = context;
        this.mResource = resource;
        this.mRetailers = retailers;
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
        if (mRetailers != null
                && mRetailers.get(position) != null) {
            rowTitle = mRetailers.get(position);
        }

        if (rowTitle != null) {
            viewHolderItem.rowTitle.setText(rowTitle);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mRetailers.size();
    }

    static class ViewHolderItem {
        TextView rowTitle;
    }
}
