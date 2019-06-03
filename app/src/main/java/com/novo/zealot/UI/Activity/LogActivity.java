package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;

import com.novo.zealot.Adapter.LogAdapter;
import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.R;
import com.novo.zealot.Utils.GlobalUtil;

import java.util.List;

public class LogActivity extends Activity implements View.OnTouchListener {

    RecyclerView rv_log;
    LogAdapter logAdapter;

    LinearLayout ll_noData;

    LinearLayout ll_logActivity;

    //手指向右滑动时的最小速度
    private static final int XSPEED_MIN = 200;

    //手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

    //记录手指按下时的横坐标。
    private float xDown;

    //记录手指移动时的横坐标。
    private float xMove;

    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        rv_log = findViewById(R.id.rv_log);

        ll_logActivity = findViewById(R.id.ll_logActivity);
        ll_logActivity.setOnTouchListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LogActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_log.setLayoutManager(linearLayoutManager);

        ll_noData = findViewById(R.id.ll_noData);

        DividerItemDecoration decoration = new DividerItemDecoration(LogActivity.this, DividerItemDecoration.VERTICAL);
        rv_log.addItemDecoration(decoration);

        //获取所有数据
        List<RunRecord> records = GlobalUtil.getInstance().databaseHelper.queryRecord();

        if (records.size() != 0) {
            ll_noData.setVisibility(LinearLayout.INVISIBLE);

            logAdapter = new LogAdapter(records);
            rv_log.setAdapter(logAdapter);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                //活动的距离
                int distanceX = (int) (xMove - xDown);
                //获取顺时速度
                int xSpeed = getScrollVelocity();
                //当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
                if (distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        return true;
    }
}
