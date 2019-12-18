package com.meishe.yangquan.bean;

public class ServerZanResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private ServerZan data;

    public ServerZan getData() {
        return data;
    }

    public void setData(ServerZan data) {
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
