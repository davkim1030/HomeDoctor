package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phirered2015.homedoctor.R;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    final String TAG = "debuging";
    private FirebaseAuth mAuth;
    Context mContext;
    Spinner spinnerPhone1;
    EditText editMail, editPhone2, editPhone3, editPwd, editName, editPost, editAddress, editDetailAddress;
    Button btnSignUp, btnSearchPost;
    ArrayAdapter arrayAdapter;

    WebView webView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mContext = this;

        editMail = findViewById(R.id.edit_mail);
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
                if(isValid()){
                    final String email = editMail.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, editPwd.getText().toString())
                            .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
                                        // 데이터베이스 스키마에 "."이 못 들어가므로 .을 !로 대체
                                        mDatabase.child(email.replace(".","!"));
                                        mDatabase = mDatabase.child(email.replace(".","!"));
                                        mDatabase.child("name").setValue(editName.getText().toString());
                                        mDatabase.child("post_num").setValue(editPost.getText().toString());
                                        mDatabase.child("address").setValue(editAddress.getText().toString());
                                        mDatabase.child("detail_address").setValue(editDetailAddress.getText().toString());
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(mContext, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

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

    // 텍스트 필드 값들이 유효한지 확인하는 메소드
    boolean isValid(){
        if(!editMail.getText().toString().isEmpty() &&
           !editPwd.getText().toString().isEmpty() &&
           !editName.getText().toString().isEmpty() &&
           !editPost.getText().toString().isEmpty() &&
           !editAddress.getText().toString().isEmpty() &&
           !editDetailAddress.getText().toString().isEmpty()){
            return editMail.getText().toString().contains("@") &&
                    editMail.getText().toString().contains(".");
        } else
            return false;
    }
}
