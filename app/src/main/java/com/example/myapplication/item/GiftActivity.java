package com.example.myapplication.item;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.Item;
import com.example.myapplication.database.WordViewModel;
import com.example.myapplication.ui.main.PlaceholderFragment;

import java.util.Random;

public class GiftActivity extends com.example.myapplication.item.ItemInventory {


    public static int SleepingStatus = 1;
    public int counter = 0;
    ImageView giftBoxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        FindByViewId();
        if (SleepingStatus == 0) {
            goBack();
        } else if (SleepingStatus == 1) {
            readAllItemsFromDatabase();
        } else {
            readRareItemsFromDatabase();
        }

    }

    // 0: Invalid sleeping; duration < 0.5 hour || duration >= 24 hours
    // 1: Valid sleeping; 0.5 hour < duration <= 6 hours
    // 2: Valid and healthy sleeping; 6 < duration < 24 hours



    public void goBack() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void FindByViewId() {
        giftBoxView = findViewById(R.id.giftBoxView);
    }

    public void giftAnimation(Item[] newGifts) {
        Toast t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        ImageView itemImageView = findViewById(R.id.itemImageView);
        Animation ani = new AlphaAnimation(0.00f, 1.00f);
        Animation aniEnd = new AlphaAnimation(1.00f, 0.00f);
        ani.setDuration(1800);
        aniEnd.setDuration(800);
        ani.setAnimationListener(new Animation.AnimationListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onAnimationStart(Animation animation) {
                Item newGift = newGifts[counter];

                int amount = userItems.getOrDefault(newGift, 0);
                updateItem(newGift, amount + 1);
                String name = newGift.getName();
                String icon = newGift.getIcon();
                itemImageView.setImageResource(getResources().getIdentifier(icon, "drawable", getPackageName()));
                t.makeText(getApplicationContext(), "Congratulations! you get a " + name.toLowerCase() + " and some gold coins!", Toast.LENGTH_SHORT).show();


            }

            public void onAnimationRepeat(Animation animation) {


            }

            public void onAnimationEnd(Animation animation) {
                t.cancel();
                itemImageView.startAnimation(aniEnd);
                if (counter < newGifts.length - 1) {
                    counter += 1;
                    itemImageView.startAnimation(ani);
                }

            }

        });
        itemImageView.startAnimation(ani);
    }




    public void readAllItemsFromDatabase(){

        WordViewModel mWordViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WordViewModel.class);
        mWordViewModel.getAllItems().observe(this, items -> {
            Random random = new Random();

            int index = random.nextInt(items.size());
            Item newItem = items.get(index);
            Item[] newGifts = new Item[] {newItem};


            Log.i("new gift", newItem.getName());
            giftAnimation(newGifts);

            giftBoxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });


        });


    }

    public void readRareItemsFromDatabase() {

        WordViewModel mWordViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WordViewModel.class);
        mWordViewModel.getRareItems().observe(this, items -> {
            Random random = new Random();

            int index = random.nextInt(items.size());
            Item newItem = items.get(index);
            Item[] newGifts = new Item[] {newItem};


            Log.i("new rare gift", newItem.getName());
            giftAnimation(newGifts);

            giftBoxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });


        });

    }
}