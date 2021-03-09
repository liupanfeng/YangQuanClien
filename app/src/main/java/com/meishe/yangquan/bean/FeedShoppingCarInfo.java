package com.meishe.yangquan.bean;

import com.meishe.yangquan.activity.BaseActivity;
import com.meishe.yangquan.activity.FeedShoppingDetailActivity;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/3/6
 * @Description :
 */
public class FeedShoppingCarInfo extends BaseInfo {

//  "shopId": 7,
//            "shopName": "跃跃",
//            "goodsList": [
//        {
//            "id": null,
//                "shopId": null,
//                "shopName": null,
//                "title": "优质预混料",
//                "goodsImageFileIds": "319,320,324",
//                "goodsImageUrls": [
//            "http://59.110.142.42:9999/shop_goods/2021-03/04/1614870790386.jpg",
//                    "http://59.110.142.42:9999/shop_goods/2021-03/04/1614870789423.jpg",
//                    "http://59.110.142.42:9999/shop_goods/2021-03/04/1614870789461.jpg"
//                    ],
//            "firstCategory": null,
//                "secondCategory": null,
//                "place": null,
//                "weight": 56,
//                "specification": "袋",
//                "address": null,
//                "isPublic": null,
//                "brand": null,
//                "storeAmount": null,
//                "sellAmount": null,
//                "price": 156,
//                "description": null,
//                "descriptionImageFileIds": null,
//                "descriptionImageUrls": null,
//                "modDate": null
//        }
//            ]
//    }

    private int shopId;

    private String shopName;

    private List<FeedGoodsInfo> goodsList;

}
