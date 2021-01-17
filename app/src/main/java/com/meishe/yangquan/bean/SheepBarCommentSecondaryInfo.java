package com.meishe.yangquan.bean;

/**
 * @author liupanfeng
 * @desc  羊吧 二级评论列表
 * @date 2020/12/20 17:10
 */
public class SheepBarCommentSecondaryInfo extends BaseInfo {


//        "id": 5,
//        "content": "回复评论了3",
//        "initDate": 1608472729000,
//        "initUser": 2,
//        "nickname": "张三",
//        "iconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg",
//        "childAmount": 0,
//        "hasPraised": false,
//        "replyUserId": 2,
//        "replyNickname": "张三",
//        "replyIconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg"



    private int id;

    private String content;

    private long initDate;

    private int initUser;     //这个是用户id

    private String nickname;

    private String iconUrl;

    private int childAmount;

    private boolean hasPraised;

    private int replyUserId;            //一级评论的用户id

    private String replyIconUrl;        //一级评论的链接

    private String replyNickname;   //被回复的昵称 一级评论的昵称

    private int praiseAmount;

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

    public int getChildAmount() {
        return childAmount;
    }

    public void setChildAmount(int childAmount) {
        this.childAmount = childAmount;
    }

    public boolean isHasPraised() {
        return hasPraised;
    }

    public void setHasPraised(boolean hasPraised) {
        this.hasPraised = hasPraised;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyIconUrl() {
        return replyIconUrl;
    }

    public void setReplyIconUrl(String replyIconUrl) {
        this.replyIconUrl = replyIconUrl;
    }

    public String getReplyNickname() {
        return replyNickname;
    }

    public void setReplyNickname(String replyNickname) {
        this.replyNickname = replyNickname;
    }

    public int getPraiseAmount() {
        return praiseAmount;
    }

    public void setPraiseAmount(int praiseAmount) {
        this.praiseAmount = praiseAmount;
    }
}
