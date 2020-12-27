package com.meishe.yangquan.bean;

/**
 * 不需要额外数据的返回结果
 */
public class ServerResult {

    // 响应业务状态
    private int code;
    // 响应消息
    private String msg;

    private String enMsg;


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
}
