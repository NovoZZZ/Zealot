package com.novo.zealot.Utils;

/**
 * Created by Novo on 2019/5/29.
 */

public class DataUtil {
    /**
     * 将持续时间转换为时分秒
     * @param duration total seconds
     * @return HH:mm:ss
     */
    public static String getFormattedTime(int duration){
        int hours = 0, minutes = 0, seconds = 0;
        StringBuilder sb = new StringBuilder();

        //小时 = duration/3600秒
        hours = duration / 3600;
        duration = duration % 3600;
        if (hours < 10){
            sb.append("0");
        }
        sb.append(hours + ":");

        //分钟 = duration/60秒
        minutes = duration / 60;
        duration = duration % 60;
        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes + ":");

        //分钟 = duration
        seconds = duration;
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds + "");

        return sb.toString();
    }

    /**
     * 判断是否为纯数字
     * @param str 待判断字符串
     * @return 结果
     */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
