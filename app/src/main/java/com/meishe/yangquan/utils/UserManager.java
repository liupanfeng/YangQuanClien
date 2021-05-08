package com.meishe.yangquan.utils;

import android.content.Context;
import android.text.TextUtils;

import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.ReceiverInfo;
import com.meishe.yangquan.bean.UserInfo;

public class UserManager {
    private static UserManager instance;
    private UserInfo user;
    private ReceiverInfo mReceiverInfo;
    /*店铺数据，为了处理关注的更新做的这个处理*/
    private FeedShoppingInfo feedShoppingInfo;
    /*本来这些属性没必要保存这里，为了刷新收藏的问题，暂时这样处理*/
    private FeedGoodsInfo feedGoodsInfo;

    /*这个是为了处理订单评论*/
    private MineOrderInfo mineOrderInfo;

    /*这个是为了处理是否开店用的*/
    private BUShoppingInfo buShoppingInfo;

    private String token = "";
    private Context mContext;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public static UserManager getInstance(Context context) {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager(context);
                }
            }
        }
        return instance;
    }


    private UserManager(Context context) {
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(context);
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public boolean isNeedLogin() {
        if (TextUtils.isEmpty(getToken())) {
            return true;
        }
        return false;
    }

    public String getToken() {
        if (!TextUtils.isEmpty(token)) {
            return token;
        }
        token = sharedPreferencesUtil.getString("token");
        return token;
//        return "78816dafbbe544fe80e8d21118bf3faa";
//        return "";
    }

    public void setToken(String token) {
        this.token = token;
        sharedPreferencesUtil.putString("token", token);
    }


    public ReceiverInfo getReceiverInfo() {
        return mReceiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.mReceiverInfo = receiverInfo;
    }

    public FeedShoppingInfo getFeedShoppingInfo() {
        return feedShoppingInfo;
    }

    public void setFeedShoppingInfo(FeedShoppingInfo feedShoppingInfo) {
        this.feedShoppingInfo = feedShoppingInfo;
    }

    public FeedGoodsInfo getFeedGoodsInfo() {
        return feedGoodsInfo;
    }

    public void setFeedGoodsInfo(FeedGoodsInfo feedGoodsInfo) {
        this.feedGoodsInfo = feedGoodsInfo;
    }


    public MineOrderInfo getMineOrderInfo() {
        return mineOrderInfo;
    }

    public void setMineOrderInfo(MineOrderInfo mineOrderInfo) {
        this.mineOrderInfo = mineOrderInfo;
    }


    public BUShoppingInfo getBuShoppingInfo() {
        return buShoppingInfo;
    }

    public void setBuShoppingInfo(BUShoppingInfo buShoppingInfo) {
        this.buShoppingInfo = buShoppingInfo;
    }
}
