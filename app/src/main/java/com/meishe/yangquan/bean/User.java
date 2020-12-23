package com.meishe.yangquan.bean;


import java.util.Date;

import static com.meishe.yangquan.utils.Constants.tokenId;

/**
 * @author 86188
 */
public class User extends BaseInfo{


    private String photoUrl;

    private Integer userType;

    private String userCardIdUrl;

    private Integer sex;

    private Integer star;

    private Date birthday;

    private String autograph;

    private Date startWork;

    private Integer number;

    private String address;

    private String businessLicenseUrl;

    private String agentBrand;

    private String qualiUploadUrl;

    private String carPicUrl;

    private String minePicUrl;

    private Integer numPlayer;

    private String dockingFinance;

    private long createTime;
    private Integer id;
    private Long userId;
    private String tokenId;
    private String phoneNumber;
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId == null ? null : tokenId.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserCardIdUrl() {
        return userCardIdUrl;
    }

    public void setUserCardIdUrl(String userCardIdUrl) {
        this.userCardIdUrl = userCardIdUrl == null ? null : userCardIdUrl.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph == null ? null : autograph.trim();
    }

    public Date getStartWork() {
        return startWork;
    }

    public void setStartWork(Date startWork) {
        this.startWork = startWork;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    public void setBusinessLicenseUrl(String businessLicenseUrl) {
        this.businessLicenseUrl = businessLicenseUrl == null ? null : businessLicenseUrl.trim();
    }

    public String getAgentBrand() {
        return agentBrand;
    }

    public void setAgentBrand(String agentBrand) {
        this.agentBrand = agentBrand == null ? null : agentBrand.trim();
    }

    public String getQualiUploadUrl() {
        return qualiUploadUrl;
    }

    public void setQualiUploadUrl(String qualiUploadUrl) {
        this.qualiUploadUrl = qualiUploadUrl == null ? null : qualiUploadUrl.trim();
    }

    public String getCarPicUrl() {
        return carPicUrl;
    }

    public void setCarPicUrl(String carPicUrl) {
        this.carPicUrl = carPicUrl == null ? null : carPicUrl.trim();
    }

    public String getMinePicUrl() {
        return minePicUrl;
    }

    public void setMinePicUrl(String minePicUrl) {
        this.minePicUrl = minePicUrl == null ? null : minePicUrl.trim();
    }

    public Integer getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(Integer numPlayer) {
        this.numPlayer = numPlayer;
    }

    public String getDockingFinance() {
        return dockingFinance;
    }

    public void setDockingFinance(String dockingFinance) {
        this.dockingFinance = dockingFinance == null ? null : dockingFinance.trim();
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}