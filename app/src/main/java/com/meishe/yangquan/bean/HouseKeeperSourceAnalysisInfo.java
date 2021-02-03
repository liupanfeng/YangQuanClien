package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 养殖助手 -养殖过程 --配料分析 菜单
 */
public class HouseKeeperSourceAnalysisInfo extends BaseInfo {

//    "id": 2,
//            "name": "花生秧",
//            "brand": null,
//            "protein": 11.22,
//            "energy": 11.22,
//            "calcium": 11.22,
//            "phosphorus": 11.22,
//            "salt": 11.22,
//            "carbohydrate": null


    private int id;
    /*名称*/
    private String name;
    /*品牌*/
    private String brand;
    /*蛋白质*/
    private float protein;
    /*能量*/
    private float energy;
    /*钙*/
    private float calcium;
   /*磷*/
    private float phosphorus;
    /*盐*/
    private float salt;
    /*小苏打*/
    private float carbohydrate;


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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getCalcium() {
        return calcium;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public float getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(float phosphorus) {
        this.phosphorus = phosphorus;
    }

    public float getSalt() {
        return salt;
    }

    public void setSalt(float salt) {
        this.salt = salt;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
