package com.phirered2015.homedoctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.activity.SignUpActivity;
import com.phirered2015.homedoctor.item.TutorialItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<TutorialItem> itemList;
    private Activity activity;
    public ViewPagerAdapter(Context context, ArrayList<TutorialItem> list, Activity activity) {
        mContext = context;
        itemList = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position){
        View view = null;

        if(mContext != null){
            LayoutInflater inflater
                    = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_tutorial, container, false);

            ImageView imageView = view.findViewById(R.id.img_desc);
            TextView textView = view.findViewById(R.id.txt_desc);

            imageView.setImageDrawable(itemList.get(position).getImgDesc());
            textView.setText(itemList.get(position).getTxtDesc());

            if(itemList.get(position).getActivity() != null){
                textView.setTextColor(view.getResources().getColor(R.color.colorAccent));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, itemList.get(position).getActivity());
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);

                        activity.finish();
                    }
                });
            }
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }



}
