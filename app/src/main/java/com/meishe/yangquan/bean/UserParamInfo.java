package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description : 更新用户信息 参数信息
 */
public class UserParamInfo {


    private String nickname;

    private Integer gender;

    // 头像的文件id
    private Integer iconFileId;
    //  身份证正面的文件id
    private Integer idCardFrontFileId;
    //身份证反面的文件id
    private int idCardReverseFileId;

    //驾驶证正面的文件id
    private Integer driveCardFrontFileId;
    // 驾驶证反面的文件id
    private Integer driveCardReverseFileId;
    //行驶证正面的文件id
    private Integer runCardFrontFileId;

    // 行驶证反面的文件id
    private Integer runCardReverseFileId;

    //养殖规模
    private Integer culturalScale;
    //养殖地址
    private String culturalAddress;

    //养殖年限
    private Integer culturalAge;

    //存栏数量
    private Integer currentCulturalQuantity;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(Integer iconFileId) {
        this.iconFileId = iconFileId;
    }

    public Integer getIdCardFrontFileId() {
        return idCardFrontFileId;
    }

    public void setIdCardFrontFileId(Integer idCardFrontFileId) {
        this.idCardFrontFileId = idCardFrontFileId;
    }

    public int getIdCardReverseFileId() {
        return idCardReverseFileId;
    }

    public void setIdCardReverseFileId(int idCardReverseFileId) {
        this.idCardReverseFileId = idCardReverseFileId;
    }

    public Integer getDriveCardFrontFileId() {
        return driveCardFrontFileId;
    }

    public void setDriveCardFrontFileId(Integer driveCardFrontFileId) {
        this.driveCardFrontFileId = driveCardFrontFileId;
    }

    public Integer getDriveCardReverseFileId() {
        return driveCardReverseFileId;
    }

    public void setDriveCardReverseFileId(Integer driveCardReverseFileId) {
        this.driveCardReverseFileId = driveCardReverseFileId;
    }

    public Integer getRunCardFrontFileId() {
        return runCardFrontFileId;
    }

    public void setRunCardFrontFileId(Integer runCardFrontFileId) {
        this.runCardFrontFileId = runCardFrontFileId;
    }

    public Integer getRunCardReverseFileId() {
        return runCardReverseFileId;
    }

    public void setRunCardReverseFileId(Integer runCardReverseFileId) {
        this.runCardReverseFileId = runCardReverseFileId;
    }

    public Integer getCulturalScale() {
        return culturalScale;
    }

    public void setCulturalScale(Integer culturalScale) {
        this.culturalScale = culturalScale;
    }

    public String getCulturalAddress() {
        return culturalAddress;
    }

    public void setCulturalAddress(String culturalAddress) {
        this.culturalAddress = culturalAddress;
    }

    public Integer getCulturalAge() {
        return culturalAge;
    }

    public void setCulturalAge(Integer culturalAge) {
        this.culturalAge = culturalAge;
    }

    public Integer getCurrentCulturalQuantity() {
        return currentCulturalQuantity;
    }

    public void setCurrentCulturalQuantity(Integer currentCulturalQuantity) {
        this.currentCulturalQuantity = currentCulturalQuantity;
    }

    public void clear() {
        this.iconFileId = null;
        this.culturalAddress = null;
    }
}
