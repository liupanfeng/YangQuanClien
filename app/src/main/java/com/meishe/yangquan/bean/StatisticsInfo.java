package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/23
 * @Description : 历史行情里边的嵌套的数据结构
 */
public class StatisticsInfo {

//    "statistics": {
//        "yesterday": 0,
//                "average": 0,
//                "min": 2147483647,
//                "max": 0,
//                "today": 0
//    }

    private float yesterday;

    private float average;

    private float min;

    private float max;

    private float today;

    private String updateDate;

    public float getYesterday() {
        return yesterday;
    }

    public void setYesterday(float yesterday) {
        this.yesterday = yesterday;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getToday() {
        return today;
    }

    public void setToday(float today) {
        this.today = today;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
