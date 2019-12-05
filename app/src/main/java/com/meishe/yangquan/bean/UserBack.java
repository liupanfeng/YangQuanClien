package com.meishe.yangquan.bean;

import java.util.Date;

public class UserBack {
    private Integer id;

    private String username;

    private String parssworld;

    private Integer level;

    private Date createTime;

    private String t1;

    private String t2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getParssworld() {
        return parssworld;
    }

    public void setParssworld(String parssworld) {
        this.parssworld = parssworld == null ? null : parssworld.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1 == null ? null : t1.trim();
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2 == null ? null : t2.trim();
    }
}