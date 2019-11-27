package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.phirered2015.homedoctor.R;


public class MyPageActivity extends AppCompatActivity {

    String UID;
    Button btnLogout;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceStated){
        super.onCreate(savedInstanceStated);
        setContentView(R.layout.activity_mypage);
        mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        btnLogout = findViewById(R.id.btn_logout);

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
