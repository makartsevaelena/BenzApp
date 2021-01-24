package com.makartsevaelena.benzapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
    GridView gridViewTerminalCount;
    FillingStation fillingStation;
    ArrayList<String> spinnerArray;
    BottomNavigationView bottomNavigationView;
    Order order;
    int maxGazolineValue = 100;
    int backpositionTerminalCount = -1;
    int backpositionGazolineType = -1;
    final int REQUEST_ENABLE_BT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // verifyBLEIsSupported();

        fillingStation = new FillingStation();
        order = new Order();
        setInfoAboutTerminalCount();
        setInfoAboutGazlineType();
        setInfoAboutGazoliveValue();
        createBill();
        setBottomNavigation();

    }

    private void verifyBLEIsSupported() {
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


    private void setBottomNavigation() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.action_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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

    private void createBill() {
        Button buttonPay = (Button) findViewById(R.id.button_activitymain_pay);
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
                    order.setSumPrice(order.getGazolinaValue() * order.getPriceForLiter());
                    FragmentManager manager = getSupportFragmentManager();
                    PayDialogFragment payDialogFragment = new PayDialogFragment(order);
                    payDialogFragment.show(manager, "payDialog");
                }
            }
        });
    }

    private void setInfoAboutGazoliveValue() {
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
    }

    private void setInfoAboutGazlineType() {
        final GridView gridViewGazilineType = (GridView) findViewById(R.id.gridViewGazolineType);
        GridViewGazolineTypesAdapter gazolineTypeAdapter = new GridViewGazolineTypesAdapter(getApplicationContext(), fillingStation.getGazolineTypes());
        gridViewGazilineType.setAdapter(gazolineTypeAdapter);
        gridViewGazilineType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order.setGazolineType(fillingStation.getGazolineType(position).getGazoliveType());
                order.setPriceForLiter(fillingStation.getGazolineType(position).getPriceForLiter());
                LinearLayout gridViewItem = (LinearLayout) view;
                gridViewItem.setBackgroundColor(Color.parseColor("#0a0d13"));
                LinearLayout backSelectedItem = (LinearLayout) gridViewGazilineType.getChildAt(backpositionGazolineType);
                if (backpositionGazolineType != -1) {
                    if (backpositionGazolineType != position) {
                        backSelectedItem.setSelected(false);
                        backSelectedItem.setBackgroundColor(Color.parseColor("#1e3552"));
                    }
                }
                backpositionGazolineType = position;
            }
        });
    }

    private void setInfoAboutTerminalCount() {
        gridViewTerminalCount = (GridView) findViewById(R.id.gridViewTerminalCount);
        final GridViewTerminalCountsAdapter terminalCountAdapter = new GridViewTerminalCountsAdapter(getApplicationContext(), fillingStation.getTerminalCounts());
        gridViewTerminalCount.setAdapter(terminalCountAdapter);
        gridViewTerminalCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order.setTerminalCount(fillingStation.getTerminalCount(position));
                TextView gridViewItem = (TextView) view;
                gridViewItem.setBackgroundColor(Color.parseColor("#0a0d13"));
                TextView backSelectedItem = (TextView) gridViewTerminalCount.getChildAt(backpositionTerminalCount);
                if (backpositionTerminalCount != -1) {
                    if (backpositionTerminalCount != position) {
                        backSelectedItem.setSelected(false);
                        backSelectedItem.setBackgroundColor(Color.parseColor("#1e3552"));
                    }
                }
                backpositionTerminalCount = position;
            }
        });
    }

}