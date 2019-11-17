package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.phirered2015.homedoctor.R;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    Context mContext;
    Spinner spinnerPhone1;
    EditText editPhone2, editPhone3, editPwd, editName, editPost, editAddress, editDetailAddress;
    Button btnSignUp, btnSearchPost;
    ArrayAdapter arrayAdapter;

    WebView webView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mContext = this;

        spinnerPhone1 = findViewById(R.id.spinner_phone_1);
        editPhone2 = findViewById(R.id.edit_phone_2);
        editPhone3 = findViewById(R.id.edit_phone_3);
        editPwd = findViewById(R.id.edit_pwd);
        editName = findViewById(R.id.edit_name);
        editPost = findViewById(R.id.edit_post_num);
        editAddress = findViewById(R.id.edit_address);
        editDetailAddress = findViewById(R.id.edit_detail_address);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnSearchPost = findViewById(R.id.btn_search_post);

        // Spinner 값 추가
        ArrayList phoneList = new ArrayList<>();
        phoneList.addAll(Arrays.asList("010", "011", "017", "019"));
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, phoneList);
        spinnerPhone1.setAdapter(arrayAdapter);


        btnSearchPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_webView();
                handler = new Handler();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: firebase 연동하여 로그인 구현
                finish();
            }
        });
    }

    public void init_webView() {
        // WebView 설정
        webView = findViewById(R.id.webview_post);
        webView.setVisibility(View.VISIBLE);
        // JavaScript 허용
        webView.loadUrl("http://poipoi.online/api/daum_post_api.php");
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);


        // webview url load. php 파일 주소

    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    editPost.setText(arg1);
                    editAddress.setText(arg2);
                    editDetailAddress.setText(arg3);

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            Log.d("check URL", url);
            view.loadUrl(url);
            return true;
        }
    }


}
