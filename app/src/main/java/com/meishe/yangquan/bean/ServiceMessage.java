package com.meishe.yangquan.bean;

import java.util.Date;

/**
 * 服务循环消息
 */
@Deprecated
public class ServiceMessage extends BaseInfo{
    private Integer id;

    private String content;

    private long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}