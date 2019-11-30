package com.phirered2015.homedoctor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.activity.BasketActivity;

public class DialogBasket {
    Context mContext;

    public DialogBasket(Context context){
        this.mContext = context;
    }

    public void callFunc(){
        final Dialog dlg = new Dialog(mContext);
        final int[] ctr = {0};
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_basket);
        dlg.show();

        final Button decrease = dlg.findViewById(R.id.decrease_btn2);
        final Button increase = dlg.findViewById(R.id.increase_btn2);
        final TextView count = dlg.findViewById(R.id.count_text2);
        final Button cancel = dlg.findViewById(R.id.cancel_btn2);
        final Button adder = dlg.findViewById(R.id.add_btn);

        count.setText(String.valueOf(ctr[0]));

        // 수량 0 일시 구매버튼 비활성화
        if(ctr[0] == 0){
            adder.setTextColor(Color.parseColor("#CCCCCC"));
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
                    adder.setTextColor(Color.parseColor("#CCCCCC"));
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctr[0]++;
                count.setText(String.valueOf(ctr[0]));
                adder.setTextColor(Color.parseColor("#1a75d2"));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

        adder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ctr[0] != 0){
                    Intent intent = new Intent(mContext, BasketActivity.class);
                    intent.putExtra("toAdd", ctr[0]);
                    mContext.startActivity(intent);
                    ctr[0] = 0;
                    dlg.dismiss();
                }
            }
        });
    }
}
