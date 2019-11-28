package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phirered2015.homedoctor.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    Context mContext;
    String UID;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    String posstr;
    String prefix = "00";
    int pos = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mContext = this;
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");

        // 뷰 선언
        final ImageView topImage = findViewById(R.id.detailImage);
        final TextView productName = findViewById(R.id.productName);
        final TextView productPrice = findViewById(R.id.productPrice);
        final TextView descText = findViewById(R.id.descText);
        final ImageView descImage = findViewById(R.id.detailDescimage);
        Button cart = findViewById(R.id.detailCartBtn);
        Button purchase = findViewById(R.id.detailPurchaseBtn);

        //이전 intent로 부터 값 전달 받음
        Intent intent = getIntent();
        pos = intent.getExtras().getInt("gridpos");
        int i = pos + 1;
        posstr = prefix + i; // 그리드뷰 위치 변수; Main activity로 부터 넘어옴; MainGridViewAdapter에 구현

        /*
        mRef.child("product").child(posstr).addValueEventListener(new ValueEventListener(){...});
        파이어베이스 product/posstr(해당 그리드 위치)에서 데이터 수신 후 View로 표출
        dataSnapshot = possrt(해당 그리드 위치)
         */

        mRef.child("product").child(posstr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StorageReference topimg = FirebaseStorage.getInstance().getReference("thumbnail/" + dataSnapshot.child("thumbnail").getValue());
                StorageReference descimg = FirebaseStorage.getInstance().getReference("desc_img/" + dataSnapshot.child("desc_images").child("0").getValue());
                Glide.with(mContext).load(topimg).into(topImage);
                productName.setText((String) dataSnapshot.child("name").getValue());
                productPrice.setText((String) dataSnapshot.child("price").getValue());
                descText.setText((String) dataSnapshot.child("description").getValue());
                Glide.with(mContext).load(descimg).into(descImage);
                Log.e("text", descimg.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //장바구니 버튼 제어
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                startActivity(intent);
            }
        });
        //구매하기 버튼 제어
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PayInfoActivity.class);
                startActivity(intent);
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
