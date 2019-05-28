package com.novo.zealot.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.R;
import com.novo.zealot.Utils.DateUtil;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.RecordHolder> {

    //记录
    List<RunRecord> records;


    //单条记录ViewHolder
    class RecordHolder extends RecyclerView.ViewHolder {
        //获取log_item中元素
        TextView tv_recordDistance, tv_recordUnit, tv_recordStartTime, tv_recordAvgSpeed, tv_recordDuration;


        public RecordHolder(@NonNull View itemView) {
            super(itemView);

            tv_recordDistance = itemView.findViewById(R.id.tv_recordDistance);
            tv_recordUnit = itemView.findViewById(R.id.tv_recordUnit);
            tv_recordStartTime = itemView.findViewById(R.id.tv_recordStartTime);
            tv_recordAvgSpeed = itemView.findViewById(R.id.tv_recordAvgSpeed);
            tv_recordDuration = itemView.findViewById(R.id.tv_recordDuration);
        }
    }

    public LogAdapter(List<RunRecord> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.log_item, viewGroup, false);
        RecordHolder recordHolder = new RecordHolder(view);
        return recordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder recordHolder, int i) {
        RunRecord record = records.get(i);
        int distance = (int)record.getDistance();

        //判断是否大于1000米，是则显示公里
        double distanceKM = 0;
        boolean unitIsKM = false;
        if (distance > 1000) {
            distanceKM = distance / 1000.0;
            unitIsKM = true;
        }

        if (unitIsKM){
            recordHolder.tv_recordDistance.setText(String.valueOf(String.valueOf(distanceKM)));
            recordHolder.tv_recordUnit.setText("公里");
        }else{
            recordHolder.tv_recordDistance.setText(String.valueOf(distance));
            recordHolder.tv_recordUnit.setText("米");
        }

        recordHolder.tv_recordStartTime.setText(DateUtil.getFormattedTime(record.getStartTime()));
        recordHolder.tv_recordAvgSpeed.setText(String.valueOf((int)record.getAvgSpeed()));
        recordHolder.tv_recordDuration.setText(String.valueOf(record.getDuration()));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
