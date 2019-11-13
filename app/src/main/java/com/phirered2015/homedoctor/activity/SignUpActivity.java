package com.phirered2015.homedoctor.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.phirered2015.homedoctor.R;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    Spinner spinnerPhone1;
    EditText editPhone2, editPhone3, editPwd, editName, editPost, editAddress, editDetailAddress;
    Button btnSignUp, btnSearchPost;

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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


    }
}
