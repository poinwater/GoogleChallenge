package com.example.googlechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class GiftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
    }

    public boolean isHealthySleeping = false;


    public void openGift(View view){
        //TODO: Shanshan Yu openGift
    }

    public Item[] getGift(boolean isHealthySleeping){
        //TODO: Shanshan Yu the function randomly gets Gifts according to the condition
        if (isHealthySleeping){
            // do something
        }else{
            // do something
        }
    }
}