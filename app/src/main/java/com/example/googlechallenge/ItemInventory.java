package com.example.googlechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.googlechallenge.R.drawable.bronzethread;

public class ItemInventory extends AppCompatActivity {
    // Use getGold() method to get user's gold amount;
    int userGold = 0;
    ArrayList<Item> testItems = new ArrayList<Item>();
    ArrayList<Item> storeItems = new ArrayList<Item>();
    GridView gridView;
    GridView storeGridView;
    TextView goldText;
    Button sellBtn;
    Button buyBtn;

    Toast text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_inventory);

        getItems();
        sellBtn = findViewById(R.id.sellBtn);
        buyBtn = findViewById(R.id.buyBtn);
        gridView = findViewById(R.id.gridView);
        storeGridView = findViewById(R.id.storeGridView);
        goldText = findViewById(R.id.userGold);

        ItemAdapter itemAdapter = new ItemAdapter(this, testItems);
        ItemAdapter storeAdapter = new ItemAdapter(this, storeItems);
        gridView.setAdapter(itemAdapter);
        storeGridView.setAdapter(storeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean[] hasSold = {false};
                Log.i("sell item", String.valueOf(position));
                sellBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("sell click item", String.valueOf(position));
                        if (hasSold[0]){
                            return;
                        }
                        hasSold[0] = sellItem(view, position);
                        gridView.setAdapter(itemAdapter);

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
                        gridView.setAdapter(itemAdapter);
                    }
                });
            }
        });


    }

    public boolean sellItem(View view, int position){
        Log.i("sell function position", String.valueOf(position));
        if (testItems.size() < position + 1){
            return false;
        }
        userGold += testItems.get(position).getValue();
        testItems.remove(position);
        view.setVisibility(View.GONE);
        updateGoldText();
        return true;
    }

    public void buyItem(View view, int position){
        if (text != null){
            text.cancel();
        }
        final Item item = storeItems.get(position);
        final int price = item.getValue();
        text = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        if (userGold >= price){
            userGold -= price;
            testItems.add(item);
            text.setText("Success!");
        }else{
            text.setText("You don't have enough golds!");
        }
        updateGoldText();
        text.show();
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

        testItems = items;
        storeItems = new ArrayList<Item>(Arrays.asList(new Item[] {bronzeThread, silverThread, silverThread, goldThread}));



    }
}