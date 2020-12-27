package com.meishe.yangquan.bean;

/**
 * 羊管家-获取批次列表数据
 */
public class BatchInfo {

//    "id": 1,
//            "title": "第一批",
//            "initDate": 1607869241000,
//            "currentCulturalQuantity": 0

    private int id;

    private String title;

    private long initDate;

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

    public int getCurrentCulturalQuantity() {
        return currentCulturalQuantity;
    }

    public void setCurrentCulturalQuantity(int currentCulturalQuantity) {
        this.currentCulturalQuantity = currentCulturalQuantity;
    }


}
