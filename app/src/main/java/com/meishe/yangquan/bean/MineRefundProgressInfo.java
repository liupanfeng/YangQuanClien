package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/4/24
 * @Description : 退货进度
 */
public class MineRefundProgressInfo extends BaseInfo{

//    "id": 1,
//            "type": 1,
//            "orderId": 10,
//            "operation": 11,
//            "description": "用户申请取消订单-商家待确认",
//            "option": "点错了",
//            "initDate": 1615734778000,
//            "initUser": 2


    private int id;

    private int type;

    private int orderId;

    private int operation;

    private String description;

    private String option;

    private long initDate;

    private int  initUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public int getInitUser() {
        return initUser;
    }

    public void setInitUser(int initUser) {
        this.initUser = initUser;
    }
}
