package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/22
 * @Description : 剪羊毛数据 view 层的显示
 */
public class SheepHairInfo extends BaseInfo{


    //
//    {
//        "id": 8,
//            "batchId": 1,
//            "recordType": 1,
//            "amount": 11,
//            "price": 15,
//            "initDate": 1611991285000,
//            "state": 1,
//            "recordDate": "01.30",
//            "recordContent": "打疫苗2"
//    },

    private int id;

    private int batchId;

    private int recordType;

    private int amount;

    private float price;

    private long initDate;

    private int  state;

    private String recordDate;

    private String recordContent;

    private int type;


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

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
