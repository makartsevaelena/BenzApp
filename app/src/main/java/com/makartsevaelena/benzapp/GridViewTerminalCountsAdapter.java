package com.makartsevaelena.benzapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewTerminalCountsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listTerminalCounts;
    private final LayoutInflater inflater;

    public GridViewTerminalCountsAdapter(Context context, ArrayList<String> listTerminalCounts) {
        this.context = context;
        this.listTerminalCounts = listTerminalCounts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listTerminalCounts.size();
    }

    @Override
    public String getItem(int position) {
        return listTerminalCounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_terminalcount_item,parent,false);
            holder = new ViewHolder();
            holder.textview_griditem_terminalcount = (TextView) convertView.findViewById(R.id.textview_griditem_terminalcount);
            holder.textview_griditem_terminalcount.setText(listTerminalCounts.get(position));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    static class ViewHolder {
        TextView textview_griditem_terminalcount;
    }
}
