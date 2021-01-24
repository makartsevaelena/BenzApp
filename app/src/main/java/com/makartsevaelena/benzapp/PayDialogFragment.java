package com.makartsevaelena.benzapp;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class PayDialogFragment extends AppCompatDialogFragment {
    Order order;
    String orderId = "123456";

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
        TextView textView_bill_orderId = (TextView) view.findViewById(R.id.textview_bill_orderid);
        TextView textView_bill_terminalCount = (TextView) view.findViewById(R.id.textview_bill_terminalcount);
        TextView textView_bill_gazolineType = (TextView) view.findViewById(R.id.textview_bill_gazolinetype);
        TextView textView_bill_priceForLiter = (TextView) view.findViewById(R.id.textview_bill_priceforliter);
        TextView textView_bill_gazolineValue = (TextView) view.findViewById(R.id.textView_bill_gazolineValue);
        TextView textView_bill_sumprice = (TextView) view.findViewById(R.id.textview_bill_sumprice);
        textView_bill_orderId.setText(orderId);
        textView_bill_terminalCount.setText(order.getTerminalCount());
        textView_bill_gazolineType.setText(order.getGazolineType());
        textView_bill_priceForLiter.setText(String.valueOf(order.getPriceForLiter()) + " " + order.getCurrency());
        textView_bill_gazolineValue.setText(String.valueOf(order.getGazolinaValue()));
        textView_bill_sumprice.setText(String.valueOf(order.getSumPrice()) + " " + order.getCurrency());

        Button button_bill_confirm = (Button) view.findViewById(R.id.button_bill_confirm);
        button_bill_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("billConfirm", "clicked");
            }
        });
        return builder.create();
    }
}
