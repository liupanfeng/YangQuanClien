package com.meishe.yangquan.bean;

import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/5/6 18:41
 * @Description : 饲料-评论数据
 */
public class FeedCommentInfo {

    private int count;

    private List<FeedCommentElementInfo> elements;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<FeedCommentElementInfo> getElements() {
        return elements;
    }

    public void setElements(List<FeedCommentElementInfo> elements) {
        this.elements = elements;
    }


    public class FeedCommentElementInfo extends BaseInfo{
//"id": 4,
//        "orderId": 15,
//        "goodsId": 3,
//        "initUser": 2,
//        "nickname": "张三-3-1",
//        "iconUrl": "http://localhost:9999/user_icon/2021-03/01/1614612608874.jpg",
//        "initDate": 1617552150000,
//        "score": 5,
//        "description": "宝贝很不错1",
//        "anonymity": "true",
//        "shopId": 6,
//        "images": []

        private int id;

        private int orderId;

        private int goodsId;

        private int shopId;

        private int initUser;

        private String nickname;

        private String iconUrl;

        private long initDate;

        private float score;

        private String description;

        private String anonymity;

        private List<String> images;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getInitUser() {
            return initUser;
        }

        public void setInitUser(int initUser) {
            this.initUser = initUser;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public long getInitDate() {
            return initDate;
        }

        public void setInitDate(long initDate) {
            this.initDate = initDate;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAnonymity() {
            return anonymity;
        }

        public void setAnonymity(String anonymity) {
            this.anonymity = anonymity;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
