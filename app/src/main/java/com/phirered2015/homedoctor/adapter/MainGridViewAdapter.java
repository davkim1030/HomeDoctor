package com.phirered2015.homedoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.activity.DetailActivity;
import com.phirered2015.homedoctor.dialog.DialogBasket;
import com.phirered2015.homedoctor.item.MainGridItem;

import java.util.ArrayList;

public class MainGridViewAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<MainGridItem> items;


    public MainGridViewAdapter(Context context, ArrayList<MainGridItem> items){
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final int i = position;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_main, viewGroup, false);
        }

        /*
        view.setOnClickListener(new View.OnClickListener(){...});
        그리드 뷰에서 클릭 시 i 값 전달 받은 후 Detail activity로 전달
        */

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("gridpos", i);
                        mContext.startActivity(intent);
                    }

        });
        ImageView gridImg = view.findViewById(R.id.img_gridview);
        TextView name = view.findViewById(R.id.name_gridview);
        TextView price = view.findViewById(R.id.price_gridview);
        Button btn = view.findViewById(R.id.btn_gridview);

        MainGridItem item = items.get(i);
        String pricestr = item.getPrice() + "원";
        Glide.with(mContext).load(item.getImg()).into(gridImg);
        name.setText(item.getName());
        price.setText(pricestr);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBasket db = new DialogBasket(mContext);
                db.callFunc();
            }
        });

        return view;
    }
}
