package com.novo.zealot.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.novo.zealot.R;
import com.novo.zealot.UI.Activity.mapActivity;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunFragment extends Fragment {

    public static final String TAG = "RunFragment";

    TickerView tv_todayDistance;
    ImageView img_run;
    Context context;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public RunFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RunFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RunFragment newInstance(String param1, String param2) {
        RunFragment fragment = new RunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_run, container, false);

        //设置TickerView
        tv_todayDistance = view.findViewById(R.id.tv_todayDistance);
        tv_todayDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_todayDistance.setAnimationDuration(3000);

        //添加OnClickListener
        img_run = view.findViewById(R.id.img_run);
        img_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, mapActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onStart() {
        super.onStart();
        tv_todayDistance.setText("56789");
        Log.d(TAG, "onStart()");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }


}
