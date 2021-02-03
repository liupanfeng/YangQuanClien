package com.meishe.yangquan.bean;

import java.util.List;

/**
 * 我的 - 我的消息
 */
public class MineMyFocusInfoResult {

    // 响应业务状态
    private int code;

    // 响应消息
    private String msg;

    // 响应消息
    private String enMsg;

    private List<MineMyFocusInfo> data;

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

    public List<MineMyFocusInfo> getData() {
        return data;
    }

    public void setData(List<MineMyFocusInfo> data) {
        this.data = data;
    }
}
