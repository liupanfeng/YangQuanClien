package com.meishe.yangquan.bean;


import java.util.List;

public class MsgResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private Message data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Message data) {
        this.data = data;
    }

    public Message getData() {
        return data;
    }
}