package com.makartsevaelena.benzapp;

import android.bluetooth.*;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import static com.makartsevaelena.benzapp.Constants.SERVICE_UUID;

public class ServerActivity extends AppCompatActivity {

    private static final String TAG = "ServerActivity";
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private BluetoothGattServer mGattServer;
    private List<BluetoothDevice> mDevices;
    TextView server_device_info_text_view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        server_device_info_text_view = (TextView)findViewById(R.id.server_device_info_text_view);
        Button restart_server_button = (Button) findViewById(R.id.restart_server_button);
        restart_server_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartServer();
            }
        });
    }

    // должно иметь включенный Bluetooth и поддерживать функцию энергосбережения
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            finish();
            return;
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.d(TAG, "No LE Support.");
            finish();
            return;
        }
        //нужно убедиться, что реклама поддерживается оборудованием
        if (!mBluetoothAdapter.isMultipleAdvertisementSupported()) {
            Log.d(TAG, "No Advertising Support.");
            finish();
            return;
        }

        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        GattServerCallback gattServerCallback = new GattServerCallback();
        mGattServer = mBluetoothManager.openGattServer(this, gattServerCallback);
        String deviceInfo = "Device Info" + "\nName: " + mBluetoothAdapter.getName() + "\nAddress: " + mBluetoothAdapter.getAddress();
        server_device_info_text_view.setText(deviceInfo);
        setupServer();
        startAdvertising();
    }

    private class GattServerCallback extends BluetoothGattServerCallback {
        //отслеживать подключенные устройства
        //добавляем и удаляем устройства из списка на основе newState
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mDevices.add(device);
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mDevices.remove(device);
            }
        }
    }

    //добавить Сервис на сервер
    private void setupServer() {
        //SERVICE_UUID гарантирует, что мы подключаемся к правильному серверу GATT
        BluetoothGattService service = new BluetoothGattService(SERVICE_UUID,
                BluetoothGattService.SERVICE_TYPE_PRIMARY);
        mGattServer.addService(service);
    }

    //adv GATT
    private void startAdvertising() {
        if (mBluetoothLeAdvertiser == null) {
            return;
        }
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                //данные туда и обратно, в отличие от маяка.
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW)
                .build();

        //отправить посылку и установить UUID службы.
        ParcelUuid parcelUuid = new ParcelUuid(SERVICE_UUID);
        AdvertiseData data = new AdvertiseData.Builder()
                //быстрым и простым способом идентифицировать сервер
                .setIncludeDeviceName(false)
                .addServiceUuid(parcelUuid)
                .build();
        mBluetoothLeAdvertiser.startAdvertising(settings, data, mAdvertiseCallback);
    }

    //сообщить, успешно ли сервер запустил рекламу
    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Log.d(TAG, "Peripheral advertising started.");
        }

        @Override
        public void onStartFailure(int errorCode) {
            Log.d(TAG, "Peripheral advertising failed: " + errorCode);
        }
    };

    protected void onPause() {
        super.onPause();
        stopAdvertising();
        stopServer();
    }

    private void stopServer() {
        if (mGattServer != null) {
            mGattServer.close();
        }
        Log.d(TAG, "stopServer.");
    }

    private void stopAdvertising() {
        if (mBluetoothLeAdvertiser != null) {
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
            Log.d(TAG, "stopAdvertising.");
        }
    }

    private void restartServer() {
        stopAdvertising();
        stopServer();
        setupServer();
        startAdvertising();
        Log.d(TAG, "restartServer.");
    }
}

