package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 羊管家-获取入栏信息
 */
public class HouseKeeperEntryInformationInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private HouseKeeperEntryInformationInfo data;

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

    public HouseKeeperEntryInformationInfo getData() {
        return data;
    }

    public void setData(HouseKeeperEntryInformationInfo data) {
        this.data = data;
    }
}
