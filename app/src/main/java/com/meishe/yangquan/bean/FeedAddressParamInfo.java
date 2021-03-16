package com.meishe.yangquan.bean;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/16 16:41
 * @Description : 订单地址参数info
 */
public class FeedAddressParamInfo extends BaseInfo {

    /*收货人*/
    private String name;
    /*收货人电话*/
    private String phone;
    /*所在地区*/
    private String area;
    /*详细地址*/
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
