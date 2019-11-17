package com.phirered2015.homedoctor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.adapter.ViewPagerAdapter;
import com.phirered2015.homedoctor.item.TutorialItem;
import com.phirered2015.homedoctor.layout.CircleAnimIndicator;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<TutorialItem> tutorialItems;
    private CircleAnimIndicator indicator;
    ImageButton btnLeft, btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getSupportActionBar().hide();

        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != tutorialItems.size() - 1)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        initLayout();
        init();
    }


    private void initLayout() {
        viewPager = findViewById(R.id.viewpager_tutorial);
        indicator = findViewById(R.id.indicator);
    }

    // 레이아웃 초기화
    private void init() {
        initData();
        initViewPager();
    }

    // 데이터 초기화
    private void initData() {
        tutorialItems = new ArrayList<>();
        tutorialItems.add(new TutorialItem(getDrawable(R.drawable.ic_launcher_background), "1"));
        tutorialItems.add(new TutorialItem(getDrawable(R.drawable.ic_launcher_background), "2"));
        tutorialItems.add(new TutorialItem(getDrawable(R.drawable.ic_launcher_background), "3"));
        tutorialItems.add(new TutorialItem(getDrawable(R.drawable.ic_launcher_background), "4"));
        tutorialItems.add(new TutorialItem(getDrawable(R.drawable.ic_launcher_background), "5"));
        tutorialItems.add(new TutorialItem(getDrawable(R.drawable.ic_launcher_background), "시작하기->", LoginActivity.class));
    }

    // viewpager 초기화
    private void initViewPager() {
        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(getApplicationContext(), tutorialItems);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);

        initIndicator();
    }

    // indicator 초기화
    private void initIndicator(){
        //원사이의 간격
//        indicator.setItemMargin(15);
        //애니메이션 속도
        indicator.setAnimDuration(300);
        //indecator 생성
        indicator.createDotPanel(tutorialItems.size(), R.drawable.ic_indicator_none_48dp , R.drawable.ic_indicator_on_48dp);
    }

    // ViewPager 전환시 호출되는 메서드
    private ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.e("TAG", "selected");
            indicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
