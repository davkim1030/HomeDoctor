package com.phirered2015.homedoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.item.MainGridItem;

import java.util.ArrayList;

public class MainGridVIewAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<MainGridItem> items;

    public MainGridVIewAdapter(Context context, ArrayList<MainGridItem> items){
        mContext = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int i = position;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_main, viewGroup, false);
        }
        ImageView gridImg = view.findViewById(R.id.img_gridview);
        TextView name = view.findViewById(R.id.name_gridview);
        TextView price = view.findViewById(R.id.price_gridview);

        MainGridItem item = items.get(i);

        gridImg.setImageDrawable(item.getImg());
        name.setText(item.getName());
        price.setText(String.valueOf(item.getPrice()));

        return view;
    }
}
