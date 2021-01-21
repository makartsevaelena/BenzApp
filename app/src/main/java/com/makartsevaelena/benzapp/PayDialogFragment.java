package com.makartsevaelena.benzapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class PayDialogFragment extends AppCompatDialogFragment {
    Order order;

    public PayDialogFragment(Order order) {
        this.order = order;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Данные заказа")
                .setMessage("Заправить " + order.getGazolinaValue() +
                        " лc топливом " + order.getGazolineType() +
                        " по стоимости " + order.getStartPrice() +
                        " за литр. Колонка № " + order.getTerminalCount() +
                        ". Сумма к оплате: " + String.valueOf(order.getFinalPrice())
                )
                .setPositiveButton("Оплатить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("order", String.valueOf(order.getFinalPrice()));
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
