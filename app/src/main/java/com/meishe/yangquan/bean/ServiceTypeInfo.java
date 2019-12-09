package com.meishe.yangquan.bean;

/**
 * 服务页面的中间菜单选择
 */
public class ServiceTypeInfo extends BaseInfo{

    private String name;

    private int icon;

    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
