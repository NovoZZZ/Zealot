package com.novo.zealot.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 获得指定格式的日期
     * 2019-05-27
     *
     * @return get a formatted date yyyy-MM-dd
     */
    public static String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 将字符串转换为Date
     * yyyy-MM-dd
     * @param date yyyy-MM-dd
     * @return get a Date
     */
    public static Date strToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 将Date格式化
     * 2019-05-27 14:25:44
     * @param date a Date
     * @return get a formatted String of date
     */
    public static String getFormattedTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 将String转换成Date
     * yyyy-MM-dd HH:mm:ss
     * @param time a String of time
     * @return get a Date of this String
     */
    public static Date strToTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
