package com.meishe.yangquan.bean;

import java.util.Date;

/**
 * 系统通知，展现在我的消息里边 包含一些系统推送的消息
 */
@Deprecated
public class SystemNotification extends BaseInfo{
    private Integer id;

    private String content;

    private Integer userType;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}