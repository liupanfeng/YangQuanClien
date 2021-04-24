package com.meishe.yangquan.bean;

import java.util.List;

/**
 *我的-订单-退货进度
 */
public class MineRefundProgressInfoResult {

    // 响应业务状态
    private int code;

    // 响应消息
    private String msg;

    // 响应消息
    private String enMsg;

    private List<MineRefundProgressInfo> data;

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

    public List<MineRefundProgressInfo> getData() {
        return data;
    }

    public void setData(List<MineRefundProgressInfo> data) {
        this.data = data;
    }
}
