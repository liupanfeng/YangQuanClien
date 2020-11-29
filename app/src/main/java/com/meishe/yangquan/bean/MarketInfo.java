package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @author liupanfeng
 * @desc 市场数据
 * @date 2020/11/29 16:09
 */
public class MarketInfo extends BaseInfo{

// "amount": 1000,
//         "images": [
//         "http://localhost:9999/market/2020-11/22/1606045255640.jpg",
//         "http://localhost:9999/market/2020-11/22/1606045260384.jpg",
//         "http://localhost:9999/market/2020-11/22/1606045264593.jpg"
//         ],
//         "address": "北京",
//         "authState": 1,
//         "variety": "肉羊",
//         "phone": "12300000000",
//         "price": 900.00,
//         "initDate": "2020-11-22 19:45:06",
//         "nickname": "名人",
//         "weight": 30.00,
//         "id": 19,
//         "title": "买山羊12"

    private int id;
    private int amount;
    private List<String> images;
    private String address;
    private int authState;
    private String variety;
    private String phone;
    private float price;
    private String initDate;
    private String nickname;
    private float weight;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
