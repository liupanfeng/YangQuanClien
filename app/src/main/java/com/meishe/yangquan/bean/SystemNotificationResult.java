package com.meishe.yangquan.bean;


import java.util.List;

@Deprecated
public class SystemNotificationResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private List<SystemNotification> data;

    public List<SystemNotification> getData() {
        return data;
    }

    public void setData(List<SystemNotification> data) {
        this.data = data;
    }

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


}
