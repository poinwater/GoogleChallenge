package com.example.googlechallenge;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class navActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        TabLayout navBar = findViewById(R.id.navBar);
        navBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        intent = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent (getApplicationContext(), ItemInventory.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent (getApplicationContext(), DataActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent (getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}