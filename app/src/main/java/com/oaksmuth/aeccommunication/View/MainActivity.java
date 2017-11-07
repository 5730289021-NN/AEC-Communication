package com.oaksmuth.aeccommunication.View;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

public class MainActivity extends AppCompatActivity implements PlayFragment.OnTopicSelectedListener{
    private  static final int HOME = 27;
    private  static final int DASHBOARD = 566;
    private  static final int NOTIFICATION = 901;

    private String backState;
    private short screenState;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.content, PlayFragment.newInstance()).commit();
                    screenState=HOME;
                    return true;
                case R.id.navigation_dashboard:
                    if(screenState<DASHBOARD){
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                    }else{
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                    }
                    fragmentTransaction.replace(R.id.content, TalkFragment.newInstance()).commit();
                    screenState=DASHBOARD;
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                    fragmentTransaction.replace(R.id.content, TeachFragment.newInstance()).commit();
                    screenState=NOTIFICATION;
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
        //To place content with Play
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayFragment playFragment = new PlayFragment();
        backState = playFragment.getClass().getName();
        fragmentTransaction.replace(R.id.content, playFragment).commit();
    }

    @Override
    public void onTopicSelected(Topic topic) {
        QAFragment qaFragment = QAFragment.newInstance();
        qaFragment.setTopic(topic);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(backState);
        fragmentTransaction.replace(R.id.content, qaFragment).commit();
    }
}
