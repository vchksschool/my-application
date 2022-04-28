package com.mtc.top5;

import android.content.Context;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mtc.top5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Context mContext;

    public static  int popupcount = 0;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;
    private TextView drawname, drawname2;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch(menuItem.getItemId())
                    {
                        case R.id.navigation_home:
                            setFragment(new CategoryFragment());
                            return true;

                        case R.id.navigation_leaderboard:
                            setFragment(new LeaderboardFragment());
                            return true;


                        case R.id.navigation_account:
                            setFragment(new AccountFragment());
                            return true;

                    }

                    return false;
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.appBarMain.toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame =  findViewById(R.id.main_frame);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Categories");
        //DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        System.out.println("main acitivy here");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_account, R.id.navigation_leaderboard)
                .setOpenableLayout(drawer)
                .build();




        System.out.println("main acitivy here");
        //popupcount += 1;
        //if (popupcount ==1){
            //loadpopup
            //in loadpopup start main activtiy after loaded
        //}
        //else{
            //setfragement
        //}
        setFragment(new CategoryFragment());
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId())
        {
            case R.id.nav_home:
                setFragment(new CategoryFragment());


                return true;

            case R.id.nav_leaderboard:
                setFragment(new LeaderboardFragment());
                return true;


            case R.id.nav_account:
                setFragment(new AccountFragment());
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    private void setFragment(Fragment fragement)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragement);

        transaction.commit();
    }










}