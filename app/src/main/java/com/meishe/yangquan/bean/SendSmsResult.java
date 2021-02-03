package com.meishe.yangquan.bean;

@Deprecated
public class SendSmsResult {

    // 响应业务状态
    private int code;

    // 响应消息
    private String msg;

    //暂时先用单个对象 稍后改成list
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
