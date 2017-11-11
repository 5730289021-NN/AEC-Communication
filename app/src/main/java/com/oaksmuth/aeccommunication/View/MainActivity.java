package com.oaksmuth.aeccommunication.View;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

public class MainActivity extends AppCompatActivity implements PlayFragment.OnTopicSelectedListener{
    private static final int PLAY = 27;
    private static final int TALK = 566;
    private static final int TEACH = 901;

    private String backState;
    private short screenState;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_play:
                    if(screenState != PLAY) {
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.content, PlayFragment.newInstance()).commit();
                        screenState = PLAY;
                    }
                    return true;
                case R.id.navigation_talk:
                    if(screenState != TALK){
                        if(screenState< TALK){
                            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                        }else{
                            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                        }
                        fragmentTransaction.replace(R.id.content, TalkFragment.newInstance()).commit();
                        screenState = TALK;
                    }
                    return true;
                case R.id.navigation_teach:
                    if(screenState != TEACH) {
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        fragmentTransaction.replace(R.id.content, TeachFragment.newInstance()).commit();
                        screenState = TEACH;
                    }
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
