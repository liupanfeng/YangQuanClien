package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/13 20:27
 * @Description : 首页店铺数据
 */
public class BUHomeShoppingDataInfo extends BaseInfo{

//    "shopName": "我的店铺4",
//            "imageUrl": "http://localhost:9999/user_icon/2021-03/01/1614612608874.jpg",
//            "goodEvaluationCount": 1,      好评
//            "badEvaluationCount": 0,       差评
//            "normalEvaluationCount": 1,   中评
//            "goodsCollectionCount": 0,    商品收藏
//            "shopCollectionCount": 0,     店铺收藏
//            "goodsCount": 1,               出售中
//            "committedOrderCount": 7,      提交订单数量
//            "payedOrderCount": 2,          待付
//            "receivedOrderCount": 0,       收到货物还没评价的数量
//            "applyBackGoodsOrderCount": 1,  收到退货的数量
//            "todayOrderCount": 0,           今日订单数量
//            "totalOrderCount": 13,          总订单数量
//            "todayPrice": 0,                今日成交额
//            "totalPrice": 0                 累计成交额
    /*店铺名称*/
    private String shopName;
    /*头像*/
    private String imageUrl;
    /*好评数据*/
    private int goodEvaluationCount;
    /*差评数量*/
    private int badEvaluationCount;
    /*中评数量*/
    private int normalEvaluationCount;
    /*收藏商品*/
    private int goodsCollectionCount;
    /*关注店铺*/
    private int shopCollectionCount;
    /**/
    private int  goodsCount;

    /**/
    private int committedOrderCount;
    /*待付数量*/
    private int payedOrderCount;
    /*收到订单的数量 -出售中*/
    private int  receivedOrderCount;
    /*退货数量*/
    private int  applyBackGoodsOrderCount;
    /*今日订单数量*/
    private int  todayOrderCount;
    /*累计订单数量*/
    private int  totalOrderCount;
    /*今日成交额*/
    private int todayPrice;
    /*累计成交额数量*/
    private int totalPrice;
    /*总访客*/
    private int visitorAmount;


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getGoodEvaluationCount() {
        return goodEvaluationCount;
    }

    public void setGoodEvaluationCount(int goodEvaluationCount) {
        this.goodEvaluationCount = goodEvaluationCount;
    }

    public int getBadEvaluationCount() {
        return badEvaluationCount;
    }

    public void setBadEvaluationCount(int badEvaluationCount) {
        this.badEvaluationCount = badEvaluationCount;
    }

    public int getNormalEvaluationCount() {
        return normalEvaluationCount;
    }

    public void setNormalEvaluationCount(int normalEvaluationCount) {
        this.normalEvaluationCount = normalEvaluationCount;
    }

    public int getGoodsCollectionCount() {
        return goodsCollectionCount;
    }

    public void setGoodsCollectionCount(int goodsCollectionCount) {
        this.goodsCollectionCount = goodsCollectionCount;
    }

    public int getShopCollectionCount() {
        return shopCollectionCount;
    }

    public void setShopCollectionCount(int shopCollectionCount) {
        this.shopCollectionCount = shopCollectionCount;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getCommittedOrderCount() {
        return committedOrderCount;
    }

    public void setCommittedOrderCount(int committedOrderCount) {
        this.committedOrderCount = committedOrderCount;
    }

    public int getPayedOrderCount() {
        return payedOrderCount;
    }

    public void setPayedOrderCount(int payedOrderCount) {
        this.payedOrderCount = payedOrderCount;
    }

    public int getReceivedOrderCount() {
        return receivedOrderCount;
    }

    public void setReceivedOrderCount(int receivedOrderCount) {
        this.receivedOrderCount = receivedOrderCount;
    }

    public int getApplyBackGoodsOrderCount() {
        return applyBackGoodsOrderCount;
    }

    public void setApplyBackGoodsOrderCount(int applyBackGoodsOrderCount) {
        this.applyBackGoodsOrderCount = applyBackGoodsOrderCount;
    }

    public int getTodayOrderCount() {
        return todayOrderCount;
    }

    public void setTodayOrderCount(int todayOrderCount) {
        this.todayOrderCount = todayOrderCount;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public int getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(int todayPrice) {
        this.todayPrice = todayPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getVisitorAmount() {
        return visitorAmount;
    }

    public void setVisitorAmount(int visitorAmount) {
        this.visitorAmount = visitorAmount;
    }
}
