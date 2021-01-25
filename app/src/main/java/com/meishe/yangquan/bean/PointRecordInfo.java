package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/23
 * @Description : 我的-单个积分记录
 */
public class PointRecordInfo extends BaseInfo{

//    "id": 9,
//            "fromType": "SUGGEST_ACCEPT", //建议被采纳
//            "wealth": 5,
//            "initDate": 1610270316000

    private int id;

    private String fromType;

    private int wealth;

    private long initDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }
}
