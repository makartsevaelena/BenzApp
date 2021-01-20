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

public class MainActivity extends AppCompatActivity {
    TextView chooseTerminalCount;
    TextView chooseGasolineType;
    GridView gridViewTerminalCount;
    FillingStation fillingStation;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillingStation = new FillingStation();

        gridViewTerminalCount = (GridView) findViewById(R.id.gridViewTerminalCount);
        GridViewTerminalCountsAdapter terminalCountAdapter = new GridViewTerminalCountsAdapter(getApplicationContext(), fillingStation.getTerminalCounts());
        gridViewTerminalCount.setAdapter(terminalCountAdapter);
        chooseTerminalCount = (TextView) findViewById(R.id.chooseTerminalCount);
        gridViewTerminalCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseTerminalCount.setText(fillingStation.getTerminalCount(position));
            }
        });

        GridView gridViewGazilineType = (GridView) findViewById(R.id.gridViewGazolineType);
        GridViewGazolineTypesAdapter gazolineTypeAdapter = new GridViewGazolineTypesAdapter(getApplicationContext(), fillingStation.getGazolineTypes());
        gridViewGazilineType.setAdapter(gazolineTypeAdapter);
        chooseGasolineType = (TextView) findViewById(R.id.chooseGasolineType);
        gridViewGazilineType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseGasolineType.setText(fillingStation.getGazolineType(position).getName());
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