package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.DeliverListAdapter;
import com.phirered2015.homedoctor.item.DeliverStateItem;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DeliverStatusActivity extends AppCompatActivity {

    Context mContext;
    ListView listView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    ArrayList<DeliverStateItem> items = new ArrayList<>();
    String uid = "hCCBV48ChFTR5AQOZ29QhImL4F12";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_status);
        mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        listView = findViewById(R.id.list_deliver_state);

        // TODO: 로그인 정보는 앱 세션을 통해 넘겨야 함

        Log.e("data", mRef.child("user").child(uid).child("purchased").toString());
        mRef.child("user").child(uid).child("purchased").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i: dataSnapshot.getChildren()){
                    items.add(new DeliverStateItem(String.valueOf(i.child("items").getChildrenCount()),
                            i.child("status").getValue().toString(),
                            Integer.valueOf(i.child("amount").getValue().toString()),
                            LoadImageFromWebOperations(i.child("thumbnail").getValue().toString())));
                    Log.e("data", i.toString());
                    Log.e("amount", i.child("amount").getValue().toString());
                    Log.e("status", i.child("status").getValue().toString());
                }
                DeliverListAdapter adapter = new DeliverListAdapter(mContext, items);
                listView.setAdapter(adapter);
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

    private Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }

}
