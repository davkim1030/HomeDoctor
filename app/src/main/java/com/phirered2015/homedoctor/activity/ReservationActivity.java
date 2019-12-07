package com.phirered2015.homedoctor.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phirered2015.homedoctor.R;

public class ReservationActivity extends AppCompatActivity {
    TextView mdate, mname, mphone, mtime;
    LinearLayout mload_success;
    RelativeLayout mload_fail;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        mdate = findViewById(R.id.reservation_date);
        mname = findViewById(R.id.reservation_name);
        mphone = findViewById(R.id.reservation_phone);
        mtime = findViewById(R.id.reservation_time);
        mload_success = findViewById(R.id.load_success);
        mload_fail = findViewById(R.id.load_fail);
    }

    protected void onStart() {
        super.onStart();
        mRef.child("user").child(UID).child("reservation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String text1 = dataSnapshot.child("date").getValue(String.class);
                    String text2 = dataSnapshot.child("name").getValue(String.class);
                    String text3 = dataSnapshot.child("phone").getValue(String.class);
                    String text4 = dataSnapshot.child("time").getValue(String.class);
                    mdate.setText(text1);
                    mname.setText(text2);
                    mphone.setText(text3);
                    mtime.setText(text4);

                    mload_success.setVisibility(View.VISIBLE);
                    mload_fail.setVisibility(View.INVISIBLE);
                }
                else {
                    mload_success.setVisibility(View.INVISIBLE);
                    mload_fail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                }
        });



    }
}
