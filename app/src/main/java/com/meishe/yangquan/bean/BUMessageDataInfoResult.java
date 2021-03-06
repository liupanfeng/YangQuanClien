package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/21
 * @Description : 商版 消息 返回结果
 */
public class BUMessageDataInfoResult  {

    private int code;

    private String msg;

    private String enMsg;

    private List<BUMessageDataInfo> data;


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

    public List<BUMessageDataInfo> getData() {
        return data;
    }

    public void setData(List<BUMessageDataInfo> data) {
        this.data = data;
    }


}
