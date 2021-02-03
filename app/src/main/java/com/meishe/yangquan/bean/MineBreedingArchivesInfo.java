package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/25
 * @Description :我的-养殖档案列表
 */
public class MineBreedingArchivesInfo extends BaseInfo {

//    "data": [
//    {
//        "id": 1,
//            "title": "第一批",
//            "initUser": null,
//            "initDate": 1607869241000,
//            "state": null,
//            "currentCulturalQuantity": 0
//    }
//	]

    private int id;

    private String title;

    private long initDate;

    private int state;

    private int currentCulturalQuantity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCurrentCulturalQuantity() {
        return currentCulturalQuantity;
    }

    public void setCurrentCulturalQuantity(int currentCulturalQuantity) {
        this.currentCulturalQuantity = currentCulturalQuantity;
    }

}
