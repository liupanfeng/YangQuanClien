package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/10
 * @Description : 营养成分 数据结构
 */
public class SheepNutritionInfoResult extends BaseInfo {

    // 响应业务状态
    private int code;
    // 响应消息
    private String msg;

    private String enMsg;

    private List<SheepNutritionInfo> data;

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

    public List<SheepNutritionInfo> getData() {
        return data;
    }

    public void setData(List<SheepNutritionInfo> data) {
        this.data = data;
    }
}
