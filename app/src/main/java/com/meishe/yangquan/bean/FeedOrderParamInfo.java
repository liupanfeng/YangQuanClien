package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/16 16:41
 * @Description : 订单的参数info
 */
public class FeedOrderParamInfo extends BaseInfo {

//    "comeFrom": "car",
//            "receiverInfo": {
//        "name": "张三",
//                "phone": "13223232323",
//                "area": "北京海淀区",
//                "address": "五棵松"
//    },
//            "goods": [
//    {
//        "goodsId": 2,
//            "amount": 1
//    },
//    {
//        "goodsId": 3,
//            "amount": 1
//    }
//	]


    private String comeFrom;

    private FeedAddressParamInfo receiverInfo;

    private List<FeedGoodsParamInfo> goods;

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public FeedAddressParamInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(FeedAddressParamInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public List<FeedGoodsParamInfo> getGoods() {
        return goods;
    }

    public void setGoods(List<FeedGoodsParamInfo> goods) {
        this.goods = goods;
    }


}
