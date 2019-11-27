package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.MainGridVIewAdapter;
import com.phirered2015.homedoctor.item.MainGridItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    GridView gridView;
    String[] names = {
            "의자",
            "책상",
            "의자",
            "책상",
            "의자",
            "책상",
            "의자",
            "책상"
    } ;
    Integer[] imgs = {
            R.drawable.chair,
            R.drawable.desk,
            R.drawable.chair,
            R.drawable.desk,
            R.drawable.chair,
            R.drawable.desk,
            R.drawable.chair,
            R.drawable.desk
    };

    String[] prices = {
            "10000",
            "20000",
            "10000",
            "20000",
            "10000",
            "20000",
            "10000",
            "20000"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomList adapter = new CustomList(MainActivity.this);
        gridView = findViewById(R.id.main_gridview);
        gridView.setAdapter(adapter);
    }
    public class CustomList extends ArrayAdapter<String>{
        private  final Activity mContext;
        public CustomList(Activity mContext){
            super(mContext, R.layout.item_main, names);
            this.mContext = mContext;
        }
        @Override
        public View getView(int position, View view, ViewGroup viewGroup){
            LayoutInflater inflater = mContext.getLayoutInflater();
            View columnView = inflater.inflate(R.layout.item_main, null, true);
            ImageView imageView = columnView.findViewById(R.id.img_gridview);
            TextView name = columnView.findViewById(R.id.name_gridview);
            TextView price = columnView.findViewById(R.id.price_gridview);

            imageView.setImageResource(imgs[position]);
            name.setText(names[position]);
            price.setText(prices[position]);

            return columnView;
        }
    }
}
