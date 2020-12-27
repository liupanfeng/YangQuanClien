package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @author liupanfeng
 * @desc 服务返回结果
 * @date 2020/11/28 17:57
 */
public class ServiceResult {

    private int code;

    private String enMsg;

    private String msg;

    private List<ServiceInfo> data;

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

    public List<ServiceInfo> getData() {
        return data;
    }

    public void setData(List<ServiceInfo> data) {
        this.data = data;
    }


}
