package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

        switch(view.getId()){
            case R.id.checkIdsave:
                // TODO: ID 저장 기능 구현 필요
                if (checked)
                    Toast.makeText(getApplicationContext(), "ID 저장", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "ID 저장 취소", Toast.LENGTH_SHORT).show();
            case R.id.checkAutologin:
                // TODO: 자동 로그인 기능 구현 필요
                if(checked)
                    Toast.makeText(getApplicationContext(), "자동 로그인", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "자동 로그인 취소", Toast.LENGTH_SHORT).show();
        }
    }
}


