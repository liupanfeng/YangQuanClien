package com.meishe.yangquan.bean;


public class MyBusinessInfoResult {

    // 响应业务状态
    private int code;

    // 响应消息
    private String msg;

    // 响应消息
    private String enMsg;

    private MyBusinessInfo data;

    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(MyBusinessInfo data) {
        this.data = data;
    }

    public MyBusinessInfo getData() {
        return data;
    }
}
