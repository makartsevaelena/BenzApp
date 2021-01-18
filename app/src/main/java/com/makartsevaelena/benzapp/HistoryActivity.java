package com.makartsevaelena.benzapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ArrayList<Operation> operations;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        operations = new ArrayList<Operation>();
        setData();
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), operations);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewAdapter.notifyDataSetChanged();

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.action_history);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_main:
                        Intent inAct = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(inAct);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_history:
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
    private void setData(){
        operations.add(new Operation ("АИ-95","500 rub"));
        operations.add(new Operation ("АИ-95","2000 rub"));
        operations.add(new Operation ("АИ-95","1500 rub"));
        operations.add(new Operation ("АИ-95","600 rub"));
    }
}

