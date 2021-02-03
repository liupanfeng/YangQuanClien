package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 羊管家--入栏信息
 */
public class HouseKeeperEntryInformationInfo extends BaseInfo{

//    "data": {
//        "id": 2,
//                "batchId": 1,
//                "variety": "东北羊",
//                "price": 11.11,
//                "weight": 20.22,
//                "amount": 222,
//                "inDate": 1605110400000,
//                "initDate": 1609082280000,
//                "state": 1
//    }

    private int id;

    private int batchId;

    private String variety;

    private float price;

    private float weight;

    private int amount ;

    private long inDate;

    private long initDate;

    private int state;


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

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getInDate() {
        return inDate;
    }

    public void setInDate(long inDate) {
        this.inDate = inDate;
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
}
