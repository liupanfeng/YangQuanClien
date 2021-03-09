package com.meishe.yangquan.bean;

import java.util.List;

/**
 * 我的-饲料金列表返回结果
 */
public class MineFeedGoldInfoResult {

    // 响应业务状态
    private int code;
    // 响应消息
    private String msg;

    private String enMsg;

    private List<MineFeedGoldInfo> data;



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

    public List<MineFeedGoldInfo> getData() {
        return data;
    }

    public void setData(List<MineFeedGoldInfo> data) {
        this.data = data;
    }
}
