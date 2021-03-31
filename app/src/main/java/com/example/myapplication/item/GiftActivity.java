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

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.Item;
import com.example.myapplication.ui.main.LockScreen;
import com.example.myapplication.ui.main.PlaceholderFragment;

import java.util.Random;

public class GiftActivity extends com.example.myapplication.item.ItemInventory {

    public Item[] allGiftList = {bronzeThread, silverThread, goldThread};
    public Item[] rareGiftList = {silverThread, goldThread};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
    }
    // 0: Invalid sleeping; duration < 0.5 hour || duration >= 24 hours
    // 1: Valid sleeping; 0.5 hour < duration <= 6 hours
    // 2: Valid and healthy sleeping; 6 < duration < 24 hours
    public static int SleepingStatus = 1;
    private boolean hasReceivedGift = false;
    public int counter = 0;
    public void openGift(View view) throws InterruptedException {
        Item[] newGifts = getGift();
        if (hasReceivedGift || newGifts.length == 0){
            Intent intent = new Intent (com.example.myapplication.item.GiftActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
        ImageView itemImageView = (ImageView) findViewById(R.id.itemImageView);
        Animation ani = new AlphaAnimation(0.00f, 1.00f);
        Animation aniEnd = new AlphaAnimation(1.00f, 0.00f);
        ani.setDuration(1800);
        aniEnd.setDuration(800);
        ani.setAnimationListener(new Animation.AnimationListener() {
            Toast t = Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT);

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onAnimationStart(Animation animation) {
                Item newGift = newGifts[counter];
                int amount = userItems.getOrDefault(newGift, 0);
                updateItem(newGift, amount + 1);
                String name = newGift.getName();
                Log.i("name", name);
                String icon = newGift.getIcon();
                itemImageView.setImageResource(getResources().getIdentifier(icon, "drawable", getPackageName()));
                t = Toast.makeText(getApplicationContext(), "Congratulations! you get a " + name.toLowerCase() + " and some gold coins!", Toast.LENGTH_SHORT);
                t.show();


            }

            public void onAnimationRepeat(Animation animation) {


            }

            public void onAnimationEnd(Animation animation) {
                t.cancel();
                itemImageView.startAnimation(aniEnd);
                if (counter < newGifts.length - 1){
                    counter += 1;
                    itemImageView.startAnimation(ani);
                }

            }

        });
        itemImageView.startAnimation(ani);


        hasReceivedGift = true;

    }


    public Item[] getGift(){

        // TODO: After testing reset the first condition to == 2
        Log.i("gift sleeping status", String.valueOf(SleepingStatus));
        if (SleepingStatus == 0){
            // do something
            Random generator = new Random();
            int randomIndex = generator.nextInt(rareGiftList.length);
            PlaceholderFragment.updateGold(PlaceholderFragment.getGold() + 20);
            Log.i("getGift", Integer.toString(randomIndex));
            return new Item[] {rareGiftList[randomIndex]};
        }else if(SleepingStatus == 1){
            Random generator = new Random();
            int randomIndex = generator.nextInt(allGiftList.length);
            PlaceholderFragment.updateGold(PlaceholderFragment.getGold() + 10);
            return new Item[] {allGiftList[randomIndex]};
        }else{
            return new Item[] {};
        }
    }
}