package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/15 20:44
 * @Description : 退货数据列表
 */
public class BUGoodsRefundInfo extends BaseInfo{


    private int count;


    private List<BUGoodsRefundListInfo> elements;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BUGoodsRefundListInfo> getElements() {
        return elements;
    }

    public void setElements(List<BUGoodsRefundListInfo> elements) {
        this.elements = elements;
    }
}
