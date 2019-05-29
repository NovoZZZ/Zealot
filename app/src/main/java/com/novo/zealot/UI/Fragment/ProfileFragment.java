package com.novo.zealot.UI.Fragment;

/**
 * Created by Novo on 2019/5/29.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novo.zealot.R;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    TickerView tv_sumOfDistance, tv_sumOfTimes;
    int sumOfDistance = 0, sumOfTimes = 0;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "ProfileFragment Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //初始化
        tv_sumOfDistance = view.findViewById(R.id.tv_sumOfDistance);
        tv_sumOfDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_sumOfDistance.setAnimationDuration(1000);


        tv_sumOfTimes = view.findViewById(R.id.tv_sumOfTimes);
        tv_sumOfTimes.setCharacterLists(TickerUtils.provideNumberList());
        tv_sumOfTimes.setAnimationDuration(1000);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        //查询总距离并传给控件
        sumOfDistance = GlobalUtil.getInstance().databaseHelper.queryAllDistance();
        //格式 XX.X
        sumOfDistance/=100;
        double sumOfDistanceInKM = sumOfDistance / 10.0;
        tv_sumOfDistance.setText(sumOfDistanceInKM + "");

        //查询总次数
        sumOfTimes = GlobalUtil.getInstance().databaseHelper.queryNumOfTimes();
        tv_sumOfTimes.setText(sumOfTimes + "");
    }
}
