package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/27
 * @Description : 商版-店铺信息
 */
public class BUShoppingInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private BUShoppingInfo data;


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

    public BUShoppingInfo getData() {
        return data;
    }

    public void setData(BUShoppingInfo data) {
        this.data = data;
    }
}
