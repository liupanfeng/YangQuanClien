package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/15 16:26
 * @Description : 订单内容数据   注意这个在用户版 商版都也使用
 */
public class OrderContentsInfo {

 //    [
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


    private int goodsAmount;

    private int goodsId;

    private List<String> goodsImageUrls;

    private String goodsSpecification;

    private String goodsTitle;

    /*这个是评论专用的*/
    private String description;

    private float score;


    public int getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(int goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public List<String> getGoodsImageUrls() {
        return goodsImageUrls;
    }

    public void setGoodsImageUrls(List<String> goodsImageUrls) {
        this.goodsImageUrls = goodsImageUrls;
    }

    public String getGoodsSpecification() {
        return goodsSpecification;
    }

    public void setGoodsSpecification(String goodsSpecification) {
        this.goodsSpecification = goodsSpecification;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
