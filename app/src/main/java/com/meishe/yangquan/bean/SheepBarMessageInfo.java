package com.meishe.yangquan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author liupanfeng
 * @desc  羊吧列表
 * @date 2020/12/20 17:10
 */
public class SheepBarMessageInfo extends BaseInfo {

    //    "id": 3,
//            "content": "哈哈哈，太累了123",
//            "initDate": "1608463228000",
//            "initUser": 2,
//            "nickname": "张三",
//            "iconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg",
//            "shareAmount": 1,
//            "praiseAmount": 0,
//            "commentAmount": 0,
//            "hasPraised": false,
//            "images": [
//            "http://localhost:9999/sheep_bar_post/2020-12/20/1608463215907.jpg",
//            "http://localhost:9999/sheep_bar_post/2020-12/20/1608463191546.png"
//            ]

    private int id;

    private String content;

    private long initDate;

    private int initUser;

    private String nickname;

    private String iconUrl;

    private int shareAmount;

    private int praiseAmount;

    private int  commentAmount;

    private boolean hasPraised;

    private List<String> images;


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

    public int getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(int shareAmount) {
        this.shareAmount = shareAmount;
    }

    public int getPraiseAmount() {
        return praiseAmount;
    }

    public void setPraiseAmount(int praiseAmount) {
        this.praiseAmount = praiseAmount;
    }

    public int getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }

    public boolean isHasPraised() {
        return hasPraised;
    }

    public void setHasPraised(boolean hasPraised) {
        this.hasPraised = hasPraised;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
