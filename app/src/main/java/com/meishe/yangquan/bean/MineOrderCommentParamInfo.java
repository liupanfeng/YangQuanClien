package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/4/24
 * @Description : 我的订单 评论参数 提交评论用的
 */
public class MineOrderCommentParamInfo {

    private int orderId;

    private boolean anonymity;

    private List<MineOrderCommentInfoChildParam> list;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isAnonymity() {
        return anonymity;
    }

    public void setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;
    }

    public List<MineOrderCommentInfoChildParam> getList() {
        return list;
    }

    public void setList(List<MineOrderCommentInfoChildParam> list) {
        this.list = list;
    }
}
