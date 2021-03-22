package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/22 10:34
 * @Description : 版本信息
 */
public class VersionInfo extends BaseInfo{

//    "id": 1,
//            "tips": "1.新增功能 2.新增头像",
//            "version": "1.2.3",
//            "initDate": 1607252510000,
//            "fileUrl": "http://localhost:9999/app_version/2020-12/06/1607252459875.jpg"


    private int id;

    private String tips;
    private String version;

    private long initDate;

    private String fileUrl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
