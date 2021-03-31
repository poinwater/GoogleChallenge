package com.example.myapplication.item;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.Item;
import com.example.myapplication.ui.main.PlaceholderFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GiftActivity extends AppCompatActivity {

    Item bronzeThread = new Item(1,"Bronze Thread", "bronzethread", 5, 3);
    Item silverThread = new Item(2, "Silver Thread", "silverthread",  10, 2);
    Item goldThread = new Item(3,"Gold Thread", "goldthread", 15, 1);

    public ArrayList<Item> allGiftList = new ArrayList<>(Arrays.asList(new Item[]{bronzeThread, silverThread, goldThread}));
    public ArrayList<Item> rareGiftList = new ArrayList<>(Arrays.asList(new Item[]{silverThread, goldThread}));


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
        ArrayList<Item> newGifts = getGift(allGiftList, rareGiftList);
        if (hasReceivedGift || newGifts.size() == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        }

        giftAnimation(newGifts);
        hasReceivedGift = true;

    }

    public void giftAnimation(ArrayList<Item> newGifts) {
        ImageView itemImageView = (ImageView) findViewById(R.id.itemImageView);
        Animation ani = new AlphaAnimation(0.00f, 1.00f);
        Animation aniEnd = new AlphaAnimation(1.00f, 0.00f);
        ani.setDuration(1800);
        aniEnd.setDuration(800);
        ani.setAnimationListener(new Animation.AnimationListener() {
            Toast t = Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT);

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onAnimationStart(Animation animation) {
                Item newGift = newGifts.get(counter);

                // Update gift
                int amount = PlaceholderFragment.userItems.getOrDefault(newGift, 0);
                PlaceholderFragment.userItems.put(newGift, amount + 1);
                PlaceholderFragment.saveObject(getBaseContext(), "userItems", PlaceholderFragment.userItems);

                String name = newGift.getName();
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
                if (counter < newGifts.size() - 1) {
                    counter += 1;
                    itemImageView.startAnimation(ani);
                }

            }

        });
        itemImageView.startAnimation(ani);
    }


    public ArrayList<Item> getGift(ArrayList<Item> allGiftList, ArrayList<Item>  rareGiftList){
        ArrayList<Item> newGifts = new ArrayList<>();
        // TODO: After testing reset the first condition to == 2
        if (SleepingStatus == 0){
            // do something
            Random generator = new Random();
            int randomIndex = generator.nextInt(rareGiftList.size());
            PlaceholderFragment.updateGold(PlaceholderFragment.getGold() + 20);
            newGifts.add(rareGiftList.get(randomIndex));

        }
        if (SleepingStatus == 1) {
            Random generator = new Random();
            int randomIndex = generator.nextInt(allGiftList.size());
            PlaceholderFragment.updateGold(PlaceholderFragment.getGold() + 10);
            newGifts.add(allGiftList.get(randomIndex));
        }
        return newGifts;
    }
}