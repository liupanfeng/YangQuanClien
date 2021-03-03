package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/3 16:48
 * @Description : 商版商品数据
 */
public class BUGoodsInfo extends BaseInfo {

//    "id": 3,
//            "shopId": 6,
//            "title": "好东西",
//            "goodsImageFileIds": "68,69",
//            "goodsImageUrls": [
//            "http://localhost:9999/shop_goods/2021-01/03/1609656011971.jpg",
//            "http://localhost:9999/shop_goods/2021-01/03/1609656006029.jpg"
//            ],
//            "firstCategory": "一类",
//            "secondCategory": "二类",
//            "place": "产地",
//            "weight": 32.2,
//            "specification": "规格",
//            "address": "地址",
//            "isPublic": 0,
//            "brand": "品牌",
//            "storeAmount": 545,
//            "sellAmount": 0,
//            "price": 123.3,
//            "description": "就是好好的很"

    private int id;
    private int shopId;
    private String title;
    private String goodsImageFileIds;
    private List<String> goodsImageUrls;
    private String firstCategory;
    private String secondCategory;
    /*产地*/
    private String place;
    /*重量*/
    private float weight;
    /*规格*/
    private String specification;
    private String address;
    private int isPublic;
    /*品牌*/
    private String brand;

    private int storeAmount;
    /*已售数量*/
    private int sellAmount;
    private float price;

    private String description;


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
}
