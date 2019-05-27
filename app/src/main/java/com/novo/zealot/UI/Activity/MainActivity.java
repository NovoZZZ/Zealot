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
                    Log.d(TAG, "trying to call frgLog");
                    if(frgLog == null){
                        Log.d(TAG, "trying to new frgLog");
                        frgLog = new LogFragment();
                    }
                    switchFragment(frgLog);
                    break;
                case R.id.navigation_run:
                    Log.d(TAG, "trying to call frgRun");
                    if (frgRun == null){
                        Log.d(TAG, "trying to new frgRun");
                        frgRun = new RunFragment();
                    }
                    switchFragment(frgRun);
                    break;
                case R.id.navigation_profile:
                    Log.d(TAG, "trying to call frgProfile");
                    if (frgProfile == null){
                        Log.d(TAG, "trying to new frgProfile");
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
            Log.d(TAG, "this fragment is added");
            fm.beginTransaction().show(fragment).commit();
        } else {
            Log.d(TAG,"new Fragment");
            fm.beginTransaction().add(R.id.fl_main, fragment).commit();
        }
    }

}