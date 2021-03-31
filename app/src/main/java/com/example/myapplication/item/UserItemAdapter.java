package com.example.myapplication.item;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.myapplication.R;
import com.example.myapplication.database.Item;

import java.util.LinkedHashMap;


public class UserItemAdapter extends BaseAdapter {
    private final Context myContext;
    private final LinkedHashMap<Item, Integer> items;
    private final Resources resources;
    Item[] keys;

    public UserItemAdapter(Context context, LinkedHashMap<Item, Integer> items){
        this.myContext = context;
        this.items = items;
        this.keys = items.keySet().toArray(new Item[items.size()]);
        this.resources = context.getResources();

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return keys[position];
    }

    @Override
    public long getItemId(int position) {
        return keys[position].getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Item item = keys[position];
        final int amount = items.getOrDefault(keys[position], 0);
        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(myContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_item, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView itemNameTextView = (TextView)convertView.findViewById(R.id.itemNameTextView);
        final TextView itemValueTextView = (TextView)convertView.findViewById(R.id.itemValueTextView);
        final TextView amountTextView = (TextView) convertView.findViewById(R.id.amountTextView);

        // 4
        imageView.setImageResource(resources.getIdentifier(item.getIcon(), "drawable", myContext.getPackageName()));
        itemValueTextView.setText(String.valueOf(item.getValue()) + " G");
        itemNameTextView.setText(item.getName());
        amountTextView.setText('x'+String.valueOf(amount));
        convertView.setBackgroundResource(R.drawable.round_corner);

        if (items.get(keys[position]) == 0) {
            convertView.setVisibility(View.GONE);
        }

        return convertView;
    }

}
