package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class PayInfoActivity extends AppCompatActivity {
    ListView list;
    Context mContext;
    Integer[] images = {
            R.drawable.chair,
            R.drawable.desk
    };
    String UID, itemCode, itemPrice;
    int totalAmount = 0, totalCount = 0;
    EditText editName, editPhone1, editPhone2, editPhone3, editPost, editAddress, editDetailAddress;
    TextView txtTotalPrice;
    Button btnPurchase;
    ArrayList<String> payInfo;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_pay_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        CustomList adapter = new CustomList(PayInfoActivity.this);
        final Intent intent = getIntent();
        payInfo = intent.getStringArrayListExtra("nameList");
        // TODO: 바로 구매하기 버튼 눌렀을 시, intent 받는 부분 필요
        itemCode = intent.getStringExtra("itemCode");
        itemPrice = intent.getStringExtra("itemPrice");
        list= findViewById(R.id.shopping_list);
        list.setAdapter(adapter);

        editName = findViewById(R.id.edit_name);
        editPhone1 = findViewById(R.id.edit_phone_1);
        editPhone2 = findViewById(R.id.edit_phone_2);
        editPhone3 = findViewById(R.id.edit_phone_3);
        editPost = findViewById(R.id.edit_post_num);
        editAddress = findViewById(R.id.edit_address);
        editDetailAddress = findViewById(R.id.edit_detail_address);
        btnPurchase = findViewById(R.id.btn_purchase);
        txtTotalPrice = findViewById(R.id.total_price);

        // 유저 데이터 가져와서 넣는 부분
        mRef.child("user").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editName.setText(dataSnapshot.child("name").getValue().toString());
                editPhone1.setText(dataSnapshot.child("phone").getValue().toString().split("-")[0]);
                editPhone2.setText(dataSnapshot.child("phone").getValue().toString().split("-")[1]);
                editPhone3.setText(dataSnapshot.child("phone").getValue().toString().split("-")[2]);
                editPost.setText(dataSnapshot.child("post_num").getValue().toString());
                editAddress.setText(dataSnapshot.child("address").getValue().toString());
                editDetailAddress.setText(dataSnapshot.child("detail_address").getValue().toString());

                btnPurchase.setEnabled(true);
                btnPurchase.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("amount", "error");

            }
        });

        // 결제 버튼 클릭시
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()){
                    Intent payIntent = new Intent(mContext, PayActivity.class);
                    payIntent.putExtra("itemCode", itemCode);
                    payIntent.putExtra("itemQuantity", String.valueOf(totalCount));
                    payIntent.putExtra("itemPrice", String.valueOf(totalAmount));
                    if(payInfo.size() != 1)
                        payIntent.putExtra("itemName", payInfo.get(0) + "외 " + (payInfo.size() - 1) + "개 상품");
                    else
                        payIntent.putExtra("itemName", payInfo.get(0));
                    startActivity(payIntent);
                } else {
                    Toast.makeText(PayInfoActivity.this, "모든 데이터를 다 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context ) {
            super(context, R.layout.item_list_read_only, payInfo);
            this.context = context;
        }

        @Override
        public int getCount() {
            return payInfo.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final int pos = position;

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_list_read_only, parent, false);
            }
            ImageView imageView = view.findViewById(R.id.image);
            TextView title = view.findViewById(R.id.item_name);
            TextView price = view.findViewById(R.id.item_price);
            TextView quantity = view.findViewById(R.id.item_count);

            String[] detail = payInfo.get(pos).split(":");
            totalAmount += Integer.valueOf(detail[2]); // intent 넘겨주기 위한 총 가격
            totalCount += Integer.valueOf(detail[1]);
            txtTotalPrice.setText("" + String.valueOf(totalAmount));
            Log.e("totalAmount", String.valueOf(totalAmount));
            title.setText(detail[0]);
            quantity.setText(detail[1]);
            // TODO: 이미지 넘겨받는거 구상
            imageView.setImageResource(images[position]);
            price.setText(detail[2]);
            return view;
        }
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

    boolean isValid(){
        return (!editName.getText().toString().isEmpty() &&
                !editPhone1.getText().toString().isEmpty() &&
                !editPhone2.getText().toString().isEmpty() &&
                !editPhone3.getText().toString().isEmpty() &&
                !editPost.getText().toString().isEmpty() &&
                !editAddress.getText().toString().isEmpty() &&
                !editDetailAddress.getText().toString().isEmpty());
    }
}
