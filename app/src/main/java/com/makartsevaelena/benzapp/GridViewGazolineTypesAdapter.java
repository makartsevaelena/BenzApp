package com.makartsevaelena.benzapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewGazolineTypesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GazolineType> listGazolineTypes;
    private final LayoutInflater inflater;

    public GridViewGazolineTypesAdapter(Context context, ArrayList<GazolineType> listGazolineTypes) {
        this.context = context;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_gazolinetype_item, parent, false);
            holder = new ViewHolder();
            holder.textview_griditem_gazolinetype = (TextView) convertView.findViewById(R.id.textview_griditem_gazolinetype);
            holder.textview_griditem_priceforliter = (TextView) convertView.findViewById(R.id.textview_griditem_priceforliter);
            holder.textview_griditem_gazolinetype.setText(listGazolineTypes.get(position).getGazoliveType());
            holder.textview_griditem_priceforliter.setText(String.valueOf(listGazolineTypes.get(position).getPriceForLiter()));
        }
        return convertView;
    }
    static class ViewHolder {
        TextView textview_griditem_gazolinetype, textview_griditem_priceforliter;
    }

}