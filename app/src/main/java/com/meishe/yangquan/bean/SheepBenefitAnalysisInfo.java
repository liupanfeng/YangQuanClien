package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/6
 * @Description : 效益分析接口
 */
public class SheepBenefitAnalysisInfo  extends BaseInfo{

//    "sheepCount": 190,
//            "totalIncome": 100000,
//            "eachIncome": 526.3157894736842,
//            "totalLittleSheepExpense": 2466.42,
//            "eachLittleSheepExpense": 11.11,
//            "totalWoolExpense": 22.5,
//            "eachWoolExpense": 0.11842105263157894,

//            "totalFodderExpense": 450.8,
//            "eachFodderExpense": 2.3726315789473684,

//            "totalVaccineExpense": 36.4,
//            "eachVaccineExpense": 0.19157894736842104,

//            "totalLostExpense": 42,
//            "eachLostExpense": 0.22105263157894736,

//            "totalExpense": 2976.12,
//            "totalProfit": 97023.88,


//            "dailyGain": -0.0006666666666666524,
//            "cavityRatio": 1,

//            "fodderSheepRatio": -0.006031091964787414,
//            "fodderCavityRatio": -0.0008805670570566043

    private int sheepCount;

    private float totalIncome;

    private float eachIncome;

    private float totalLittleSheepExpense;

    private float eachLittleSheepExpense;

    public int getSheepCount() {
        return sheepCount;
    }

    public void setSheepCount(int sheepCount) {
        this.sheepCount = sheepCount;
    }

    public float getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(float totalIncome) {
        this.totalIncome = totalIncome;
    }

    public float getEachIncome() {
        return eachIncome;
    }

    public void setEachIncome(float eachIncome) {
        this.eachIncome = eachIncome;
    }

    public float getTotalLittleSheepExpense() {
        return totalLittleSheepExpense;
    }

    public void setTotalLittleSheepExpense(float totalLittleSheepExpense) {
        this.totalLittleSheepExpense = totalLittleSheepExpense;
    }

    public float getEachLittleSheepExpense() {
        return eachLittleSheepExpense;
    }

    public void setEachLittleSheepExpense(float eachLittleSheepExpense) {
        this.eachLittleSheepExpense = eachLittleSheepExpense;
    }

    public float getTotalWoolExpense() {
        return totalWoolExpense;
    }

    public void setTotalWoolExpense(float totalWoolExpense) {
        this.totalWoolExpense = totalWoolExpense;
    }

    public float getEachWoolExpense() {
        return eachWoolExpense;
    }

    public void setEachWoolExpense(float eachWoolExpense) {
        this.eachWoolExpense = eachWoolExpense;
    }

    public float getTotalFodderExpense() {
        return totalFodderExpense;
    }

    public void setTotalFodderExpense(float totalFodderExpense) {
        this.totalFodderExpense = totalFodderExpense;
    }

    public float getEachFodderExpense() {
        return eachFodderExpense;
    }

    public void setEachFodderExpense(float eachFodderExpense) {
        this.eachFodderExpense = eachFodderExpense;
    }

    public float getTotalVaccineExpense() {
        return totalVaccineExpense;
    }

    public void setTotalVaccineExpense(float totalVaccineExpense) {
        this.totalVaccineExpense = totalVaccineExpense;
    }

    public float getEachVaccineExpense() {
        return eachVaccineExpense;
    }

    public void setEachVaccineExpense(float eachVaccineExpense) {
        this.eachVaccineExpense = eachVaccineExpense;
    }

    public float getTotalLostExpense() {
        return totalLostExpense;
    }

    public void setTotalLostExpense(float totalLostExpense) {
        this.totalLostExpense = totalLostExpense;
    }

    public float getEachLostExpense() {
        return eachLostExpense;
    }

    public void setEachLostExpense(float eachLostExpense) {
        this.eachLostExpense = eachLostExpense;
    }

    public float getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(float totalExpense) {
        this.totalExpense = totalExpense;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }

    public float getDailyGain() {
        return dailyGain;
    }

    public void setDailyGain(float dailyGain) {
        this.dailyGain = dailyGain;
    }

    public float getCavityRatio() {
        return cavityRatio;
    }

    public void setCavityRatio(float cavityRatio) {
        this.cavityRatio = cavityRatio;
    }

    public float getFodderSheepRatio() {
        return fodderSheepRatio;
    }

    public void setFodderSheepRatio(float fodderSheepRatio) {
        this.fodderSheepRatio = fodderSheepRatio;
    }

    public float getFodderCavityRatio() {
        return fodderCavityRatio;
    }

    public void setFodderCavityRatio(float fodderCavityRatio) {
        this.fodderCavityRatio = fodderCavityRatio;
    }

    /*羊毛费用支持*/
    private float totalWoolExpense;
    private float eachWoolExpense;
    /*饲料费用支出*/
    private float totalFodderExpense;
    private float eachFodderExpense;
    /*疫苗费用支出*/
    private float totalVaccineExpense;
    private float eachVaccineExpense;

    /*损耗费用支出*/
    private float totalLostExpense;
    private float eachLostExpense;

    private float totalExpense;
    private float totalProfit;

    private float dailyGain;
    private float cavityRatio;

    private float fodderSheepRatio;
    private float fodderCavityRatio;


}
