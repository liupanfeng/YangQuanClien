package com.meishe.yangquan.bean;

import java.util.List;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/15 20:46
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class BUGoodsRefundInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private BUGoodsRefundInfo data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public BUGoodsRefundInfo getData() {
        return data;
    }

    public void setData(BUGoodsRefundInfo data) {
        this.data = data;
    }
}
