package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/15 16:23
 * @Description : 这个是订单的详情信息--属于BUOrderInfo 的一个内部属性
 */
public class OrdersInfo extends BaseInfo{

//      "orderContents": [
//    {
//        "goodsAmount": 1,
//            "goodsId": 10,
//            "goodsImageUrls": [
//        "http://59.110.142.42:9999/shop_goods/2021-03/18/1616073788883.jpg"
//                                ],
//        "goodsSpecification": "袋",
//            "goodsTitle": "粘玉米"
//    }
//                        ],
//                                "shopId": 11,
//                                "shopName": "葛堡村玉米店"

    private List<OrderContentsInfo> orderContents;

    private int shopId;

    private String shopName;


    public List<OrderContentsInfo> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<OrderContentsInfo> orderContents) {
        this.orderContents = orderContents;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
