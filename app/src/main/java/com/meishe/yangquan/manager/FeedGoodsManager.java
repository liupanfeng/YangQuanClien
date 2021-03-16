package com.meishe.yangquan.manager;

import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedShoppingCarGoodsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/16 11:10
 * @Description :
 */
public class FeedGoodsManager {

    private List<BaseInfo> list=new ArrayList<>();

    private static final FeedGoodsManager ourInstance = new FeedGoodsManager();

    public static FeedGoodsManager getInstance() {
        return ourInstance;
    }

    private FeedGoodsManager() {
    }

    public List<BaseInfo> getList() {
        return list;
    }

    public void setList(List<BaseInfo> list) {
        this.list = list;
    }
}
