package com.phirered2015.homedoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.BasketAdapter;
import com.phirered2015.homedoctor.item.DeliverStateItem;

import java.io.Serializable;
import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    ListView listView;
    CheckBox checkAll;
    Button btnDelete, btnPurchase;
    TextView txtTotalAmount;
    ArrayList<DeliverStateItem> items = new ArrayList<>();
    String UID;
    Context mContext;
    ProgressBar progressBar;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        mContext = this;
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");

        checkAll = findViewById(R.id.checkbox_all);
        btnDelete = findViewById(R.id.delete_all);
        btnPurchase = findViewById(R.id.order);
        txtTotalAmount = findViewById(R.id.txt_total_amount);

        listView = findViewById(R.id.shopping_list);
        progressBar = findViewById(R.id.progress_circular);


        // db에서 데이터 가져와서 list뷰에 넣는 부분
        mRef.child("user").child(UID).child("basket").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    items.clear();
                    for(DataSnapshot i: dataSnapshot.getChildren()){
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference("thumbnail/" + i.getKey() + ".jpg");
                        items.add(new DeliverStateItem(i.getKey(),
                                i.child("quantity").getValue().toString(),
                                Integer.valueOf(i.child("price").getValue().toString())
                                , storageReference)
                        );
                    }
                    BasketAdapter adapter = new BasketAdapter(mContext, items);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("amount", "error");

            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payIntent = new Intent(mContext, PayInfoActivity.class);
                ArrayList<String> arrayList = new ArrayList<>();
                for (DeliverStateItem i: items) {
                    arrayList.add(i.getName());
                }
                payIntent.putExtra("nameList", arrayList);
                startActivity(payIntent);
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
