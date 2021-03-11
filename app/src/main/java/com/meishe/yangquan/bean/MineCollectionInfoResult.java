package com.meishe.yangquan.bean;

import java.util.List;

/**
 * 我的反馈接口实体
 */
public class MineCollectionInfoResult {

    // 响应业务状态
    private int code;

    // 响应消息
    private String msg;

    // 响应消息
    private String enMsg;

    private List<MineCollectionInfo> data;

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

    public List<MineCollectionInfo> getData() {
        return data;
    }

    public void setData(List<MineCollectionInfo> data) {
        this.data = data;
    }
}
