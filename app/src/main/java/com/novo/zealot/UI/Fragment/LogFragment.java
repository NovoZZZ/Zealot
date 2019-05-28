package com.novo.zealot.UI.Fragment;

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
import com.novo.zealot.Utils.GlobalUtil;

import java.util.List;

public class LogFragment extends Fragment {
    public static final String TAG = "LogFragment";

    RecyclerView rv_log;
    LogAdapter logAdapter;

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        List<RunRecord> records = GlobalUtil.getInstance().databaseHelper.queryRecord();
        logAdapter = new LogAdapter(records);
        rv_log.setAdapter(logAdapter);
    }
}
