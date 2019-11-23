package com.meishe.yangquan.bean;

import java.util.Date;

/**
 * 咨询新闻
 */
public class SheepNews extends BaseInfo{
    private Integer id;

    private String sheepType;

    private String sheepName;

    private String sheepPrice;

    private Date publishTime;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSheepType() {
        return sheepType;
    }

    public void setSheepType(String sheepType) {
        this.sheepType = sheepType == null ? null : sheepType.trim();
    }

    public String getSheepName() {
        return sheepName;
    }

    public void setSheepName(String sheepName) {
        this.sheepName = sheepName == null ? null : sheepName.trim();
    }

    public String getSheepPrice() {
        return sheepPrice;
    }

    public void setSheepPrice(String sheepPrice) {
        this.sheepPrice = sheepPrice == null ? null : sheepPrice.trim();
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}