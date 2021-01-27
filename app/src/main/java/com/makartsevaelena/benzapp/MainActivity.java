package com.makartsevaelena.benzapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.altbeacon.beacon.BeaconManager;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final String TAG = "MailActivity";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    GridView gridViewTerminalCount;
    FillingStation fillingStation;
    ArrayList<String> spinnerArray;
    BottomNavigationView bottomNavigationView;
    Order order;
    int maxGazolineValue = 100;
    int backpositionTerminalCount = -1;
    int backpositionGazolineType = -1;
    private LocationManager locationManager;
    private BluetoothManager bluetoothManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyBluetooth();
        requestLocationPermission();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requestGpsStatus();
        }
        bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        fillingStation = new FillingStation();
        order = new Order();
        setInfoAboutTerminalCount();
        setInfoAboutGazlineType();
        setInfoAboutGazoliveValue();
        createBill();
        setBottomNavigation();
//        Button button_server = (Button) findViewById(R.id.button_server);
//        button_server.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ServerActivity.class));
//            }
//        });
    }

    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                Log.d(TAG, "Requested user enables Bluetooth. Try starting the scan again.");
            }
        } catch (RuntimeException e) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    //finish();
                    //System.exit(0);
                }

            });
            builder.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestGpsStatus() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setMessage("GPS Enable")
                    .setPositiveButton("Settings", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }


    private boolean hasLocationPermissions() {
        return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                            builder.setTitle("This app needs background location access");
                            builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                                @TargetApi(21)
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                            PERMISSION_REQUEST_BACKGROUND_LOCATION);
                                }

                            });
                            builder.show();
                        } else {
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                            builder.setTitle("Functionality limited");
                            builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setNeutralButton("try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                            PERMISSION_REQUEST_FINE_LOCATION);
                                }
                            });
                            builder.show();
                        }
                    }
                }
            } else {
                if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            PERMISSION_REQUEST_FINE_LOCATION);
                } else {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setNeutralButton("try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                    PERMISSION_REQUEST_FINE_LOCATION);
                        }
                    });
                    builder.show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "fine location permission granted");
                } else {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
            case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "background location permission granted");
                } else {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setNeutralButton("try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                    PERMISSION_REQUEST_FINE_LOCATION);
                        }
                    });
                    builder.show();
                }
                return;
            }
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
                    watchSumBill();


                }
            }
        });
    }

    private void watchSumBill() {
        View view = getLayoutInflater().inflate(R.layout.layout_bill, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        TextView textView_bill_orderId = (TextView) view.findViewById(R.id.textview_bill_orderid);
        TextView textView_bill_terminalCount = (TextView) view.findViewById(R.id.textview_bill_terminalcount);
        TextView textView_bill_gazolineType = (TextView) view.findViewById(R.id.textview_bill_gazolinetype);
        TextView textView_bill_priceForLiter = (TextView) view.findViewById(R.id.textview_bill_priceforliter);
        TextView textView_bill_gazolineValue = (TextView) view.findViewById(R.id.textView_bill_gazolineValue);
        TextView textView_bill_sumprice = (TextView) view.findViewById(R.id.textview_bill_sumprice);
        textView_bill_orderId.setText(String.valueOf(order.getOrderId()));
        textView_bill_terminalCount.setText(order.getTerminalCount());
        textView_bill_gazolineType.setText(order.getGazolineType());
        textView_bill_priceForLiter.setText(order.getPriceForLiter() + " " + order.getCurrency());
        textView_bill_gazolineValue.setText(String.valueOf(order.getGazolinaValue()));
        textView_bill_sumprice.setText(order.getSumPrice() + " " + order.getCurrency());
        builder.show();
    }

    private void setInfoAboutGazoliveValue() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
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