package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/9 13:47
 * @Description :
 */
public class MineFeedGoldInfo extends BaseInfo{
//    "initDate": 1615215801000,
//            "typeName": "订单",
//            "recordType": "ORDER_OUT",
//            "gold": 369.9

    private long initDate;

    private String typeName;

    private String recordType;

    private float gold;


    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public float getGold() {
        return gold;
    }

    public void setGold(float gold) {
        this.gold = gold;
    }
}
