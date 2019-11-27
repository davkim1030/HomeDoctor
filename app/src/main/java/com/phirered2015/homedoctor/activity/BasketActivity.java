package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.phirered2015.homedoctor.R;

public class BasketActivity extends AppCompatActivity {
    ListView list;
    String[] titles = {
            "의자",
            "책상",
            "의자",
            "책상",
            "의자",
            "책상",
            "의자",
            "책상"
    } ;
    Integer[] images = {
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
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        CustomList adapter = new CustomList(BasketActivity.this);
        list= findViewById(R.id.shopping_list);
        list.setAdapter(adapter);
    }
    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context ) {
            super(context, R.layout.item_list, titles);
            this.context = context;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.item_list, null, true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
            TextView title = (TextView) rowView.findViewById(R.id.item_name);
            TextView price = (TextView) rowView.findViewById(R.id.item_price);

            title.setText(titles[position]);
            imageView.setImageResource(images[position]);
            price.setText(prices[position]);
            return rowView;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
