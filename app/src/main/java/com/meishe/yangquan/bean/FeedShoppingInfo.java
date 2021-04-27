package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/2/20 11:32
 * @Description : 饲料商店列表数据
 */
public class FeedShoppingInfo extends BaseInfo {

//  "id": 7,
//            "name": "跃跃",
//            "shopType": "个体工商户",
//            "mainCategory": "五金电料",
//            "longitude": null,
//            "latitude": null,
//            "address": "新奥特科技大厦",
//            "goodsSource": "分销代销",
//            "sign": "null",
//            "shopScore": 0,
//            "shopOutSideImageId": 276,
//            "shopOutSideImageUrl": "http://59.110.142.42:9999/shop_outside/2021-03/01/1614565556341.jpg",
//            "shopInsideImageIds": "279",
//            "shopInSideImageUrls": [
//        "http://59.110.142.42:9999/shop_inside/2021-03/01/1614565556433.jpg"
//            ],
//        "ownerName": "刘盘风",
//            "ownerIdCardNum": "130627198709293639",
//            "ownerIdCardFrontImageId": 278,
//            "ownerIdCardFrontImageUrl": "http://59.110.142.42:9999/user_id_card_front/2021-03/01/1614565556408.jpg",
//            "ownerIdCardReverseImageId": null,
//            "ownerIdCardReverseImageUrl": null,
//            "businessName": null,
//            "businessCreditCode": null,
//            "businessScope": null,
//            "businessPeriod": null,
//            "businessCardImageId": null,
//            "businessCardImageUrl": null,
//            "initUser": 9,
//            "initDate": 1614565557000,
//            "modDate": null,
//            "authDate": 1614870769000,
//            "authState": 1,
//            "authUser": 1,
//            "state": 1,
//            "nickname": "新修改",
//            "iconUrl": "http://59.110.142.42:9999/user_icon/2021-03/02/1614694964643.jpg",
//            "authOption": null

    private int id;

    private String name;

    private String shopType;

    private String mainCategory;

    private long longitude;

    private long latitude;

    private String address;

    private String goodsSource;

    private String sign;

    private float shopScore;

    private int shopOutSideImageId;

    private String shopOutSideImageUrl;

    private String shopInsideImageIds;

    private List<String> shopInSideImageUrls;

    private String ownerName;

    private String ownerIdCardNum;
    private int ownerIdCardFrontImageId;

    private String ownerIdCardFrontImageUrl;

    private String ownerIdCardReverseImageId;

    private String ownerIdCardReverseImageUrl;

    private String businessName;

    private String businessCreditCode;

    private String businessScope;

    private String businessPeriod;

    private String businessCardImageId;
    private String businessCardImageUrl;
    private int initUser;

    private long initDate;
    private String modDate;
    private long authDate;
    private int authState;
    private int authUser;
    private int state;

    private String nickname;
    private String iconUrl;
    private String authOption;
    /*销量*/
    private int sellAmount;

   /*是否关注了店铺*/
    private boolean hasCollected;

    private String shopPhone;

   /*  if (type == 1) {
        param.put("mainCategory", "饲料");
    } else if (type == 2) {
        param.put("mainCategory", "玉米");
    } else if (type == 3) {
        param.put("mainCategory", "五金电料");
    }*/
    /*请求数据类型  饲料 玉米 五金电料  这里定义的规则不能变*/
    private int type;





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

    public float getShopScore() {
        return shopScore;
    }

    public void setShopScore(float shopScore) {
        this.shopScore = shopScore;
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

    public String getOwnerIdCardReverseImageId() {
        return ownerIdCardReverseImageId;
    }

    public void setOwnerIdCardReverseImageId(String ownerIdCardReverseImageId) {
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

    public String getBusinessCardImageId() {
        return businessCardImageId;
    }

    public void setBusinessCardImageId(String businessCardImageId) {
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

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public long getAuthDate() {
        return authDate;
    }

    public void setAuthDate(long authDate) {
        this.authDate = authDate;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public int getAuthUser() {
        return authUser;
    }

    public void setAuthUser(int authUser) {
        this.authUser = authUser;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getAuthOption() {
        return authOption;
    }

    public void setAuthOption(String authOption) {
        this.authOption = authOption;
    }

    public int getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(int sellAmount) {
        this.sellAmount = sellAmount;
    }


    public boolean isHasCollected() {
        return hasCollected;
    }

    public void setHasCollected(boolean hasCollected) {
        this.hasCollected = hasCollected;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
