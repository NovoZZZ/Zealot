package com.novo.zealot.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novo.zealot.R;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.RecordHolder> {

    //单条记录ViewHolder
    class RecordHolder extends RecyclerView.ViewHolder {
        //获取log_item中元素
        TextView tv_recordDistance
                , tv_recordUnit
                , tv_recordStartTime
                , tv_recordAvgSpeed
                , tv_recordDuration;


        public RecordHolder(@NonNull View itemView) {
            super(itemView);

            tv_recordDistance = itemView.findViewById(R.id.tv_recordDistance);
            tv_recordUnit = itemView.findViewById(R.id.tv_recordUnit);
            tv_recordStartTime = itemView.findViewById(R.id.tv_recordStartTime);
            tv_recordAvgSpeed = itemView.findViewById(R.id.tv_recordAvgSpeed);
            tv_recordDuration = itemView.findViewById(R.id.tv_recordDuration);
        }
    }


    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder recordHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
