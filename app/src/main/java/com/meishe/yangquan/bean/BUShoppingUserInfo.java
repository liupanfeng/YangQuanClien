package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/27
 * @Description : 商版-入住用户
 */
public class BUShoppingUserInfo extends BaseInfo {

//    "userId": 2,
//            "nickname": "张三",
//            "iconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg"

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
