package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/13 20:38
 * @Description :
 */
public class BUHomeShoppingDataInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private BUHomeShoppingDataInfo data;


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

    public BUHomeShoppingDataInfo getData() {
        return data;
    }

    public void setData(BUHomeShoppingDataInfo data) {
        this.data = data;
    }
}
