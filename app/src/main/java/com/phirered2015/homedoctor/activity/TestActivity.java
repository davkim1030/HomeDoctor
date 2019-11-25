package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.phirered2015.homedoctor.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    Context mContext;

    Button[] btnList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mContext = this;
        btnList = new Button[11];
        btnList[0] = findViewById(R.id.btn_splash);
        btnList[1] = findViewById(R.id.btn_tutorial);
        btnList[2] = findViewById(R.id.btn_login);
        btnList[3] = findViewById(R.id.btn_sign_up);
        btnList[4] = findViewById(R.id.btn_main);
        btnList[5] = findViewById(R.id.btn_basket);
        btnList[6] = findViewById(R.id.btn_detail);
        btnList[7] = findViewById(R.id.btn_deliver_info);
        btnList[8] = findViewById(R.id.btn_pay_result);
        btnList[9] = findViewById(R.id.btn_deliver_state);
        btnList[10] = findViewById(R.id.btn_pay);


        // onClickListener 설정
        btnList[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SplashActivity.class));
            }
        });
        btnList[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TutorialActivity.class));
            }
        });
        btnList[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
        btnList[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SignUpActivity.class));
            }
        });
        btnList[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
        btnList[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, BasketActivity.class));
            }
        });
        btnList[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, DetailActivity.class));
            }
        });
        btnList[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PayInfoActivity.class));
            }
        });
        btnList[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PaySuccessActivity.class));
            }
        });
        btnList[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, DeliverStatusActivity.class));
            }
        });
        btnList[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PayActivity.class));
            }
        });

    }

    
}
