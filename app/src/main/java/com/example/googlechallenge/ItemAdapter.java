package com.example.googlechallenge;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView dummyTextView = new TextView(myContext);
        dummyTextView.setText(String.valueOf(position));
        return dummyTextView;
    }
}
