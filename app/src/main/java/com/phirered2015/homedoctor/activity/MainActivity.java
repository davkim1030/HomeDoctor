package com.phirered2015.homedoctor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.phirered2015.homedoctor.R;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setCustomView(R.layout.app_bar_main);
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        NavigationView nv = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_mypage, R.id.nav_basket, R.id.nav_delivery, R.id.nav_booking).setDrawerLayout(drawer).build();
        NavController nc = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, nc, mAppBarConfiguration);
        NavigationUI.setupWithNavController(nv, nc);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public  boolean onSupportNavigateUp() {
         NavController nc = Navigation.findNavController(this, R.id.nav_host_fragment);
         return NavigationUI.navigateUp(nc, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
