package com.makartsevaelena.benzapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView_selectedTerminalCount;
    TextView textView_selectedGasolineType;
    GridView gridViewTerminalCount;
    FillingStation fillingStation;
    ArrayList<String> spinnerArray;
    BottomNavigationView navigationView;
    Order order;
    int maxGazolineValue = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillingStation = new FillingStation();
        order = new Order();

        gridViewTerminalCount = (GridView) findViewById(R.id.gridViewTerminalCount);
        GridViewTerminalCountsAdapter terminalCountAdapter = new GridViewTerminalCountsAdapter(getApplicationContext(), fillingStation.getTerminalCounts());
        gridViewTerminalCount.setAdapter(terminalCountAdapter);
        textView_selectedTerminalCount = (TextView) findViewById(R.id.selectedTerminalCount);
        gridViewTerminalCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView_selectedTerminalCount.setText(fillingStation.getTerminalCount(position));
                order.setTerminalCount(fillingStation.getTerminalCount(position));
            }
        });

        GridView gridViewGazilineType = (GridView) findViewById(R.id.gridViewGazolineType);
        GridViewGazolineTypesAdapter gazolineTypeAdapter = new GridViewGazolineTypesAdapter(getApplicationContext(), fillingStation.getGazolineTypes());
        gridViewGazilineType.setAdapter(gazolineTypeAdapter);
        textView_selectedGasolineType = (TextView) findViewById(R.id.selectedGasolineType);
        gridViewGazilineType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView_selectedGasolineType.setText(fillingStation.getGazolineType(position).getName());
                order.setGazolineType(fillingStation.getGazolineType(position).getName());
                order.setStartPrice(fillingStation.getGazolineType(position).getPrice());
            }
        });

        Spinner spinner = findViewById(R.id.spinner);
        spinnerArray = new ArrayList<String>();
        for (int j = 10; j < maxGazolineValue; j++) {
            spinnerArray.add(String.valueOf(j));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                order.setGazolinaValue(Integer.parseInt(spinnerArray.get(selectedItemPosition)));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button buttonPay = (Button) findViewById(R.id.button_pay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getPriceForLiter() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Не выбрано топливо", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (order.getTerminalCount() == null) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Не выбрана колонка", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    order.setFinalPrice(order.getGazolinaValue() * order.getPriceForLiter());
                    FragmentManager manager = getSupportFragmentManager();
                    PayDialogFragment payDialogFragment = new PayDialogFragment(order);
                    payDialogFragment.show(manager, "payDialog");
                }
            }
        });

        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.action_main);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_main:
                        return true;
                    case R.id.action_history:
                        Intent inHis = new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(inHis);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_profile:
                        Intent inPro = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(inPro);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

}