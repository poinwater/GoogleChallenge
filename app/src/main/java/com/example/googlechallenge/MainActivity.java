package com.example.googlechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ChangeToGift(View view){
        Intent intent = new Intent (MainActivity.this, GiftActivity.class);
        startActivity(intent);

    }
    public void ChangeToNav(View view){
        Intent intent = new Intent (MainActivity.this, navActivity.class);
        startActivity(intent);

    }

}