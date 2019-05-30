package com.novo.zealot.UI.Activity;

/**
 * Created by Novo on 2019/5/27.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.novo.zealot.R;
import com.novo.zealot.UI.Fragment.LogFragment;
import com.novo.zealot.UI.Fragment.ProfileFragment;
import com.novo.zealot.UI.Fragment.RunFragment;
import com.novo.zealot.Utils.GlobalUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final  String TAG = "MainActivity";

    private FragmentManager fm;
    private LogFragment frgLog;
    private RunFragment frgRun;
    private ProfileFragment frgProfile;
    String isFirstUse = null;


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
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_run);


        //设置Context
        GlobalUtil.getInstance().setContext(getApplicationContext());
        GlobalUtil.getInstance().mainActivity = this;

        InputStream inputStream = null;

        try {
            inputStream = openFileInput("config");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            isFirstUse = bufferedReader.readLine();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //说明是第一次使用
        if (isFirstUse == null || !isFirstUse.equals("false")){
            Intent intent = new Intent(MainActivity.this, firstUseActivity.class);
            startActivity(intent);
        }
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

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
}
