package com.makartsevaelena.benzapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.action_profile);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_main:
                        Intent inAct = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(inAct);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_history:
                        Intent inHis = new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(inHis);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_profile:
                        return true;
                }
                return false;
            }
        });

    }
}
