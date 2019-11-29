package com.phirered2015.homedoctor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.activity.PayInfoActivity;

public class DialogPurchase {
    Context mContext;

    public DialogPurchase(Context context){
        this.mContext = context;
    }

    //다이얼로그 호출 함수
    public void callFunc(){
        final Dialog dlg = new Dialog(mContext);
        final int[] ctr = {0};
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_purchase);
        dlg.show();

        final Button decrease = dlg.findViewById(R.id.decrease_btn);
        final Button increase = dlg.findViewById(R.id.increase_btn);
        final TextView count = dlg.findViewById(R.id.count_text);
        final Button cancel = dlg.findViewById(R.id.cancel_btn);
        final Button purchase = dlg.findViewById(R.id.purchase_btn);

        count.setText(String.valueOf(ctr[0]));

        // 수량 0 일시 구매버튼 비활성화
        if(ctr[0] == 0){
            purchase.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //수량 0보다 클때만 카운터 감소
                if(ctr[0] > 0){
                    ctr[0]--;
                    count.setText(String.valueOf(ctr[0]));
                }

                //수량 0으로 돌아올 시 버튼 비활성화
                if(count.getText().toString().equals("0"))
                    purchase.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 수량 제한 있으면 변경
                ctr[0]++;
                count.setText(String.valueOf(ctr[0]));
                purchase.setBackgroundColor(Color.parseColor("#1a75d2"));

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //수량 0이 아닐때만 화면 전환
                if(ctr[0] != 0){
                    Intent intent = new Intent(mContext, PayInfoActivity.class);
                    intent.putExtra("toBuy", ctr[0]);
                    mContext.startActivity(intent);

                }
            }
        });
    }
}

