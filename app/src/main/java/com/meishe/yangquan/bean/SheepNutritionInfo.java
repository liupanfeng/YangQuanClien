package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/10
 * @Description : 营养成分 数据结构
 */
public class SheepNutritionInfo extends BaseInfo {

//    "name": "蛋白质",
//            "standardValue": "15.00 - 16.00",
//            "userValue": "226.644",
//            "suggest": "您的配方蛋白偏高，羊容易造成梭菌发病，三联四防苗要及时打，建议减少蛋白原料"

    private String name;

    private String standardValue;

    private float userValue;

    private String suggest;

    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }


    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public float getUserValue() {
        return userValue;
    }

    public void setUserValue(float userValue) {
        this.userValue = userValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
