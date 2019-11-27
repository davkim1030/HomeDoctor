package com.phirered2015.homedoctor.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.phirered2015.homedoctor.R;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawerLayout); // drawer 정보
        NavigationView nv = findViewById(R.id.drawer_navigation); // navigation 정보
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,
                R.id.nav_mypage, R.id.nav_basket, R.id.nav_delivery,
                R.id.nav_booking, R.id.nav_calling)
                .setDrawerLayout(drawer)
                .build();
        NavController nc = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, nc, mAppBarConfiguration);
        nv.setNavigationItemSelectedListener();
        NavigationUI.setupWithNavController(nv, nc);

    }
    /*
    onCreateOptionsMenu
    메뉴 확장
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /*
    onSupportNavigateUp
    navigation flow 관리
     */
    @Override
    public boolean onSupportNavigateUp() {
         NavController nc = Navigation.findNavController(this, R.id.nav_host_fragment);
         return NavigationUI.navigateUp(nc, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
    /*
    onNavigationItemSelected listener 함수
    Drawer에서 선택된 부분 동작 branch
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_mypage){
            Intent intent = new Intent(this, MyPageActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_basket) {
            Intent intent = new Intent(this, BasketActivity.class);
            startActivity(intent);
        }

        else if(id == R.id.nav_delivery){
            Intent intent = new Intent(this, DeliverStatusActivity.class);
            startActivity(intent);
        }

        else if(id == R.id.nav_booking){
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        } //TODO: 예약 activity 필요

        else if(id == R.id.nav_calling){
            String tel = "000000000";
            startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
