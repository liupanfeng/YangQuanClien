package com.meishe.yangquan.bean;

import java.util.List;

/**
 * 疫苗记录返回结果
 */
public class SheepVaccineInfoResult {

    // 响应业务状态
    private int code;
    // 响应消息
    private String msg;

    private String enMsg;

    private List<SheepVaccineInfo> data;

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

    public List<SheepVaccineInfo> getData() {
        return data;
    }

    public void setData(List<SheepVaccineInfo> data) {
        this.data = data;
    }
}
