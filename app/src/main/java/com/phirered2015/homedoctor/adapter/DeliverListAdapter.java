package com.phirered2015.homedoctor.adapter;

import android.content.Context;
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

import androidx.recyclerview.widget.ListAdapter;

public class DeliverListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<DeliverStateItem> items;

    public DeliverListAdapter(Context context, ArrayList<DeliverStateItem> items){
        mContext = context;
        this.items = items;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_deliver_state, viewGroup, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView imgRep = view.findViewById(R.id.img_rep);
        TextView txtName = view.findViewById(R.id.txt_name);
        TextView txtPrice = view.findViewById(R.id.txt_price);
        TextView txtState = view.findViewById(R.id.txt_deliver_state);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DeliverStateItem item = items.get(pos);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(mContext)
                .load(item.getImg())
                .into(imgRep);
        txtName.setText(item.getName());
        txtPrice.setText(String.valueOf(item.getPrice()));
        txtState.setText(item.getState());



        return view;
    }
}
