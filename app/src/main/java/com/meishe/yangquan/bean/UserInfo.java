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
//        "nickname": "张三",
//                "iconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg",
//                "isInfoComplete": 1,
//                "idCardFrontUrl": "http://localhost:9999/user_id_card_front/2020-12/06/1607242952268.jpg",
//                "idCardReverseUrl": "http://localhost:9999/user_id_card_reverse/2020-12/06/1607242962993.jpg",
//                "driveCardFrontUrl": "http://localhost:9999/user_drive_card_front/2020-12/06/1607242983664.jpg",
//                "driveCardReverseUrl": "http://localhost:9999/user_drive_card_reverse/2020-12/06/1607242994433.jpg",
//                "runCardFrontUrl": "http://localhost:9999/user_run_card_front/2020-12/06/1607243004052.jpg",
//                "runCardReverseUrl": "http://localhost:9999/user_run_card_reverse/2020-12/06/1607243018576.jpg",
//                "culturalScale": 1000,
//                "culturalAddress": "北京市海淀区",
//                "culturalAge": 20,
//                "currentCulturalQuantity": 567
//    }


    private String nickname;                        //昵称

    private String iconUrl;                         //头像

    private int isInfoComplete;                     //信息是否完整

    private String idCardFrontUrl;                  //身份证正面信息

    private Integer idCardReverseUrl;               //身份证反面信息

    private String driveCardFrontUrl;               //驾驶本正面地址

    private String driveCardReverseUrl;               //驾驶本反面地址

    private String runCardFrontUrl;

    private String runCardReverseUrl;

    private int culturalScale;

    private String culturalAddress;                //养殖场地址

    private int culturalAge;

    private int currentCulturalQuantity;

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

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl;
    }

    public Integer getIdCardReverseUrl() {
        return idCardReverseUrl;
    }

    public void setIdCardReverseUrl(Integer idCardReverseUrl) {
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