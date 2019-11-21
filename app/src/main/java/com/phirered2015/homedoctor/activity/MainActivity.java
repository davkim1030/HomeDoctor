package com.phirered2015.homedoctor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.phirered2015.homedoctor.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setCustomView(R.layout.app_bar_main);

        DrawerLayout drawer = findViewById(R.id.drawerLayout); // drawer 정보
        NavigationView nv = findViewById(R.id.nav_view); // navigation 정보
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_mypage, R.id.nav_basket, R.id.nav_delivery, R.id.nav_booking, R.id.nav_calling).setDrawerLayout(drawer).build();
        NavController nc = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, nc, mAppBarConfiguration);
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
    public  boolean onSupportNavigateUp() {
         NavController nc = Navigation.findNavController(this, R.id.nav_host_fragment);
         return NavigationUI.navigateUp(nc, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
    /*
    onNavigationItemSelected listener 함수
    Drawer에서 선택된 부분 동작 branch
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id =item.getItemId();
        switch (id) {
            case R.id.nav_mypage:
                break;
            case R.id.nav_basket:
                break;
            case R.id.nav_delivery:
                break;
            case R.id.nav_booking:
                break;
            case R.id.nav_calling:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
