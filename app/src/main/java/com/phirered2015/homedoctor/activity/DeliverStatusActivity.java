package com.phirered2015.homedoctor.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.DeliverListAdapter;
import com.phirered2015.homedoctor.item.DeliverStateItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DeliverStatusActivity extends AppCompatActivity {
    Context mContext;
    ListView listView;
    ProgressBar progressBar;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    ArrayList<DeliverStateItem> items = new ArrayList<>();
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_status);
        mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        listView = findViewById(R.id.list_deliver_state);
        progressBar = findViewById(R.id.progress_circular);

        Log.e("data", mRef.child("user").child(UID).child("purchased").toString());
        mRef.child("user").child(UID).child("purchased").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot i: dataSnapshot.getChildren()){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("thumbnail/" + i.child("thumbnail").getValue().toString());
                    items.add(new DeliverStateItem(i.child("title").getValue().toString(),
                            i.child("status").getValue().toString(),
                            Integer.valueOf(i.child("amount").getValue().toString()), storageReference)
                    );
                }
                DeliverListAdapter adapter = new DeliverListAdapter(mContext, items);
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("amount", "error");

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
