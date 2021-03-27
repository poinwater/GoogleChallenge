package com.example.googlechallenge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.example.googlechallenge.R.drawable.bronzethread;

public class ItemInventory extends AppCompatActivity {
    // Use getGold() method to get user's gold amount;
    ArrayList<Item> testItems = new ArrayList<Item>();
    ArrayList<Item> storeItems = new ArrayList<Item>();
    UserItemAdapter userItemAdapter;
    ItemAdapter storeAdapter;
    GridView gridView;
    GridView storeGridView;
    TextView goldText;
    Button sellBtn;
    Button buyBtn;

    Toast text;
    LinkedHashMap<Item, Integer> userItems = new LinkedHashMap<>();
    Item[] keys;
    int userGold = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_inventory);

        getItems();
        sellBtn = findViewById(R.id.sellBtn);
        buyBtn = findViewById(R.id.buyBtn);
        gridView = findViewById(R.id.gridView);
        goldText = findViewById(R.id.userGold);
        storeGridView = findViewById(R.id.storeGridView);

        storeAdapter = new ItemAdapter(this, storeItems);
        userItemAdapter = new UserItemAdapter(this, userItems);
        keys = userItems.keySet().toArray(new Item[userItems.size()]);


        gridView.setAdapter(userItemAdapter);
        storeGridView.setAdapter(storeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean[] hasSold = {false};
                Log.i("original view id", String.valueOf(view.getUniqueDrawingId()));
                sellBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("sell click item", String.valueOf(position));
                        if (hasSold[0]){
                            return;
                        }
                        hasSold[0] = sellItem(view, position);
                        Log.i("inner view id", String.valueOf(view.getUniqueDrawingId()));

                    }
                });
            }
        });

        storeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("buy item", String.valueOf(position));
                buyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("buy click item", String.valueOf(position));
                        buyItem(view, position);
                    }
                });
            }
        });


    }

    public boolean sellItem(View view, int position){
        controlText();
        if (userItems.size() == 0){
            return false;
        }
        Item item = keys[position];
        int amount = userItems.get(item);
        userGold += item.getValue();
        updateItem(item, amount - 1);
        updateGoldText();
        text.setText("You have sold " + item.getName() + " !");
        text.show();
        return true;
    }

    public void updateItem(Item item, int amount){
        LinkedHashMap<Item, Integer> newUserItems = new LinkedHashMap<>(userItems);

        if (amount == 0) {

            newUserItems.remove(item);

        } else {

            newUserItems.put(item, amount);

        }

        userItemAdapter = new UserItemAdapter(getApplicationContext(), newUserItems);
        userItems = newUserItems;
        keys = userItems.keySet().toArray(new Item[userItems.size()]);
        gridView.setAdapter(userItemAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void buyItem(View view, int position){

        controlText();
        final Item item = storeItems.get(position);
        final int price = item.getValue();
        int amount = userItems.getOrDefault(item, 0);
        if (userGold >= price){
            userGold -= price;
            updateItem(item, amount + 1);
            text.setText("Success!");

        }else{

            text.setText("You don't have enough golds!");

        }

        updateGoldText();
        text.show();
    }

    public void controlText() {
        if (text == null){

            text = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        } else {

            text.cancel();

        }
    }
    public void updateGoldText(){
        goldText.setText(String.valueOf(userGold));
    }

    public void getItems(){
        // get items from user data
        // for now, just use the test data
        Item bronzeThread = new Item("Bronze Thread", bronzethread, 1, 5, 3);
        Item silverThread = new Item("Silver Thread", R.drawable.silverthread, 2, 10, 2);
        Item goldThread = new Item("Gold Thread", R.drawable.goldthread, 3, 15, 1);
        ArrayList<Item> items = new ArrayList<Item>(Arrays.asList(new Item[] {bronzeThread, silverThread, goldThread}));


        userItems.put(bronzeThread, 1);
        userItems.put(goldThread, 3);
        userItems.put(silverThread, 2);


        testItems = items;
        storeItems = new ArrayList<Item>(Arrays.asList(new Item[] {bronzeThread, silverThread, silverThread, goldThread}));



    }
}