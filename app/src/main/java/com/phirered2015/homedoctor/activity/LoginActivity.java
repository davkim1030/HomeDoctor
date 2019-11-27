package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.phirered2015.homedoctor.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Context mContext;
    private FirebaseAuth mAuth;
    final String TAG = "DEBUGING";
    Button login, signup;
    private EditText id, pwd;
    CheckBox idSave, autoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceStated){
        super.onCreate(savedInstanceStated);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mContext = this;

        id = (EditText) findViewById(R.id.idInput);
        pwd = (EditText) findViewById(R.id.pwdInput);
        login = (Button) findViewById(R.id.loginBtn);
        signup = (Button) findViewById(R.id.registerBtn);
        idSave = (CheckBox) findViewById(R.id.checkIdsave);
        autoLogin = (CheckBox) findViewById(R.id.checkAutologin);

        //로그인 버튼 리스너
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 값이 다 있고 로그인 정보가 맞으면 로그인 정보를 가지고 MainActivity로 인텐트
                if (!id.getText().toString().isEmpty() && !pwd.getText().toString().isEmpty())
                    mAuth.signInWithEmailAndPassword(id.getText().toString(), pwd.getText().toString())
                            .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        setFirebaseSession();   // sharedPreference에 firebase_uid_pref라는 곳에 uid 저장함; 세션 유지
                                        Intent loginIntent = new Intent(mContext, MainActivity.class);
                                        loginIntent.putExtra("firebaseUser", user);
                                        startActivity(loginIntent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(mContext, "잘못된 아이디나 비밀번호입니다",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                else
                    Toast.makeText(mContext, "ID와 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
        //회원가입 버튼 리스너
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
    //체크박스 할당
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        SharedPreferences pref = getSharedPreferences("idpref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String idstr = id.getText().toString();
        String pwdstr = pwd.getText().toString();
        String loginid = pref.getString("id", null);
        String loginpwd = pref.getString("pwd", null);


        switch(view.getId()){
            case R.id.checkIdsave:
                // ID 저장 기능
                if (checked) {
                    if(idstr != null) {
                        editor.putString("id", idstr);
                        editor.commit();
                    }
                    else
                        return;
                }
                else {
                    editor.remove("id");
                    editor.commit();
                }
            case R.id.checkAutologin:
                // 자동 로그인 기능
                if(checked){
                    editor.putString("id", idstr);
                    editor.putString("pwd", pwdstr);
                    editor.putString("UID", mAuth.getUid());
                    editor.commit();
                   if(loginid != null && loginpwd != null){
                       if(loginid.equals(id.getText().toString()) && loginpwd.equals(pwd.getText().toString())){
                            Intent loginintent = new Intent(mContext, MainActivity.class);
                            startActivity(loginintent);
                            finish();
                       }
                   }
                }

                else {
                    editor.remove("id");
                    editor.remove("pwd");
                    editor.commit();
                }
        }
    }

    void setFirebaseSession(){
        SharedPreferences pref = getSharedPreferences("firebase_uid_pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("UID", mAuth.getUid());
        editor.apply();
    }
}


