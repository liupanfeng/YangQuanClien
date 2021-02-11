package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/6
 * @Description :
 */
public class FodderInfo extends BaseInfo {

//    "fodderId": 2,
//            "weight": 11.1,
//            "percent": 21.1,
//            "price": 31.1,
//            "totalPrice": 110.2

    private String name;

    private int fodderId;

    private float weight;

    private float percent;

    private float price;

    private float totalPrice;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFodderId() {
        return fodderId;
    }

    public void setFodderId(int fodderId) {
        this.fodderId = fodderId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
