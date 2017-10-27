package com.oaksmuth.aeccommunication.View;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.oaksmuth.aeccommunication.R;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content, new PlayFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.content, new TalkFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.content, new TeachFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.content, new PlayFragment()).commit();

    }

}