package com.meishe.yangquan.bean;



/**
 * push绑定设备id
 */
@Deprecated
public class UpdateDeviceAliasResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
