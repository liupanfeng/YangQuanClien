package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/5 13:26
 * @Description : 商版-评论数据
 */
public class BUManagerCommentInfo extends BaseInfo{

    private int count;

    private List<BUManagerCommentChildInfo> elements;



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BUManagerCommentChildInfo> getElements() {
        return elements;
    }

    public void setElements(List<BUManagerCommentChildInfo> elements) {
        this.elements = elements;
    }


}
