package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/2/20 11:32
 * @Description : 饲料商品列表数据
 */
public class FeedShoppingCarGoodsInfo extends BaseInfo {


// "id": 4,
//            "shopId": 7,
//            "shopName": null,
//            "title": "优质预混料",
//            "goodsImageFileIds": "319,320,324",
//            "goodsImageUrls": [
//        "http://59.110.142.42:9999/shop_goods/2021-03/04/1614870790386.jpg",
//                "http://59.110.142.42:9999/shop_goods/2021-03/04/1614870789423.jpg",
//                "http://59.110.142.42:9999/shop_goods/2021-03/04/1614870789461.jpg"
//            ],
//        "firstCategory": "饲料",
//            "secondCategory": "预混料",
//            "place": "海特花园(苹果园大街)",
//            "weight": 56,
//            "specification": "袋",
//            "address": "Bless美容美体生活馆",
//            "isPublic": 1,
//            "brand": "天牌",
//            "storeAmount": 563,
//            "sellAmount": 0,
//            "price": 156,
//            "description": "物美价廉优质预混料！",
//            "descriptionImageFileIds": null,
//            "descriptionImageUrls": null,
//            "modDate": 1614912242000


     private int id;

     private int shopId;

     private String shopName;

     private String title;

     private String goodsImageFileIds;

     private List<String> goodsImageUrls;

     private String firstCategory;

     private String secondCategory;
     private String place;

     private float weight;

     private String specification;

     private String address;

     private int isPublic;

     private String brand;

     private int storeAmount;

     private int sellAmount;

     private float price;

     private String description;

     private String descriptionImageFileIds;

     private List<String> descriptionImageUrls;

     private long modDate;
     /*选择数量-下单的时候选择的数量*/
     private int selectAmount=1;

     /*购物车是否选中*/
     private boolean isSelect;
    /*是否需要隐藏选择框*/
     private boolean isNeedHideSelect;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoodsImageFileIds() {
        return goodsImageFileIds;
    }

    public void setGoodsImageFileIds(String goodsImageFileIds) {
        this.goodsImageFileIds = goodsImageFileIds;
    }

    public List<String> getGoodsImageUrls() {
        return goodsImageUrls;
    }

    public void setGoodsImageUrls(List<String> goodsImageUrls) {
        this.goodsImageUrls = goodsImageUrls;
    }

    public String getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(int storeAmount) {
        this.storeAmount = storeAmount;
    }

    public int getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(int sellAmount) {
        this.sellAmount = sellAmount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionImageFileIds() {
        return descriptionImageFileIds;
    }

    public void setDescriptionImageFileIds(String descriptionImageFileIds) {
        this.descriptionImageFileIds = descriptionImageFileIds;
    }

    public List<String> getDescriptionImageUrls() {
        return descriptionImageUrls;
    }

    public void setDescriptionImageUrls(List<String> descriptionImageUrls) {
        this.descriptionImageUrls = descriptionImageUrls;
    }

    public long getModDate() {
        return modDate;
    }

    public void setModDate(long modDate) {
        this.modDate = modDate;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {

        isSelect = select;
    }


    public boolean isNeedHideSelect() {
        return isNeedHideSelect;
    }

    public void setNeedHideSelect(boolean needHideSelect) {

        isNeedHideSelect = needHideSelect;
    }

    public int getSelectAmount() {
        return selectAmount;
    }

    public void setSelectAmount(int selectAmount) {
        this.selectAmount = selectAmount;
    }

    /**
     * 数据类型转化FeedGoodsInfo ---》FeedShoppingCarGoodsInfo
     * @param feedGoodsInfo
     * @return
     */
    public static FeedShoppingCarGoodsInfo parseGoodsInfo(FeedGoodsInfo feedGoodsInfo) {
        FeedShoppingCarGoodsInfo shoppingCarGoodsInfo=new FeedShoppingCarGoodsInfo();
        shoppingCarGoodsInfo.setBrand(feedGoodsInfo.getBrand());
        shoppingCarGoodsInfo.setId(feedGoodsInfo.getId());
        shoppingCarGoodsInfo.setAddress(feedGoodsInfo.getAddress());
        shoppingCarGoodsInfo.setDescription(feedGoodsInfo.getDescription());
        shoppingCarGoodsInfo.setDescriptionImageFileIds(feedGoodsInfo.getDescriptionImageFileIds());
        shoppingCarGoodsInfo.setDescriptionImageUrls(feedGoodsInfo.getDescriptionImageUrls());
        shoppingCarGoodsInfo.setGoodsImageUrls(feedGoodsInfo.getGoodsImageUrls());
        shoppingCarGoodsInfo.setFirstCategory(feedGoodsInfo.getFirstCategory());
        shoppingCarGoodsInfo.setSelectAmount(1);
        shoppingCarGoodsInfo.setStoreAmount(feedGoodsInfo.getStoreAmount());
        shoppingCarGoodsInfo.setIsPublic(feedGoodsInfo.getIsPublic());
        shoppingCarGoodsInfo.setPrice(feedGoodsInfo.getPrice());
        shoppingCarGoodsInfo.setModDate(feedGoodsInfo.getModDate());
        shoppingCarGoodsInfo.setPlace(feedGoodsInfo.getPlace());
        return shoppingCarGoodsInfo;
    }


}
