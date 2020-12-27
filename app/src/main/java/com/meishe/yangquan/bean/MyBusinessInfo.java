package com.meishe.yangquan.bean;

/**
 * 我的服务
 */
public class MyBusinessInfo {

//    "id": 1,
//            "name": "张三",
//            "phone": "13800000000",
//            "idCardNum": "41142199909090001",
//            "culturalAddress": "北京市海淀区",
//            "culturalAge": 12,
//            "currentCulturalQuantity": 1000,
//            "initDate": 1607844849000,
//            "authState": 0      // 0 待审核 1 初次审核通过  -1 初审拒绝 2 复审通过 -2 复审 拒绝

    private int id;
    /*姓名*/
    private String name;
    /*身份证号*/
    private String idCardNum;
    /*地址*/
    private String culturalAddress;
    /*养殖年限*/
    private int culturalAge;
    /*养殖数量*/
    private int currentCulturalQuantity;
    private String initDate;
    /*当前状态*/
    private int authState;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getCulturalAddress() {
        return culturalAddress;
    }

    public void setCulturalAddress(String culturalAddress) {
        this.culturalAddress = culturalAddress;
    }

    public int getCulturalAge() {
        return culturalAge;
    }

    public void setCulturalAge(int culturalAge) {
        this.culturalAge = culturalAge;
    }

    public int getCurrentCulturalQuantity() {
        return currentCulturalQuantity;
    }

    public void setCurrentCulturalQuantity(int currentCulturalQuantity) {
        this.currentCulturalQuantity = currentCulturalQuantity;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }
}
