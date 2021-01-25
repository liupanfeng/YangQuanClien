package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/19
 * @Description : 资讯详情页数据实体
 */
public class IndustryNewsInfo extends BaseInfo{

    /*新闻来源*/
    private String newsSource;
    /*作者*/
    private String author;

    private long publishDate;

    /*标题*/
    private String title;

    private List<IndustryNewsClip> clips;


    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IndustryNewsClip> getClips() {
        return clips;
    }

    public void setClips(List<IndustryNewsClip> clips) {
        this.clips = clips;
    }

}
