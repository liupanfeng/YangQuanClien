package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/3 16:54
 * @Description : 商品数据请求结果
 */
public class BUGoodsInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private List<BUGoodsInfo> data;

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

    public List<BUGoodsInfo> getData() {
        return data;
    }

    public void setData(List<BUGoodsInfo> data) {
        this.data = data;
    }
}
