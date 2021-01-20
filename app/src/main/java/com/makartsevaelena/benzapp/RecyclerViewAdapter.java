package com.makartsevaelena.benzapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Operation> operations;

    RecyclerViewAdapter(Context context, ArrayList<Operation> operations) {
        this.operations = operations;
        this.inflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Operation operation = operations.get(position);
        holder.price.setText(operation.getPrice());
        Log.d("myLOG",operation.getName());
        holder.operation.setText(operation.getName());
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView operation, price;

        ViewHolder(View view) {
            super(view);
            operation = (TextView) view.findViewById(R.id.operation);
            price = (TextView) view.findViewById(R.id.price);
        }
    }
}
