package com.meishe.yangquan.utils;

import android.content.Context;
import android.text.TextUtils;

import com.meishe.yangquan.bean.User;

public class UserManager {
    private static  UserManager instance ;
    private User user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
        return "01e2ab9def4d42e2aab36834ed3b66b7";
    }

    public void setToken(String token) {
        this.token = token;
        sharedPreferencesUtil.putString("token",token);
    }
}
