package com.meishe.yangquan.bean;

/**
 * 长传文件结果
 */
public class UploadFileResult {

    // 响应业务状态
    private int code;
    // 响应消息
    private String msg;

    private String enMsg;

    private UploadFileInfo data;

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

    public UploadFileInfo getData() {
        return data;
    }

    public void setData(UploadFileInfo data) {
        this.data = data;
    }
}
