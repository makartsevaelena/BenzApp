package com.makartsevaelena.benzapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView chooseTerminalCount;
    TextView chooseGasolineType;
    GridView gridViewTerminalCount;
    ArrayList<String> listTerminalCounts;
    ArrayList<String> listGazolineTypes;
    int terminalCounts = 6;
    private static final String TAG = "myLogs";
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listTerminalCounts = new ArrayList<String>();
        for (int i = 1; i <= terminalCounts; i++) {
            listTerminalCounts.add(String.valueOf(i));
        }
        listGazolineTypes = new ArrayList<String>();
        listGazolineTypes.add("АИ-80");
        listGazolineTypes.add("АИ-92");
        listGazolineTypes.add("АИ-95");
        listGazolineTypes.add("АИ-95+");
        listGazolineTypes.add("АИ-98");
        listGazolineTypes.add("АИ-100");


        gridViewTerminalCount = (GridView) findViewById(R.id.gridViewTerminalCount);
        GridViewAdapter terminalCountAdapter = new GridViewAdapter(getApplicationContext(), R.layout.gridview_item, listTerminalCounts);
        gridViewTerminalCount.setAdapter(terminalCountAdapter);
        chooseTerminalCount = (TextView) findViewById(R.id.terminalCount);


        GridView gridViewGazilineType = (GridView) findViewById(R.id.gridViewGazolineType);
        GridViewAdapter gazolineTypeAdapter = new GridViewAdapter(getApplicationContext(), R.layout.gridview_item, listGazolineTypes);
        gridViewGazilineType.setAdapter(gazolineTypeAdapter);
        chooseGasolineType = (TextView) findViewById(R.id.gasolineType);


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
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_profile:
                        Intent inPro = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(inPro);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}