package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/5 13:26
 * @Description : 商版-退货管理数据
 */
public class BUManagerRefundInfo extends BaseInfo{

    /*订单状态*/
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
