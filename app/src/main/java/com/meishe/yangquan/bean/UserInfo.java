package com.meishe.yangquan.bean;

/**
 * 用户信息
 */
public class UserInfo {

//
//    "code": 1,
//            "enMsg": "success",
//            "msg": "成功",
//            "data": {


//        "userId": null,
//         "nickname": "羊圈用户(2160)",
//         "iconUrl": "http://59.110.142.42:9999/user_icon/2021-01/17/1610897395306.jpg",
//         "isInfoComplete": 0,
//         "phone": null,
//         "gender": null,
//         "state": 1,
//         "idCardFrontUrl": null,
//         "idCardReverseUrl": null,
//         "driveCardFrontUrl": null,
//         "driveCardReverseUrl": null,
//         "runCardFrontUrl": null,
//         "runCardReverseUrl": null,
//         "culturalScale": null,
//         "culturalAddress": null,
//         "culturalAge": null,
//         "currentCulturalQuantity": null

//    }


    private String nickname;                        //昵称

    private String iconUrl;                         //头像

    private int isInfoComplete;                     //信息是否完整

    private Integer gender ;                             //性别

    private int state;                              //这个是什么状态

    private String idCardFrontUrl;                  //身份证正面信息

    private String idCardReverseUrl;               //身份证反面信息

    private String driveCardFrontUrl;               //驾驶本正面地址

    private String driveCardReverseUrl;               //驾驶本反面地址

    private String runCardFrontUrl;

    private String runCardReverseUrl;

    private int culturalScale = -1;                    //养殖规模

    private String culturalAddress;                  //养殖场地址

    private int culturalAge = -1;                        //养殖年限

    private int currentCulturalQuantity = -1;           // 存栏量

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIsInfoComplete() {
        return isInfoComplete;
    }

    public void setIsInfoComplete(int isInfoComplete) {
        this.isInfoComplete = isInfoComplete;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl;
    }

    public String getIdCardReverseUrl() {
        return idCardReverseUrl;
    }

    public void setIdCardReverseUrl(String idCardReverseUrl) {
        this.idCardReverseUrl = idCardReverseUrl;
    }

    public String getDriveCardFrontUrl() {
        return driveCardFrontUrl;
    }

    public void setDriveCardFrontUrl(String driveCardFrontUrl) {
        this.driveCardFrontUrl = driveCardFrontUrl;
    }

    public String getDriveCardReverseUrl() {
        return driveCardReverseUrl;
    }

    public void setDriveCardReverseUrl(String driveCardReverseUrl) {
        this.driveCardReverseUrl = driveCardReverseUrl;
    }

    public String getRunCardFrontUrl() {
        return runCardFrontUrl;
    }

    public void setRunCardFrontUrl(String runCardFrontUrl) {
        this.runCardFrontUrl = runCardFrontUrl;
    }

    public String getRunCardReverseUrl() {
        return runCardReverseUrl;
    }

    public void setRunCardReverseUrl(String runCardReverseUrl) {
        this.runCardReverseUrl = runCardReverseUrl;
    }

    public int getCulturalScale() {
        return culturalScale;
    }

    public void setCulturalScale(int culturalScale) {
        this.culturalScale = culturalScale;
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
}