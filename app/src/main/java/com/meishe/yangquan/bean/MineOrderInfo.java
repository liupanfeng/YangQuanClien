package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/9 17:53
 * @Description : 我的-订单数据结构 用户版
 */
public class MineOrderInfo extends BaseInfo{


//    "orderId": 20,
//            "orderState": 1,
//            "price": 246.6,
//            "receiverInfo": {
//        "name": "张三",
//                "phone": "13223232323",
//                "area": "北京海淀区",
//                "address": "五棵松"
//    },
//    "backGoodsOption": null,


    private int orderId;

    private int orderState;

    private float price;

    private ReceiverInfo receiverInfo;

    private Object backGoodsOption;

    private List<OrdersInfo> orders;

    /*这个字段不是服务器返回的*/
    private int type;


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

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public Object getBackGoodsOption() {
        return backGoodsOption;
    }

    public void setBackGoodsOption(Object backGoodsOption) {
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
}
