package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/22
 * @Description : 疫苗数据 view 层的显示
 */
public class SheepVaccineInfo extends BaseInfo{
    private int id;

    private int batchId;

    private int recordType;

    private int amount;

    private float price;

    private long initDate;

    private int  state;

    private String recordDate;

    private String recordContent;

    /*用来区分显示类型：1养殖助手 2：养殖档案  养殖档案不需要显示 保存按钮 */
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
