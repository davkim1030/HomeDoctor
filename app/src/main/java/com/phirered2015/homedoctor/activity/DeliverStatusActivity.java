package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.DeliverListAdapter;
import com.phirered2015.homedoctor.item.DeliverStateItem;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DeliverStatusActivity extends AppCompatActivity {

    Context mContext;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_status);
        mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        listView = findViewById(R.id.list_deliver_state);
        ArrayList<DeliverStateItem> items = new ArrayList<>();
        items.add(new DeliverStateItem("의자 외 1개 상품", "결제 완료", 55000, R.drawable.chair));
        items.add(new DeliverStateItem("책상 외 1개 상품", "배송중", 22000, R.drawable.desk));
        items.add(new DeliverStateItem("의자", "배송 완료", 11000, R.drawable.chair));
        items.add(new DeliverStateItem("의자 외 1개 상품", "결제 완료", 55000, R.drawable.chair));
        items.add(new DeliverStateItem("책상 외 1개 상품", "배송중", 22000, R.drawable.desk));
        items.add(new DeliverStateItem("의자", "배송 완료", 11000, R.drawable.chair));
        items.add(new DeliverStateItem("의자 외 1개 상품", "결제 완료", 55000, R.drawable.chair));
        items.add(new DeliverStateItem("책상 외 1개 상품", "배송중", 22000, R.drawable.desk));
        items.add(new DeliverStateItem("의자", "배송 완료", 11000, R.drawable.chair));

        DeliverListAdapter adapter = new DeliverListAdapter(mContext, items);
        listView.setAdapter(adapter);
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
