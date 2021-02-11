package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/6
 * @Description :
 */
public class MineUserMessageInfo extends BaseInfo{

//    "title": "回复了您",
//            "content": null,
//            "initDate": 1612071079000,
//            "initUser": 3,
//            "state": null,
//            "msgType": 3,
//            "nickname": "羊圈用户(8765)",
//            "iconUrl": ""

    private String title;

    private String content;

    private long initDate;

    private int initUser;

    private int msgType;

    private String nickname;

    private String iconUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public int getInitUser() {
        return initUser;
    }

    public void setInitUser(int initUser) {
        this.initUser = initUser;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
