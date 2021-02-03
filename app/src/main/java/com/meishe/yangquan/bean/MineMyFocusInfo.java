package com.meishe.yangquan.bean;

import android.provider.ContactsContract;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/31
 * @Description : 我的-我的关注
 */
public class MineMyFocusInfo extends BaseInfo{

//    {
//        "userId": 3,
//            "nickname": "羊圈用户(8765)",
//            "iconUrl": ""
//    },

    private int userId;

    private String nickname;

    private String iconUrl;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
