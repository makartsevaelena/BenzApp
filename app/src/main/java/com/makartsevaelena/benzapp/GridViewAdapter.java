package com.makartsevaelena.benzapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> itemList;
    Context mContext;

    public GridViewAdapter(Context context, int textViewResourceId, ArrayList<String> itemList) {
        super(context, textViewResourceId, itemList);
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) convertView;

        if (convertView == null) {
            convertView = new TextView(mContext);
            label = (TextView) convertView;
            label.setText(itemList.get(position));
        }
        return (convertView);
    }

    public String getItem(int position) {
        return itemList.get(position);
    }

}
