package com.meishe.yangquan.bean;

import com.meishe.yangquan.utils.CommonUtils;

import java.util.List;

/**
 * @author liupanfeng
 * @desc  羊吧 评论列表
 * @date 2020/12/20 17:10
 */
public class SheepBarCommentInfo extends BaseInfo {


//    "id": 2,
//            "content": "评论来饿了2222",
//            "initDate": 1608470409000,
//            "initUser": 2,
//            "nickname": "张三",
//            "iconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg",
//            "childAmount": 0,
//            "hasPraised": true

    private int id;

    private String content;

    private long initDate;

    private int initUser;

    private String nickname;

    private String iconUrl;

    private int childAmount;

    private boolean hasPraised;

    /*是否只看楼主  这个是上层需要的服务端并没有返回这个字段，需要上层维护*/
    private boolean onlySeeOwner;

    /**
     * 帖子的id 服务端没有返回上层维护
     */
    private int sheepBarMessageId;

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

    public boolean isOnlySeeOwner() {
        return onlySeeOwner;
    }

    public void setOnlySeeOwner(boolean onlySeeOwner) {
        this.onlySeeOwner = onlySeeOwner;
    }

    public int getSheepBarMessageId() {
        return sheepBarMessageId;
    }

    public void setSheepBarMessageId(int sheepBarMessageId) {
        this.sheepBarMessageId = sheepBarMessageId;
    }

    public int getPraiseAmount() {
        return praiseAmount;
    }

    public void setPraiseAmount(int praiseAmount) {
        this.praiseAmount = praiseAmount;
    }

    public static void setSeeOwner(List<SheepBarCommentInfo> datas, boolean finalOnlySeeOwner) {
        if (!CommonUtils.isEmpty(datas)){
            for (SheepBarCommentInfo sheepBarCommentInfo:datas){
                if (sheepBarCommentInfo==null){
                    continue;
                }
                sheepBarCommentInfo.setOnlySeeOwner(finalOnlySeeOwner);
            }
        }
    }

    public static void setSheepBarMeesageId(List<SheepBarCommentInfo> datas, int id) {
        if (!CommonUtils.isEmpty(datas)){
            for (SheepBarCommentInfo sheepBarCommentInfo:datas){
                if (sheepBarCommentInfo==null){
                    continue;
                }
                sheepBarCommentInfo.setSheepBarMessageId(id);
            }
        }
    }

}
