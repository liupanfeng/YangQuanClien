package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/3/6
 * @Description : 购物车列表接秒
 */
public class FeedShoppingCarInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private List<FeedShoppingCarInfo> data;


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

    public List<FeedShoppingCarInfo> getData() {
        return data;
    }

    public void setData(List<FeedShoppingCarInfo> data) {
        this.data = data;
    }
}
