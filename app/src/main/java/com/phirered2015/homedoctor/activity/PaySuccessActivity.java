package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.phirered2015.homedoctor.R;

import androidx.appcompat.app.AppCompatActivity;

public class PaySuccessActivity extends AppCompatActivity {
    String UID;
    TextView txtName,txtPhone, txtPost, txtAddress, txtDetailAddress;
    Button btnMain;
    String itemName, itemPrice;
    Context mContext;

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


        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(mContext, MainActivity.class));
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
