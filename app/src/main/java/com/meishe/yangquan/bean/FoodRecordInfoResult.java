package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/6
 * @Description : 饲料记录 数据
 */
public class FoodRecordInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private FoodRecordInfo data;


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

    public FoodRecordInfo getData() {
        return data;
    }

    public void setData(FoodRecordInfo data) {
        this.data = data;
    }
}
