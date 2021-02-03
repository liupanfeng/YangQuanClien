package com.meishe.yangquan.bean;


import java.util.List;

@Deprecated
public class MessageResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private List<Message> data;

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

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
