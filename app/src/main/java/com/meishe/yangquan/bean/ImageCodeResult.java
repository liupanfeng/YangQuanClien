package com.meishe.yangquan.bean;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/11/28 17:57
 */
public class ImageCodeResult {

    private int code;

    private String enMsg;

    private String msg;

    private ImageCodeInfo data;

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

    public ImageCodeInfo getData() {
        return data;
    }

    public void setData(ImageCodeInfo data) {
        this.data = data;
    }


}
