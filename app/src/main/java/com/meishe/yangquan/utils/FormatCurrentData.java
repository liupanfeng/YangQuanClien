package com.meishe.yangquan.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
     * 距离现在几天了
     * @param startTime
     * @return
     */
    public static String getTimeRangeDay(long startTime)
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
            return "0";
        }
        if (elapsedTime < seconds_of_1hour) {
            return "0";
        }
        if (elapsedTime < seconds_of_1day) {
            return "0";
        }

        if (elapsedTime < seconds_of_2days) {
            return "1";
        }

        if(elapsedTime < seconds_of_3days){
            int n = elapsedTime/seconds_of_2days;
            if(n <= 3){
                return String.valueOf(elapsedTime/seconds_of_2days);
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
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        String dd=decimalFormat.format(value);
        return dd;
    }


    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
