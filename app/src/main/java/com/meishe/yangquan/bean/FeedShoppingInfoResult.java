package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/3/6
 * @Description : 饲料-请求店铺列表返回的数据
 */
public class FeedShoppingInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private List<FeedShoppingInfo> data;

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

    public List<FeedShoppingInfo> getData() {
        return data;
    }

    public void setData(List<FeedShoppingInfo> data) {
        this.data = data;
    }
}
