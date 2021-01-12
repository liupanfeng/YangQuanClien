package com.meishe.yangquan.utils;

import android.content.Context;
import android.text.TextUtils;

import com.meishe.yangquan.bean.UserInfo;

public class UserManager {
    private static  UserManager instance ;
    private UserInfo user;
    private String token="";
    private Context mContext;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public static UserManager getInstance(Context context) {
        if (instance==null){
            synchronized (UserManager.class){
                if (instance==null){
                    instance=new UserManager(context);
                }
            }
        }
        return instance;
    }

    private UserManager(Context context) {
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(context);
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public boolean isNeedLogin(){
        if (TextUtils.isEmpty(getToken())){
            return true;
        }
        return false;
    }

    public String getToken() {
//        if (!TextUtils.isEmpty(token)){
//            return token;
//        }
//        token=sharedPreferencesUtil.getString("token");
//        return token;
        return "78816dafbbe544fe80e8d21118bf3faa";
    }

    public void setToken(String token) {
        this.token = token;
        sharedPreferencesUtil.putString("token",token);
    }
}
