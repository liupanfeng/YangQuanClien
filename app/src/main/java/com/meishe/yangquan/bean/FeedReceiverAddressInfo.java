package com.meishe.yangquan.bean;


/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/16 14:22
 * @Description : 收货地址列表
 */
public class FeedReceiverAddressInfo extends BaseInfo {



    /*收货人*/
    private String name;
    /*收货人电话*/
    private String phone;
    /*所在地区*/
    private String area;
    /*详细地址*/
    private String address;

    /*标签：家，公司等*/
    private String label;
    /*是否设置默认地址*/
    private boolean isDefaultAddress;


    public String getReceiverName() {
        return name;
    }

    public void setReceiverName(String receiverName) {
        this.name = receiverName;
    }

    public String getReceiverPhone() {
        return phone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.phone = receiverPhone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailAddress() {
        return address;
    }

    public void setDetailAddress(String detailAddress) {
        this.address = detailAddress;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDefaultAddress() {
        return isDefaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }
}
