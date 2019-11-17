package com.phirered2015.homedoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.phirered2015.homedoctor.R;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


        // MainActivity.class 자리에 다음에 넘어갈 액티비티를 넣어주기
        mRunnable = new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                startActivity(intent);
                finish();
            }
        };

        // Handler를 통한 대기시간 설정
        // (임의로 출력 확인을 위해 적은것 뿐, 원래는 앱 로딩 끝나면 바로 넘어가게 되어있음)
        mHandler = new Handler();
        // mRunnable 내부 run() 실행하려면 기다려야 하는 delayMillis
        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
