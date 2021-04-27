package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/26 10:35
 * @Description : 评论管理数据结果
 */
public class BUManagerCommentInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private BUManagerCommentInfo data;

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

    public BUManagerCommentInfo getData() {
        return data;
    }

    public void setData(BUManagerCommentInfo data) {
        this.data = data;
    }
}
