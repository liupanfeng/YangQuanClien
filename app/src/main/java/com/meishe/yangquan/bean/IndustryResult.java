package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/11/28 17:57
 */
public class IndustryResult {

    private int code;

    private String enMsg;

    private String msg;

    private List<IndustryInfo> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<IndustryInfo> getData() {
        return data;
    }

    public void setData(List<IndustryInfo> data) {
        this.data = data;
    }


}
