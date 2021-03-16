package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/16 16:50
 * @Description : 订单商品paramInfo
 */
public class FeedGoodsParamInfo {

  //   "goodsId": 3,
//            "amount": 1

    private int goodsId;

    private int amount;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
