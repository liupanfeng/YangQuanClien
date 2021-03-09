package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/3/6
 * @Description :
 */
public class FeedGoodsInfoResult {


    private int code;

    private String msg;

    private String enMsg;

    private FeedGoodsInfo data;

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

    public FeedGoodsInfo getData() {
        return data;
    }

    public void setData(FeedGoodsInfo data) {
        this.data = data;
    }
}
