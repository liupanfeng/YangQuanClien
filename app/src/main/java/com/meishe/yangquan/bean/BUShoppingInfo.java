package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/27
 * @Description : 商版-店铺信息
 */
public class BUShoppingInfo {

//    "id": 1,
////            "name": "我的店铺1",
////            "shopType": "有限公司1",
////            "mainCategory": "玉米1",
////            "longitude": 1231,
////            "latitude": 1231,
////            "address": "北京是海定区1",
////            "goodsSource": "厂家直销1",
////            "sign": "我就是最牛逼的!1",
////            "shopOutSideImageId": 62,
////            "shopOutSideImageUrl": "http://localhost:9999/shop_outside/2021-01/03/1609647542734.jpg",
////            "shopInsideImageIds": "63,64",
////            "shopInSideImageUrls": [
////            "http://localhost:9999/shop_inside/2021-01/03/1609647581222.jpg",
////            "http://localhost:9999/shop_inside/2021-01/03/1609647577194.jpg"
////            ],
////            "ownerName": "张三1",
////            "ownerIdCardNum": "1101101955020311111",
////            "ownerIdCardFrontImageId": 65,
////            "ownerIdCardFrontImageUrl": "http://localhost:9999/user_id_card_front/2021-01/03/1609647828574.jpg",
////            "ownerIdCardReverseImageId": 66,
////            "ownerIdCardReverseImageUrl": "http://localhost:9999/user_id_card_reverse/2021-01/03/1609647835394.jpg",
////            "businessName": "百度有限公司1",
////            "businessCreditCode": "1231231231KLKHJLK1",
////            "businessScope": "数码1",
////            "businessPeriod": "2020.12.231",
////            "businessCardImageId": 67,
////            "businessCardImageUrl": "http://localhost:9999/business_card/2021-01/03/1609647853803.jpg",
////            "initUser": 2,
////            "initDate": 1609648625000,
////            "modDate": 1609648865000,
////            "authState": 0,
////            "state": 1,
////            "nickname": "张三",
////            "iconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg"

    private int id;
    /*店铺名称*/
    private String name;
    /*店铺类型*/
    private String shopType;
    /*主营类型*/
    private String mainCategory;

    private long longitude;

    private long latitude;

    /*店铺地址*/
    private String address;
    /*主要货源*/
    private String goodsSource;
    /*店铺签名*/
    private String sign;

    private int shopOutSideImageId;

    /*店外照片*/
    private String shopOutSideImageUrl;

    private String shopInsideImageIds;

    /*店内照片*/
    private List<String> shopInSideImageUrls;

    /*真实姓名*/
    private String ownerName;
    /*身份证号*/
    private String ownerIdCardNum;

    private int ownerIdCardFrontImageId;

    /*身份证正面图*/
    private String ownerIdCardFrontImageUrl;

    private int ownerIdCardReverseImageId;

    /*身份证反面图*/
    private String ownerIdCardReverseImageUrl;
    /*营业执照名称*/
    private String businessName;
    /*社会信用码*/
    private String businessCreditCode;
    /*经营范围*/
    private String businessScope;
    /*到期时间*/
    private String businessPeriod;

    private int businessCardImageId;

    /*营业执照照片*/
    private String businessCardImageUrl;

    private int initUser;

    private long initDate;

    private long modDate;

    private int authState;

    private String nickname;

    private String iconUrl;


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

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoodsSource() {
        return goodsSource;
    }

    public void setGoodsSource(String goodsSource) {
        this.goodsSource = goodsSource;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getShopOutSideImageId() {
        return shopOutSideImageId;
    }

    public void setShopOutSideImageId(int shopOutSideImageId) {
        this.shopOutSideImageId = shopOutSideImageId;
    }

    public String getShopOutSideImageUrl() {
        return shopOutSideImageUrl;
    }

    public void setShopOutSideImageUrl(String shopOutSideImageUrl) {
        this.shopOutSideImageUrl = shopOutSideImageUrl;
    }

    public String getShopInsideImageIds() {
        return shopInsideImageIds;
    }

    public void setShopInsideImageIds(String shopInsideImageIds) {
        this.shopInsideImageIds = shopInsideImageIds;
    }

    public List<String> getShopInSideImageUrls() {
        return shopInSideImageUrls;
    }

    public void setShopInSideImageUrls(List<String> shopInSideImageUrls) {
        this.shopInSideImageUrls = shopInSideImageUrls;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdCardNum() {
        return ownerIdCardNum;
    }

    public void setOwnerIdCardNum(String ownerIdCardNum) {
        this.ownerIdCardNum = ownerIdCardNum;
    }

    public int getOwnerIdCardFrontImageId() {
        return ownerIdCardFrontImageId;
    }

    public void setOwnerIdCardFrontImageId(int ownerIdCardFrontImageId) {
        this.ownerIdCardFrontImageId = ownerIdCardFrontImageId;
    }

    public String getOwnerIdCardFrontImageUrl() {
        return ownerIdCardFrontImageUrl;
    }

    public void setOwnerIdCardFrontImageUrl(String ownerIdCardFrontImageUrl) {
        this.ownerIdCardFrontImageUrl = ownerIdCardFrontImageUrl;
    }

    public int getOwnerIdCardReverseImageId() {
        return ownerIdCardReverseImageId;
    }

    public void setOwnerIdCardReverseImageId(int ownerIdCardReverseImageId) {
        this.ownerIdCardReverseImageId = ownerIdCardReverseImageId;
    }

    public String getOwnerIdCardReverseImageUrl() {
        return ownerIdCardReverseImageUrl;
    }

    public void setOwnerIdCardReverseImageUrl(String ownerIdCardReverseImageUrl) {
        this.ownerIdCardReverseImageUrl = ownerIdCardReverseImageUrl;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessCreditCode() {
        return businessCreditCode;
    }

    public void setBusinessCreditCode(String businessCreditCode) {
        this.businessCreditCode = businessCreditCode;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessPeriod() {
        return businessPeriod;
    }

    public void setBusinessPeriod(String businessPeriod) {
        this.businessPeriod = businessPeriod;
    }

    public int getBusinessCardImageId() {
        return businessCardImageId;
    }

    public void setBusinessCardImageId(int businessCardImageId) {
        this.businessCardImageId = businessCardImageId;
    }

    public String getBusinessCardImageUrl() {
        return businessCardImageUrl;
    }

    public void setBusinessCardImageUrl(String businessCardImageUrl) {
        this.businessCardImageUrl = businessCardImageUrl;
    }

    public int getInitUser() {
        return initUser;
    }

    public void setInitUser(int initUser) {
        this.initUser = initUser;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public long getModDate() {
        return modDate;
    }

    public void setModDate(long modDate) {
        this.modDate = modDate;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

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
}
