package com.meishe.yangquan.bean;


import java.util.List;

/**
 * 我的商机数据结果
 */
public class BusinessOpportunityResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private List<BusinessOpportunity> data;

    public List<BusinessOpportunity> getData() {
        return data;
    }

    public void setData(List<BusinessOpportunity> data) {
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
