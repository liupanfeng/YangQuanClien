package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/15 15:08
 * @Description : 这个是订单管理里边嵌套的数据结构  BUManagerOrderInfo
 */
public class BUOrderInfo extends BaseInfo{

    private int  orderId;

    private int  orderState;

    private float price;

    private BUReceiveInfo receiverInfo;

    private List<BUOrderDetailInfo> orders;



    /*订单状态 这个目前是上层数据在用*/
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public BUReceiveInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(BUReceiveInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public List<BUOrderDetailInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<BUOrderDetailInfo> orders) {
        this.orders = orders;
    }
}
