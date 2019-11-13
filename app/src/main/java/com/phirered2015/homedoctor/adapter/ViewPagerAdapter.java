package com.phirered2015.homedoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.item.TutorialItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<TutorialItem> itemList;

    public ViewPagerAdapter(Context context, ArrayList<TutorialItem> list) {
        mContext = context;
        itemList = list;
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
    public Object instantiateItem(ViewGroup container, int position){
        View view = null;

        if(mContext != null){
            LayoutInflater inflater
                    = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_tutorial, container, false);

            ImageView imageView = view.findViewById(R.id.img_desc);
            TextView textView = view.findViewById(R.id.txt_desc);

            imageView.setImageDrawable(itemList.get(position).getImgDesc());
            textView.setText(itemList.get(position).getTxtDesc());
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }


}
