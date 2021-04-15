package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/5 13:26
 * @Description : 商版-订单数据
 */
public class BUManagerOrderInfo extends BaseInfo{

    private int count;


    private List<BUOrderInfo> elements;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BUOrderInfo> getElements() {
        return elements;
    }

    public void setElements(List<BUOrderInfo> elements) {
        this.elements = elements;
    }
}
