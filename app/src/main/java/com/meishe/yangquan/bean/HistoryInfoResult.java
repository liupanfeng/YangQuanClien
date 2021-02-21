package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/23
 * @Description : 历史行情结果
 */
public class HistoryInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private HistoryInfo data;


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

    public HistoryInfo getData() {
        return data;
    }

    public void setData(HistoryInfo data) {
        this.data = data;
    }
}
