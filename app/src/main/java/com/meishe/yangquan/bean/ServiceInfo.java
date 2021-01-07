package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @author liupanfeng
 * @desc 首页-服务数据
 * @date 2020/11/29 16:09
 */
public class ServiceInfo extends BaseInfo {

    //    {
//        "code": 1,
//            "enMsg": "success",
//            "msg": "成功",
//            "data": [{
//        "images": [],
//        "authState": 1,
//                "initDate": "2020-12-12 21:20:39",
//                "max_height": 4.50,
//                "team_desc": "专业团队",
//                "team_car_scale": 5,
//                "team_name": "剪羊毛6",
//                "car_type": 1,
//                "car_volume": "3m*5m*3m",
//                "team_human_scale": 5,
//                "service_type": "2",
//                "phone": "13254543434",
//                "price": 800.00,
//                "id": 10
//    }]
//    }
    /*服务类型 剪羊毛 打疫苗 找车辆*/
    private int serverType;

    private int id;
    private int authState;
    private List<String> images;
    private long initDate;

    private float max_height;
    private String team_desc;

    private int team_car_scale;
    private String team_name;

    private int car_type;

    private String car_volume;

    private int team_human_scale;

    private int service_type;


    private String phone;

    private float price;

    private String nickname;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public float getMax_height() {
        return max_height;
    }

    public void setMax_height(float max_height) {
        this.max_height = max_height;
    }

    public String getTeam_desc() {
        return team_desc;
    }

    public void setTeam_desc(String team_desc) {
        this.team_desc = team_desc;
    }

    public int getTeam_car_scale() {
        return team_car_scale;
    }

    public void setTeam_car_scale(int team_car_scale) {
        this.team_car_scale = team_car_scale;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getCar_type() {
        return car_type;
    }

    public void setCar_type(int car_type) {
        this.car_type = car_type;
    }

    public String getCar_volume() {
        return car_volume;
    }

    public void setCar_volume(String car_volume) {
        this.car_volume = car_volume;
    }

    public int getTeam_human_scale() {
        return team_human_scale;
    }

    public void setTeam_human_scale(int team_human_scale) {
        this.team_human_scale = team_human_scale;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void setPrice(float price) {
        this.price = price;
    }


    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public float getPrice() {
        return price;
    }
}
