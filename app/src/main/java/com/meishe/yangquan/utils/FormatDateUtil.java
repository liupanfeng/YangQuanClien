package com.meishe.yangquan.utils;

import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateUtil {

    public static final String FORMAT_TYPE="yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TYPE_YEAR_MONTH_DAY="yyyy-MM-dd";
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }


    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }


    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }


    /**
     * 计算某个时间距离当前时间的天数,小时数以及分钟数.
     * @param calDate
     * @return
     */
    public static String calculateTime(Date calDate){
        String tips = "";
        Date now = new Date();
        long l = calDate.getTime() - now.getTime();
        long days =  (l / (1000 * 60 * 60 * 24));
        if (days > 0){
            tips += days + "天";
        }
        long hours =  (l / (1000 * 60 * 60) - days*24);
        if (hours > 0 || (days > 0 && hours == 0)){
            tips += hours + "小时";
        }
        long mins =  (l / (1000 * 60)) - days*24*60 - hours*60;
        tips += mins + "分钟";
        return tips;
    }


}
