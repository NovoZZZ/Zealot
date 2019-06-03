package com.novo.zealot.UI.Fragment;

/**
 * Created by Novo on 2019/5/28.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.novo.zealot.R;
import com.novo.zealot.UI.Activity.ChartActivity;
import com.novo.zealot.UI.Activity.LogActivity;
import com.novo.zealot.Utils.DataUtil;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class LogFragment extends Fragment {
    public static final String TAG = "LogFragment";


    TextView tv_bestDistanceUnit;
    TickerView tv_bestDistance, tv_bestSpeed, tv_bestTime;

    RelativeLayout rl_log, rl_chart;

    public LogFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log, container, false);

        //查看历史记录
        rl_log = view.findViewById(R.id.rl_log);
        rl_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LogActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //查看图表
        rl_chart = view.findViewById(R.id.rl_chart);
        rl_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChartActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //最远距离单位
        tv_bestDistanceUnit = view.findViewById(R.id.tv_bestDistanceUnit);

        //设置最远距离TickerView
        tv_bestDistance = view.findViewById(R.id.tv_bestDistance);
        tv_bestDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_bestDistance.setAnimationDuration(1000);

        //设置最快速度TickerView
        tv_bestSpeed = view.findViewById(R.id.tv_bestSpeed);
        tv_bestSpeed.setCharacterLists(TickerUtils.provideNumberList());
        tv_bestSpeed.setAnimationDuration(1000);

        //设置最长时间TickerView
        tv_bestTime = view.findViewById(R.id.tv_bestTime);
        tv_bestTime.setCharacterLists(TickerUtils.provideNumberList());
        tv_bestTime.setAnimationDuration(1000);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取最远距离，最快速度，最长时间
        //保留至个位
        int bestDistance = (int) GlobalUtil.getInstance().databaseHelper.queryBestDistance();
        if (bestDistance < 1000) {
            tv_bestDistanceUnit.setText("米");
            tv_bestDistance.setText(bestDistance + "");
        } else {
            //保留两位
            int bestDistanceInKM = (bestDistance / 100) / 10;
            tv_bestDistance.setText(bestDistanceInKM + "");
        }
        double bestSpeed = ((int) (GlobalUtil.getInstance().databaseHelper.queryBestSpeed() * 100)) / 100.0;
        String bestTime = DataUtil.getFormattedTime(GlobalUtil.getInstance().databaseHelper.queryBestTime());

        tv_bestDistance.setText("" + bestDistance);
        tv_bestSpeed.setText("" + bestSpeed);
        tv_bestTime.setText(bestTime);

    }
}
