package com.example.googlechallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
    private final Context myContext;
    private final Item[] items;

    public ItemAdapter(Context context, Item[] items){
        this.myContext = context;
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Item item = items[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(myContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_item, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView itemNameTextView = (TextView)convertView.findViewById(R.id.itemNameTextView);
        final TextView itemValueTextView = (TextView)convertView.findViewById(R.id.itemValueTextView);


        // 4
        imageView.setImageResource(item.getIcon());
        itemValueTextView.setText(String.valueOf(item.getValue()) + " G");
        itemNameTextView.setText(item.getName());

        return convertView;
    }

}
