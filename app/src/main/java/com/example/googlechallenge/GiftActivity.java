package com.example.googlechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import static com.example.googlechallenge.R.drawable.bronzethread;

public class GiftActivity extends AppCompatActivity {
    public Item bronzeThread = new Item("Bronze Thread", bronzethread, 1, 5, 3);
    public Item silverThread = new Item("Silver Thread", R.drawable.silverthread, 2, 10, 2);
    public Item goldThread = new Item("Gold Thread", R.drawable.goldthread, 3, 15, 1);
    public Item[] allGiftList = {bronzeThread, silverThread, goldThread};
    public Item[] rareGiftList = {silverThread, goldThread};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
    }
    // 0: Invalid sleeping; duration < 0.5 hour;
    // 1: Valid sleeping; duration >= 0.5 hours;
    // 2: Valid and healthy sleeping; start <= set start time && end >= set end time && 6 hours <= duration <= 9 hours
    public int SleepingStatus = 1;
    public boolean hasReceivedGift = false;
    public int counter = 0;
    public void openGift(View view) throws InterruptedException {
        if (hasReceivedGift){
            Intent intent = new Intent (GiftActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
        Item[] newGifts = getGift();
        ImageView itemImageView = (ImageView) findViewById(R.id.itemImageView);
        Animation ani = new AlphaAnimation(0.00f, 1.00f);
        Animation aniEnd = new AlphaAnimation(1.00f, 0.00f);
        ani.setDuration(1800);
        aniEnd.setDuration(800);
        ani.setAnimationListener(new Animation.AnimationListener() {
            Toast t = Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT);

            public void onAnimationStart(Animation animation) {
                String name = newGifts[counter].getName();
                Log.i("name", name);
                int icon = newGifts[counter].getIcon();
                itemImageView.setImageResource(icon);
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
        //TODO: Shanshan Yu the function randomly gets Gifts according to the condition
        if (SleepingStatus == 2){
            // do something
            Random generator = new Random();
            int randomIndex = generator.nextInt(rareGiftList.length);
            ItemInventory.updateGold(ItemInventory.userGold + 20);
            Log.i("getGift", Integer.toString(randomIndex));
            return new Item[] {rareGiftList[randomIndex]};
        }else if(SleepingStatus == 1){
            Random generator = new Random();
            int randomIndex = generator.nextInt(allGiftList.length);
            ItemInventory.updateGold(ItemInventory.userGold + 10);
            return new Item[] {allGiftList[randomIndex]};
        }else{
            return new Item[] {};
        }
    }
}