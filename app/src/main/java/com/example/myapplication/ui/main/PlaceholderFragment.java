package com.example.myapplication.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.database.Item;
import com.example.myapplication.item.ItemAdapter;
import com.example.myapplication.item.UserItemAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    Item bronzeThread = new Item(1,"Bronze Thread", "bronzethread", 5, 3);
    Item silverThread = new Item(2, "Silver Thread", "silverthread",  10, 2);
    Item goldThread = new Item(3,"Gold Thread", "goldthread", 15, 1);

    static SharedPreferences sharedPref;
    static SharedPreferences.Editor editor;

    ArrayList<Item> storeItems = new ArrayList<Item>();
    static UserItemAdapter userItemAdapter;
    static ItemAdapter storeAdapter;
    static GridView gridView;
    static GridView storeGridView;
    static TextView goldText;
    static Button sellBtn;
    static Button buyBtn;

    Toast text;
    public static LinkedHashMap<Item, Integer> userItems = new LinkedHashMap<>();
    Item[] keys;

    // User Profile
    protected static int userGold;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        sharedPref = root.getContext().getSharedPreferences("com.example.myapplication.ui.main", MODE_PRIVATE);
        editor = sharedPref.edit();
        userGold = sharedPref.getInt("userGold", 0);

        getItems(root);
        saveObject(root.getContext(), "userItems", userItems);
        sellBtn = root.findViewById(R.id.sellBtn);
        buyBtn = root.findViewById(R.id.buyBtn);
        gridView = root.findViewById(R.id.gridView);
        goldText = root.findViewById(R.id.userGold);
        storeGridView = root.findViewById(R.id.storeGridView);

        storeAdapter = new ItemAdapter(root.getContext(), storeItems);
        userItemAdapter = new UserItemAdapter(root.getContext(), userItems);
        keys = userItems.keySet().toArray(new Item[userItems.size()]);

        goldText.setText(String.valueOf(userGold));
        gridView.setAdapter(userItemAdapter);
        storeGridView.setAdapter(storeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean[] hasSold = {false};
                sellBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hasSold[0]){
                            return;
                        }

                        hasSold[0] = sellItem(view, position);

                    }
                });
            }
        });

        storeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                buyBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        buyItem(view, position);
                    }
                });
            }
        });

        return root;
    }

    public boolean sellItem(View view, int position){
        controlText(view);
        if (userItems.size() == 0){
            return false;
        }
        Item item = keys[position];
        int amount = userItems.get(item);
        updateItem(view, item, amount - 1);
        updateGold(userGold + item.getValue());
        text.setText("You have sold " + item.getName() + " !");
        text.show();
        return true;
    }

    public void updateItem(View view, Item item, int amount){
        LinkedHashMap<Item, Integer> newUserItems = new LinkedHashMap<>(userItems);

        if (amount == 0) {

            newUserItems.remove(item);

        } else {

            newUserItems.put(item, amount);

        }

        userItemAdapter = new UserItemAdapter(view.getContext(), newUserItems);
        userItems = newUserItems;
        keys = userItems.keySet().toArray(new Item[userItems.size()]);
        gridView.setAdapter(userItemAdapter);
        saveObject(view.getContext(), "userItems", userItems);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void buyItem(View view, int position){

        controlText(view);
        final Item item = storeItems.get(position);
        final int price = item.getValue();
        int amount = userItems.getOrDefault(item, 0);
        if (userGold >= price){
            updateGold(userGold - price);
            updateItem(view ,item, amount + 1);
            text.setText("Success!");

        }else{

            text.setText("You don't have enough golds!");

        }


        text.show();
    }

    public void controlText(View view) {
        if (text == null){

            text = Toast.makeText(view.getContext(), "", Toast.LENGTH_SHORT);

        } else {

            text.cancel();

        }
    }
    public static void updateGold(int amount){
        userGold = amount;
        goldText.setText(String.valueOf(userGold));
        editor.putInt("userGold", userGold).apply();

    }

    public static int getGold() {
        return userGold;
    }


    public void getItems(View view ){
        // get items from user data

        userItems = (LinkedHashMap<Item, Integer>) readObj(view.getContext(), "userItems");
        storeItems = new ArrayList<Item>(Arrays.asList(new Item[] {bronzeThread, silverThread, silverThread, goldThread}));



    }

    public static void saveObject(Context mContext, String filename, Object obj){
        try {
            FileOutputStream f = mContext.openFileOutput(filename + ".dat", MODE_PRIVATE);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(obj);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readObj(Context mContext, String filename) {
        try {
            FileInputStream fis = mContext.openFileInput(filename + ".dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashMap<Item, Integer>();
        }
    }
}