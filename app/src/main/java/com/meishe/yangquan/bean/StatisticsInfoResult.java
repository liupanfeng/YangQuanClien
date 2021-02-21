package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/23
 * @Description :  历史行情记录 当日行情，最新的行情数据
 */
public class StatisticsInfoResult {


    // 响应业务状态
    private int code;

    // 响应消息
    private String msg;

    // 响应消息
    private String enMsg;

    private StatisticsInfo data;


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

    public StatisticsInfo getData() {
        return data;
    }

    public void setData(StatisticsInfo data) {
        this.data = data;
    }
}
