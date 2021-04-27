package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/5 13:26
 * @Description : 商版-评论-子列表数据
 */
public class BUManagerCommentChildInfo extends BaseInfo{


    private int orderId;

    private int orderState;

    private float price;


    private ReceiverInfo receiverInfo;

    private String backGoodsOption;

    private List<OrdersInfo> orders;


    /*评论类型*/
    private int type;

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public String getBackGoodsOption() {
        return backGoodsOption;
    }

    public void setBackGoodsOption(String backGoodsOption) {
        this.backGoodsOption = backGoodsOption;
    }

    public List<OrdersInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersInfo> orders) {
        this.orders = orders;
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
}
