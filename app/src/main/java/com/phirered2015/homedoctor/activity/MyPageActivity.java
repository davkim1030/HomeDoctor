package com.phirered2015.homedoctor.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.phirered2015.homedoctor.R;


public class MyPageActivity extends AppCompatActivity {

    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceStated){
        super.onCreate(savedInstanceStated);
        setContentView(R.layout.activity_mypage);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
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
