package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/5
 * @Description : 反馈建议
 */
public class MineCallbackInfo extends BaseInfo {

//    "id": 3,
//            "content": "app很好用发333",
//            "initUser": 2,
//            "initDate": 1610270304000,
//            "state": null,
//            "authState": 1,
//            "authDate": 1610270316000,
//            "authUser": null

    private int id;

    private String content;

    private int initUser;

    private long initDate;

    private int authState;

    private long authDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getInitUser() {
        return initUser;
    }

    public void setInitUser(int initUser) {
        this.initUser = initUser;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public long getAuthDate() {
        return authDate;
    }

    public void setAuthDate(long authDate) {
        this.authDate = authDate;
    }
}
