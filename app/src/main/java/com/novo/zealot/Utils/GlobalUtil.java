package com.novo.zealot.Utils;

import android.content.Context;

import com.novo.zealot.DB.ZealotDBOpenHelper;
import com.novo.zealot.UI.Activity.MainActivity;

public class GlobalUtil {

    private static final String TAG = "GlobalUtil";
    private static GlobalUtil instance;

    //db
    public ZealotDBOpenHelper databaseHelper;

    private Context context;
    public MainActivity mainActivity;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        databaseHelper = new ZealotDBOpenHelper(context, ZealotDBOpenHelper.TABLE_NAME, null, 1);
    }

    public static GlobalUtil getInstance(){
        if (instance==null){
            instance = new GlobalUtil();
        }
        return instance;
    }
}
