package com.meishe.yangquan.bean;

@Deprecated
public class ADOpenScreenResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    //暂时先用单个对象 稍后改成list
    private AdOpenScreen data;

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

    public AdOpenScreen getData() {
        return data;
    }

    public void setData(AdOpenScreen data) {
        this.data = data;
    }
}
