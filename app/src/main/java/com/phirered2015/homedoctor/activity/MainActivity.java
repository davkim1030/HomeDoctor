package com.phirered2015.homedoctor.activity;


import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.MainGridViewAdapter;
import com.phirered2015.homedoctor.item.MainGridItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    Context mContext;
    GridView gridView;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    ArrayList<MainGridItem> items = new ArrayList<>();
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        gridView = findViewById(R.id.main_gridview);

        /*
        mRef.child("product").addValueEventListener(new ValueEventListener(){...});
        파이어베이스 product/ 로부터 데이터 수신후 items에 추가
        DataSnapshot i = product/ 이하 모든 키
        i.child("product/ 이하 키 중 하나")로 이어가기
         */

        mRef.child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i: dataSnapshot.getChildren()){
                    StorageReference sr = FirebaseStorage.getInstance().getReference("thumbnail/" + i.child("thumbnail").getValue().toString());
                    items.add(new MainGridItem(sr, i.child("name").getValue().toString(),
                            i.child("price").getValue().toString()));
                }
                MainGridViewAdapter adapter = new MainGridViewAdapter(mContext, items);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        gridView.setAdapter(new ArrayAdapter<MainGridItem>(this, R.layout.item_main));

    }
}
