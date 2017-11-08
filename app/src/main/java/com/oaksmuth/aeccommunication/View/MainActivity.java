package com.oaksmuth.aeccommunication.View;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.oaksmuth.aeccommunication.Controller.ViewPagerAdapter;
import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

public class MainActivity extends AppCompatActivity implements PlayFragment.OnTopicSelectedListener{
    private  static final int HOME = 27;
    private  static final int DASHBOARD = 566;
    private  static final int NOTIFICATION = 901;

    private String backState;
    private short screenState;

    BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;

    PlayFragment playFragment;
    TalkFragment talkFragment;
    TeachFragment teachFragment;
    MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
//                    fragmentTransaction.replace(R.id.content, PlayFragment.newInstance()).commit();
//                    screenState=HOME;
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
//                    if(screenState<DASHBOARD){
//                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
//                    }else{
//                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
//                    }
//                    fragmentTransaction.replace(R.id.content, TalkFragment.newInstance()).commit();
//                    screenState=DASHBOARD;
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
//                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
//                    fragmentTransaction.replace(R.id.content, TeachFragment.newInstance()).commit();
//                    screenState=NOTIFICATION;
                    viewPager.setCurrentItem(2);
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ini viewPager
        viewPager = (ViewPager)findViewById(R.id.content);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //swipe
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        setupViewPager(viewPager);

        //To place content with Play
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayFragment playFragment = new PlayFragment();
        backState = playFragment.getClass().getName();
        fragmentTransaction.replace(R.id.content, playFragment).commit();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        playFragment=new PlayFragment();
        talkFragment=new TalkFragment();
        teachFragment=new TeachFragment();
        adapter.addFragment(playFragment);
        adapter.addFragment(talkFragment);
        adapter.addFragment(teachFragment);
        viewPager.setAdapter(adapter);
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
