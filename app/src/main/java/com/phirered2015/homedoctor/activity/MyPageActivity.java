package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phirered2015.homedoctor.R;


public class MyPageActivity extends AppCompatActivity {

    String UID;
    Button btnLogout, btnSave, btnCancel;
    Context mContext;
    EditText editName, editPhone1, editPhone2, editPhone3, editPost, editAddress, editDetail;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceStated){
        super.onCreate(savedInstanceStated);
        setContentView(R.layout.activity_mypage);
        mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        editName = findViewById(R.id.edit_name);
        editPhone1 = findViewById(R.id.edit_phone_1);
        editPhone2 = findViewById(R.id.edit_phone_2);
        editPhone3 = findViewById(R.id.edit_phone_3);
        editPost = findViewById(R.id.edit_post_num);
        editAddress = findViewById(R.id.edit_address);
        editDetail = findViewById(R.id.edit_detail_address);

        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnLogout = findViewById(R.id.btn_logout);

        mRef.child("user").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editName.setText(dataSnapshot.child("name").getValue().toString());
                String[] phone = dataSnapshot.child("phone").getValue().toString().split("-");
                editPhone1.setText(phone[0]);
                editPhone2.setText(phone[1]);
                editPhone3.setText(phone[2]);
                editPost.setText(dataSnapshot.child("post_num").getValue().toString());
                editAddress.setText(dataSnapshot.child("address").getValue().toString());
                editDetail.setText(dataSnapshot.child("detail_address").getValue().toString());
                btnSave.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child("user").child(UID).child("name").setValue(editName.getText().toString());
                mRef.child("user").child(UID).child("phone").setValue(
                        editPhone1.getText().toString() + "-" + editPhone2.getText().toString() + "-"
                        + editPhone3.getText().toString()
                );
                mRef.child("user").child(UID).child("post_num").setValue(editPost.getText().toString());
                mRef.child("user").child(UID).child("address").setValue(editAddress.getText().toString());
                mRef.child("user").child(UID).child("detail_address").setValue(editDetail.getText().toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPageActivity.this.finish();
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
