package com.meishe.yangquan.bean;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/15 15:16
 * @Description : 收货信息数据结构  注意这里用户版也在使用这个类
 */
public class BUReceiveInfo {

//      "address": "红星花园十六号楼七单元1808",
//              "area": "北京市朝阳区十里铺街道",
//              "name": "张三",
//              "phone": "177****1312"

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
