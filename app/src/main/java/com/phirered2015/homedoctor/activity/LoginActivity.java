package com.phirered2015.homedoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.phirered2015.homedoctor.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button login, signup;
    private EditText id, pwd;
    CheckBox idsave, autologin;
    @Override
    protected void onCreate(Bundle savedInstanceStated){

        super.onCreate(savedInstanceStated);
        setContentView(R.layout.activity_login);
        id = (EditText) findViewById(R.id.idInput);
        pwd = (EditText) findViewById(R.id.pwdInput);
        login = (Button) findViewById(R.id.loginBtn);
        signup = (Button) findViewById(R.id.registerBtn);
        idsave = (CheckBox) findViewById(R.id.checkIdsave);
        autologin = (CheckBox) findViewById(R.id.checkAutologin);

        //로그인 버튼 리스너
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "로그인작동", Toast.LENGTH_SHORT).show();
            }
        });
        //회원가입 버튼 리스너
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //체크박스 할당
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()){
            case R.id.checkIdsave:
                if (checked)
                    Toast.makeText(getApplicationContext(), "ID 저장", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "ID 저장 취소", Toast.LENGTH_SHORT).show();
            case R.id.checkAutologin:
                if(checked)
                    Toast.makeText(getApplicationContext(), "자동 로그인", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "자동 로그인 취소", Toast.LENGTH_SHORT).show();
        }
    }
}


