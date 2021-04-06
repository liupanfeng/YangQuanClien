package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 羊管家-获取出栏信息  初步的设计是可以多批次出栏，所以这里是一个列表
 */
public class HouseKeeperOutInformationInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private List<HouseKeeperOutInformationInfo> data;

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

    public List<HouseKeeperOutInformationInfo> getData() {
        return data;
    }

    public void setData(List<HouseKeeperOutInformationInfo> data) {
        this.data = data;
    }
}
