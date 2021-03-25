package com.example.googlechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.googlechallenge.R.drawable.bronzethread;

public class ItemInventory extends AppCompatActivity {
    List<Item> testItems = new ArrayList<Item>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_inventory);

        getItems(testItems);
        String[] testStrings = new String[] {"A", "B", "C"};
        GridView gridView = findViewById(R.id.gridView);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testStrings);
        gridView.setAdapter(myAdapter);
    }

    public void getItems(List userItems){
        // get items from user data
        // for now, just use the test data
        Item bronzeThread = new Item("Bronze Thread", bronzethread, 1, 5, 3);
        Item silverThread = new Item("Silver Thread", R.drawable.silverthread, 2, 10, 2);
        Item goldThread = new Item("Gold Thread", R.drawable.goldthread, 3, 15, 1);
        List<Item> items = Arrays.asList(new Item[] {bronzeThread, silverThread, goldThread});

        for (Item item : items) {
            userItems.add(item);
            Log.i("item name", item.getName());
        }


    }
}