package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/30 10:56
 * @Description : 出栏信息数据
 */
public class HouseKeeperOutInformationInfo extends BaseInfo{

//    	"id": 6,
//                "batchId": 1,
//                "outType": "羊腔",
//                "price": 1.11,
//                "weight": 20.2,
//                "cavityWeight": 2.2,
//                "amount": 1,
//                "outDate": 1607702400000,
//                "initDate": 1611758003000,
//                "state": 1


    private int id;

    private int batchId;

    private String outType;

    private float price;

    private float weight;
    /**
     * 出腔率
     */
    private float cavityWeight;

    private int amount;

    private long outDate;

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

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
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

    public float getCavityWeight() {
        return cavityWeight;
    }

    public void setCavityWeight(float cavityWeight) {
        this.cavityWeight = cavityWeight;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getOutDate() {
        return outDate;
    }

    public void setOutDate(long outDate) {
        this.outDate = outDate;
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
