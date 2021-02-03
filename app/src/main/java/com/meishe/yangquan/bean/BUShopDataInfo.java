package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/29
 * @Description : 商版-主页-店铺数据
 */
public class BUShopDataInfo extends BaseInfo {

    private String name;

    private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
