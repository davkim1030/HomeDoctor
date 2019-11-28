package com.phirered2015.homedoctor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.item.DeliverStateItem;

import java.util.ArrayList;

public class BasketAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DeliverStateItem> items;
    public BasketAdapter(Context context, ArrayList<DeliverStateItem> items) {
        this.context = context;
        this.items = items;
        Log.e("Adapter check", "123");
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final int pos = i;

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list, parent, false);
        }
        ImageView imageView = view.findViewById(R.id.image);
        TextView title = view.findViewById(R.id.item_name);
        TextView price = view.findViewById(R.id.item_price);
        TextView quantity = view.findViewById(R.id.item_count);

        DeliverStateItem item = items.get(pos);
        title.setText(item.getName());
        Glide.with(context)
                .load(item.getImg())
                .into(imageView);
        price.setText(String.valueOf(item.getPrice()));
        quantity.setText(item.getState());   // 수량이지만 state를 사용
        return view;
    }
}
