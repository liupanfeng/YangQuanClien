package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/5 13:26
 * @Description : 商版-评论数据
 */
public class BUManagerCommentInfo extends BaseInfo{

    /*评论类型*/
    private int type;

    public int getState() {
        return type;
    }

    public void setState(int type) {
        this.type = type;
    }

}
