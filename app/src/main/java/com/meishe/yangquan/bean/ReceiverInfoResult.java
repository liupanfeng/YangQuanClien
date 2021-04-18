package com.meishe.yangquan.bean;


/**
 * @author liupanfeng
 * @desc 行情返回结果
 * @date 2020/11/28 17:57
 */
public class ReceiverInfoResult {

    private int code;

    private String enMsg;

    private String msg;

    private ReceiverInfo data;

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

    public ReceiverInfo getData() {
        return data;
    }

    public void setData(ReceiverInfo data) {
        this.data = data;
    }
}
