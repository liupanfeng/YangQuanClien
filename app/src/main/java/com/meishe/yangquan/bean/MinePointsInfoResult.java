package com.meishe.yangquan.bean;

/**
 * 我的积分接口实体
 */
public class MinePointsInfoResult {

    // 响应业务状态
    private int code;

    // 响应消息
    private String msg;

    // 响应消息
    private String enMsg;

    private MinePointsInfo data;

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

    public MinePointsInfo getData() {
        return data;
    }

    public void setData(MinePointsInfo data) {
        this.data = data;
    }
}
