package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/23
 * @Description : 我的-我的积分
 */
public class MinePointsInfo extends BaseInfo{

    /*总积分*/
    private String wealth;
    /*积分列表*/
    private List<PointRecordInfo> records;

    public String getWealth() {
        return wealth;
    }

    public void setWealth(String wealth) {
        this.wealth = wealth;
    }

    public List<PointRecordInfo> getRecords() {
        return records;
    }

    public void setRecords(List<PointRecordInfo> records) {
        this.records = records;
    }
}
