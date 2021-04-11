package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/9 13:47
 * @Description :我的-收藏
 * */
public class MineCollectionInfo extends BaseInfo{

//    "id": 2,
//            "name": "好东西1",
//            "imageUrl": "http://localhost:9999/shop_goods/2021-01/03/1609656006029.jpg",
//            "price": 123.3,
//            "score": null,
//            "address": null,
//            "sellAmount": 0



    private int id;

    private String name;

    private String imageUrl;

    private float price;

    private float score;

    private String address;

    private String sellAmount;







    /*上层数据展示*/
    private int type;







    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(String sellAmount) {
        this.sellAmount = sellAmount;
    }
}
