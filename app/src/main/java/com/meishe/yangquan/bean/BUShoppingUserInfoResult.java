package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/27
 * @Description : 商版-入住用户
 */
public class BUShoppingUserInfoResult extends BaseInfo {


    private int code;

    private String msg;

    private String enMsg;

    private List<BUShoppingUserInfo> data;


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

    public List<BUShoppingUserInfo> getData() {
        return data;
    }

    public void setData(List<BUShoppingUserInfo> data) {
        this.data = data;
    }
}
