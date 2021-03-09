package com.meishe.yangquan.bean;

/**
 * 我的-饲料金总额返回结果
 */
public class MineFeedGoldResult {

    // 响应业务状态
    private int code;
    // 响应消息
    private String msg;

    private String enMsg;

    private float data;



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

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
