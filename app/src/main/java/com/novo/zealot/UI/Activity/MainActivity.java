package com.novo.zealot.UI.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.novo.zealot.R;
import com.novo.zealot.UI.Fragment.LogFragment;
import com.novo.zealot.UI.Fragment.ProfileFragment;
import com.novo.zealot.UI.Fragment.RunFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final  String TAG = "MainActivity";

    private FragmentManager fm;
    private LogFragment frgLog;
    private RunFragment frgRun;
    private ProfileFragment frgProfile;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_log:
                    if(frgLog == null){
                        frgLog = new LogFragment();
                    }
                    switchFragment(frgLog);
                    break;
                case R.id.navigation_run:
                    if (frgRun == null){
                        frgRun = new RunFragment();
                    }
                    switchFragment(frgRun);
                    break;
                case R.id.navigation_profile:
                    if (frgProfile == null){
                        frgProfile = new ProfileFragment();
                    }
                    switchFragment(frgProfile);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fm = getSupportFragmentManager();



        //BottomNavigationBar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_run);



    }


    /**
     * 更换fragment
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        List<Fragment> fragments = fm.getFragments();
        for (Fragment f : fragments) {
            if (!f.equals(fragment) && !f.isHidden()) {
                fm.beginTransaction().hide(f).commit();
            }
        }
        if (fragment.isAdded()) {
            fm.beginTransaction().show(fragment).commit();
        } else {
            fm.beginTransaction().add(R.id.fl_main, fragment).commit();
        }
    }

}
