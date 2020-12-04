package com.meishe.yangquan.bean;

/**
 * @author liupanfeng
 * @desc 行业资讯 数据实体
 * @date 2020/12/3 10:48
 */
public class IndustryInfo extends BaseInfo{

    private int id;
    private String title;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
