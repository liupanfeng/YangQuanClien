package com.meishe.yangquan.bean;


import java.util.List;

public class SheepNewsResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private List<SheepNews> data;

    public List<SheepNews> getData() {
        return data;
    }

    public void setData(List<SheepNews> data) {
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