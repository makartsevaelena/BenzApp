package com.makartsevaelena.benzapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewGazolineTypesAdapter extends BaseAdapter {
    private ArrayList<GazolineType> listGazolineTypes;
    private final LayoutInflater inflater;

    public GridViewGazolineTypesAdapter(Context context, ArrayList<GazolineType> listGazolineTypes) {
        this.listGazolineTypes = listGazolineTypes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listGazolineTypes.size();
    }

    @Override
    public GazolineType getItem(int position) {
        return listGazolineTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_gazolinetype_item, parent, false);
            TextView textView_gazoline_type = (TextView) convertView.findViewById(R.id.grid_item_gazoline_type);
            TextView textView_gazoline_price = (TextView) convertView.findViewById(R.id.grid_item_gazoline_price);
            textView_gazoline_type.setText(listGazolineTypes.get(position).getName());
            textView_gazoline_price.setText(String.valueOf(listGazolineTypes.get(position).getPrice()));
        }
        return convertView;
    }

}