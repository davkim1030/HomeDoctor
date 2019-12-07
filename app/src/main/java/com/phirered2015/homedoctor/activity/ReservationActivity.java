package com.phirered2015.homedoctor.activity;

import android.os.Bundle;
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
    }

    protected void onStart() {
        super.onStart();

        mRef.child("user").child(UID).child("reservation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text1 = dataSnapshot.child("date").getValue(String.class);
                mdate.setText(text1);
                String text2 = dataSnapshot.child("name").getValue(String.class);
                mname.setText(text2);
                String text3 = dataSnapshot.child("phone").getValue(String.class);
                mphone.setText(text3);
                String text4 = dataSnapshot.child("time").getValue(String.class);
                mtime.setText(text4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
