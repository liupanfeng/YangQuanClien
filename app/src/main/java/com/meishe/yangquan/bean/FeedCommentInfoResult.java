package com.meishe.yangquan.bean;


/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/5/6 18:45
 * @Description :饲料-消息列表
 */
public class FeedCommentInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private FeedCommentInfo data;

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

    public FeedCommentInfo getData() {
        return data;
    }

    public void setData(FeedCommentInfo data) {
        this.data = data;
    }
}
