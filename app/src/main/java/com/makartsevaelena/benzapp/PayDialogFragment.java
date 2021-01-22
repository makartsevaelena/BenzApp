package com.makartsevaelena.benzapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class PayDialogFragment extends AppCompatDialogFragment {
    Order order;
    String numberOrder = "123456";

    public PayDialogFragment(Order order) {
        this.order = order;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_bill, null);
        builder.setView(view);
        TextView textView_bill_numberOrder = (TextView) view.findViewById(R.id.bill_orderNumber);
        TextView textView_bill_terminalCount = (TextView) view.findViewById(R.id.bill_terminalCount);
        TextView textView_bill_gazolineType = (TextView) view.findViewById(R.id.bill_gazolineType);
        TextView textView_bill_priceForLiter = (TextView) view.findViewById(R.id.bill_priceForLiter);
        TextView textView_bill_gazolineValue = (TextView) view.findViewById(R.id.bill_gazolineValue);
        TextView textView_bill_bill_summary = (TextView) view.findViewById(R.id.bill_summary);
        textView_bill_numberOrder.setText(numberOrder);
        textView_bill_terminalCount.setText(order.getTerminalCount());
        textView_bill_gazolineType.setText(order.getGazolineType());
        textView_bill_priceForLiter.setText(String.valueOf(order.getPriceForLiter()));
        textView_bill_gazolineValue.setText(String.valueOf(order.getGazolinaValue()));
        textView_bill_bill_summary.setText(String.valueOf(order.getSummaryPrice()));
        return builder.create();
    }
}
