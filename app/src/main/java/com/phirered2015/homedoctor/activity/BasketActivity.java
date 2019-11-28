package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.BasketAdapter;
import com.phirered2015.homedoctor.item.DeliverStateItem;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<DeliverStateItem> items = new ArrayList<>();
    String UID;
    Context mContext;
    ProgressBar progressBar;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");

        listView = findViewById(R.id.shopping_list);
        progressBar = findViewById(R.id.progress_circular);

        mRef.child("user").child(UID).child("basket").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot i: dataSnapshot.getChildren()){
                    String exec = Integer.valueOf(i.getKey()) <= 20 || Integer.valueOf(i.getKey()) > 40 ? ".jpg" : ".PNG";
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("thumbnail/" + i.getKey() + exec);
                    items.add(new DeliverStateItem(i.getKey(),
                            i.getValue().toString(),
                            100, storageReference)
                    );
                }
                BasketAdapter adapter = new BasketAdapter(mContext, items);
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("amount", "error");

            }
        });

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
