package com.phirered2015.homedoctor.activity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setId(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:010-3387-4686"));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.menu_logout:
                final AlertDialog.Builder logoutDialog = new AlertDialog.Builder(mContext);
                logoutDialog.setTitle("로그아웃 하시겠습니까?");
                logoutDialog.setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences mPref = getSharedPreferences("firebase_uid_pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPref.edit();
                        editor.remove("UID");
                        editor.apply();
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    }
                });
                logoutDialog.show();
                break;
            case R.id.menu_mypage:
                Intent mypageintent = new Intent(mContext, MyPageActivity.class);
                startActivity(mypageintent);
                break;
            case R.id.menu_basket:
                Intent basketintent = new Intent(mContext, BasketActivity.class);
                startActivity(basketintent);
                break;
            case R.id.menu_delivery:
                Intent delveryintent = new Intent(mContext, DeliverStatusActivity.class);
                startActivity(delveryintent);
                break;
            case R.id.menu_booking:
                //TODO: 예약확인 엑티비티 연결
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
