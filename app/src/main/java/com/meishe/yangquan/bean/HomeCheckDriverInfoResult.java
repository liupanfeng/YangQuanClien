package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/3/21
 * @Description :
 */
public class HomeCheckDriverInfoResult {

    private int code;

    private String msg;

    private String enMsg;

    private HomeCheckDriverInfo data;


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

    public HomeCheckDriverInfo getData() {
        return data;
    }

    public void setData(HomeCheckDriverInfo data) {
        this.data = data;
    }

    public class HomeCheckDriverInfo extends BaseInfo{

        private boolean isDriverInfoComplete;


        public boolean isDriverInfoComplete() {
            return isDriverInfoComplete;
        }

        public void setDriverInfoComplete(boolean driverInfoComplete) {
            isDriverInfoComplete = driverInfoComplete;
        }
    }
}
