package com.novo.zealot.UI.Fragment;

/**
 * Created by Novo on 2019/5/28.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novo.zealot.Adapter.LogAdapter;
import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.R;
import com.novo.zealot.Utils.DataUtil;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.List;

public class LogFragment extends Fragment {
    public static final String TAG = "LogFragment";

    RecyclerView rv_log;
    LogAdapter logAdapter;

    TickerView tv_bestDistance,tv_bestSpeed, tv_bestTime;

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

        rv_log = view.findViewById(R.id.rv_log);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_log.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rv_log.addItemDecoration(decoration);

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
        //获取所有数据
        List<RunRecord> records = GlobalUtil.getInstance().databaseHelper.queryRecord();
        logAdapter = new LogAdapter(records);
        rv_log.setAdapter(logAdapter);

        //获取最远距离，最快速度，最长时间
        double bestDistance = ((int)GlobalUtil.getInstance().databaseHelper.queryBestDistance())/1000;
        double bestSpeed = ((int)(GlobalUtil.getInstance().databaseHelper.queryBestSpeed()*100))/100;
        String bestTime = DataUtil.getFormattedTime(GlobalUtil.getInstance().databaseHelper.queryBestTime());

        tv_bestDistance.setText("" + bestDistance);
        tv_bestSpeed.setText("" + bestSpeed);
        tv_bestTime.setText(bestTime);
    }
}
