package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/6
 * @Description : 效益分析接口
 */
public class SheepBenefitAnalysisInfoResult extends BaseInfo{

    // 响应业务状态
    private int code;
    // 响应消息
    private String msg;

    private String enMsg;

    private SheepBenefitAnalysisInfo data;

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

    public SheepBenefitAnalysisInfo getData() {
        return data;
    }

    public void setData(SheepBenefitAnalysisInfo data) {
        this.data = data;
    }
}
