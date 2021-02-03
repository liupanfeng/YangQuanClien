package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 养殖助手 -养殖过程 --配料分析 返回结果
 */
public class HouseKeeperSourceAnalysisResult {

    private int code;

    private String msg;

    private String enMsg;

    private List<HouseKeeperSourceAnalysisInfo> data;

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

    public List<HouseKeeperSourceAnalysisInfo> getData() {
        return data;
    }

    public void setData(List<HouseKeeperSourceAnalysisInfo> data) {
        this.data = data;
    }
}
