package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/6
 * @Description : 饲料记录 数据
 */
public class FoodRecordInfo {

//
//    "id": 2,
//            "batchId": 1,
//            "sheepWeight": 22.22,
//            "feedDays": 11,
//            "initDate": 1611991429000,
//            "fodders": [
//    {
//        "fodderId": 2,
//            "weight": 11.1,
//            "percent": 21.1,
//            "price": 31.1,
//            "totalPrice": 110.2
//    },
//    {
//        "fodderId": 1,
//            "weight": 12.1,
//            "percent": 22.1,
//            "price": 32.1,
//            "totalPrice": 120.2
//    }
//		]

    private int id;

    private int batchId;

    private float sheepWeight;

    private int feedDays;

    private long initDate;

    private List<FodderInfo> fodders;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public float getSheepWeight() {
        return sheepWeight;
    }

    public void setSheepWeight(float sheepWeight) {
        this.sheepWeight = sheepWeight;
    }

    public int getFeedDays() {
        return feedDays;
    }

    public void setFeedDays(int feedDays) {
        this.feedDays = feedDays;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public List<FodderInfo> getFodders() {
        return fodders;
    }

    public void setFodders(List<FodderInfo> fodders) {
        this.fodders = fodders;
    }
}
