package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/4/24
 * @Description :我的订单-评论-列表  这个是为了View展示
 */
public class MineOrderCommentViewInfo extends BaseInfo {

    private int goodId;

    private List<String> coverUrl;

    private String title;

    private float score;

    private String description;

    private List<String> fileIds;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }


    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }


    public List<String> getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(List<String> coverUrl) {
        this.coverUrl = coverUrl;
    }

    public static MineOrderCommentViewInfo parseInfo(OrderContentsInfo orderContentsInfo) {
        MineOrderCommentViewInfo mineOrderCommentViewInfo = new MineOrderCommentViewInfo();
        mineOrderCommentViewInfo.setGoodId(orderContentsInfo.getGoodsId());
        mineOrderCommentViewInfo.setCoverUrl(orderContentsInfo.getGoodsImageUrls());
        mineOrderCommentViewInfo.setTitle(orderContentsInfo.getGoodsTitle());
        return mineOrderCommentViewInfo;
    }
}
