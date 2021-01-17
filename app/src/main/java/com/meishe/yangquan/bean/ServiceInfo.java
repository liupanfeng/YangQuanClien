package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @author liupanfeng
 * @desc 首页-服务数据
 * @date 2020/11/29 16:09
 */
public class ServiceInfo extends BaseInfo {

//    {
//        "id": 10,
//            "teamName": "剪羊毛6",
//            "teamHumanScale": 5,
//            "teamCarScale": 5,
//            "teamDesc": "专业团队",
//            "price": 800,
//            "phone": "13254543434",
//            "carType": 1,
//            "serviceType": 2,
//            "carVolume": "3m*5m*3m",
//            "maxHeight": 4.5,
//            "initDate": 1607779239000,
//            "initUser": 2,
//            "authState": 1,
//            "images": [ ]
//    },

    private int serverType;

    private int id;
    private int authState;
    private List<String> images;
    private long initDate;

    private float maxHeight;
    private String teamDesc;

    private int teamCarScale;
    private String teamName;

    private int carType;

    private String carVolume;

    private int teamHumanScale;

    private int serviceType;


    private String phone;

    private float price;

    private String nickname;

    private int initUser;


    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public int getTeamCarScale() {
        return teamCarScale;
    }

    public void setTeamCarScale(int teamCarScale) {
        this.teamCarScale = teamCarScale;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public String getCarVolume() {
        return carVolume;
    }

    public void setCarVolume(String carVolume) {
        this.carVolume = carVolume;
    }

    public int getTeamHumanScale() {
        return teamHumanScale;
    }

    public void setTeamHumanScale(int teamHumanScale) {
        this.teamHumanScale = teamHumanScale;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getInitUser() {
        return initUser;
    }

    public void setInitUser(int initUser) {
        this.initUser = initUser;
    }
}
