package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @author liupanfeng
 * @desc 系统消息 返回结果
 * @date 2020/11/28 17:57
 */
public class SystemMessageInfoResult {

    private int code;

    private String enMsg;

    private String msg;

    private List<SystemMessageInfo> data;

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

    public List<SystemMessageInfo> getData() {
        return data;
    }

    public void setData(List<SystemMessageInfo> data) {
        this.data = data;
    }


}
