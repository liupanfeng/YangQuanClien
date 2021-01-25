package com.meishe.yangquan.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 得到格式化的时间 几天前 几小时前等
 */
public class FormatCurrentData {
    private static final String TAG = FormatCurrentData.class.getSimpleName();

    /**设置每个阶段时间*/
    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_2days = seconds_of_1day * 2;
    private static final int seconds_of_3days = seconds_of_1day * 3;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;

    public static String getTimeRange(String startTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /**获取当前时间*/
        Date curDate = new  Date(System.currentTimeMillis());
        String dataStrNew= sdf.format(curDate);
        Date startData = new Date(Long.parseLong(startTime));
        try {
            /**将时间转化成Date*/
            curDate=sdf.parse(dataStrNew);
        } catch (ParseException e) {
            Log.e(TAG,"fail to getTimeRange" ,e);
        }
        /**除以1000是为了转换成秒*/
        long   between=(curDate.getTime()- startData.getTime())/1000;

        int   elapsedTime= (int) (between);
        if (elapsedTime < seconds_of_1minute) {
            return "刚刚";
        }
        if (elapsedTime < seconds_of_1hour) {
            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1day) {
            return elapsedTime / seconds_of_1hour + "小时前";
        }

        if (elapsedTime < seconds_of_2days) {
            return "昨天";
        }

        if(elapsedTime < seconds_of_3days){
            int n = elapsedTime/seconds_of_2days;
            if(n <= 3){
                return String.valueOf(elapsedTime/seconds_of_2days)+ "天前";
            }
        }

        if (elapsedTime < seconds_of_1year) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
            return  sdf1.format(startData);

        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf2.format(startData);
    }


    public static String getTimeRange(long startTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /**获取当前时间*/
        Date curDate = new  Date(System.currentTimeMillis());
        String dataStrNew= sdf.format(curDate);
        Date startData = new Date(startTime);
        try {
            /**将时间转化成Date*/
            curDate=sdf.parse(dataStrNew);
        } catch (ParseException e) {
            Log.e(TAG,"fail to getTimeRange" ,e);
        }
        /**除以1000是为了转换成秒*/
        long   between=(curDate.getTime()- startData.getTime())/1000;

        int   elapsedTime= (int) (between);
        if (elapsedTime < seconds_of_1minute) {
            return "刚刚";
        }
        if (elapsedTime < seconds_of_1hour) {
            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1day) {
            return elapsedTime / seconds_of_1hour + "小时前";
        }

        if (elapsedTime < seconds_of_2days) {
            return "昨天";
        }

        if(elapsedTime < seconds_of_3days){
            int n = elapsedTime/seconds_of_2days;
            if(n <= 3){
                return String.valueOf(elapsedTime/seconds_of_2days)+ "天前";
            }
        }

        if (elapsedTime < seconds_of_1year) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
            return  sdf1.format(startData);

        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf2.format(startData);
    }

    /**
     *
      * @param value
     * @return
     */
    public static String getFormatStringFromFloat(float value){
        DecimalFormat decimalFormat = new DecimalFormat("##0.0");
        String dd=decimalFormat.format(value);
        return dd;
    }
}
