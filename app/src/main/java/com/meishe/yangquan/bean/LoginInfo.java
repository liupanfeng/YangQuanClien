package com.meishe.yangquan.bean;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/11/28 19:48
 */
public class LoginInfo extends BaseInfo{

    private UserInfo userInfo;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
