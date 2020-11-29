package com.meishe.yangquan.bean;

/**
 * @author liupanfeng
 * @desc 行情实体类
 * @date 2020/11/28 17:14
 */
public class QuotationInfo extends BaseInfo{


    private String id;
    private String name;
    /**
     * 规格
     */
    private int specification;
    private String place;

    private float todayPrice;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpecification() {
        return specification;
    }

    public void setSpecification(int specification) {
        this.specification = specification;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public float getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(float todayPrice) {
        this.todayPrice = todayPrice;
    }
}
