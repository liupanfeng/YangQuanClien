package com.meishe.yangquan.bean;

/**
 * @author liupanfeng
 * @desc 轮播图
 * @date 2020/11/29 17:52
 */
public class BannerInfo {

//     "picUrl": "http://localhost:9999/banner/2020-11/29/1606640721039.png",
//             "type_id": 5,
//             "title": "羊苗"

    private String title;

    private String picUrl;

    private int newsId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }


}
