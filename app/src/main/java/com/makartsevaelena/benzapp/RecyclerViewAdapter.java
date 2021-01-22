package com.makartsevaelena.benzapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Order> orders;

    RecyclerViewAdapter(Context context, ArrayList<Order> orders) {
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderFinalPrice.setText(order.getSummaryPrice() + " руб");
        holder.orderGazolineType.setText(order.getGazolineType());
        holder.orderTerminalCount.setText(order.getTerminalCount());
        holder.orderStartPrice.setText(String.valueOf(order.getPriceForLiter()));
        holder.orderGazolineValue.setText(String.valueOf(order.getGazolinaValue()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView orderFinalPrice, orderGazolineValue, orderStartPrice, orderTerminalCount, orderGazolineType;

        ViewHolder(View view) {
            super(view);
            orderGazolineType = (TextView) view.findViewById(R.id.order_gazolineType);
            orderTerminalCount = (TextView) view.findViewById(R.id.order_terminalCount);
            orderStartPrice = (TextView) view.findViewById(R.id.order_startPrice);
            orderGazolineValue = (TextView) view.findViewById(R.id.order_gazolineValue);
            orderFinalPrice = (TextView) view.findViewById(R.id.order_finalPrice);
        }
    }
}
