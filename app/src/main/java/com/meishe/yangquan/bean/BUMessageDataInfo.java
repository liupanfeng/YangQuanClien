package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/21
 * @Description : 商版 消息
 */
public class BUMessageDataInfo extends BaseInfo {

//     "id": 1,
//             "shopId": 11,
//             "msgType": "ORDER",
//             "msgContent": "用户杀阡陌已下单，请及时发货",
//             "initUser": 9,
//             "initDate": 1620292826000,
//             "goodsId": null

    private int id;

    private int shopId;

    private String msgType;

    private String msgContent;

    private int initUser;

    private long initDate;

    private int goodsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getInitUser() {
        return initUser;
    }

    public void setInitUser(int initUser) {
        this.initUser = initUser;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
