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
    ArrayList<Order> orders;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        orders = new ArrayList<>();
        setData();
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), orders);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewAdapter.notifyDataSetChanged();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.action_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
        orders.add(new Order("АИ-92","2",14,10));
        orders.add(new Order("АИ-80","2",25,10));
        orders.add(new Order("АИ-98","3",12,10));
        orders.add(new Order("АИ-95","4",65,10));
    }
}

