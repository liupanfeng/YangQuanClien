package com.meishe.yangquan.bean;

/**
 * 创建批次得到的结果
 */
public class BatchResult {

//    "code": 1,
//            "enMsg": "success",
//            "msg": "成功",
//            "data": 1   //当前插入的批次id

    private int code;

    private String msg;

    private String enMsg;

    private int data;


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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
