package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phirered2015.homedoctor.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PaySuccessActivity extends AppCompatActivity {
    String UID;
    TextView txtName,txtPhone, txtPost, txtAddress, txtDetailAddress;
    Button btnMain;
    String itemName, itemPrice;
    Context mContext;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
    DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        mContext = this;
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        Intent intent = getIntent();

        itemName = intent.getStringExtra("itemName");
        itemPrice = intent.getStringExtra("itemPrice");

        txtName = findViewById(R.id.text_name);
        txtPhone = findViewById(R.id.text_phone);
        txtPost = findViewById(R.id.text_post_num);
        txtAddress = findViewById(R.id.text_address);
        txtDetailAddress = findViewById(R.id.text_detail_address);
        btnMain = findViewById(R.id.btn_main);

        mRef2.child(UID).child("basket").removeValue();
        mRef.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtName.setText(dataSnapshot.child("name").getValue().toString());
                txtPhone.setText(dataSnapshot.child("phone").getValue().toString());
                txtPost.setText(dataSnapshot.child("post_num").getValue().toString());
                txtAddress.setText(dataSnapshot.child("address").getValue().toString());
                txtDetailAddress.setText(dataSnapshot.child("detail_address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
