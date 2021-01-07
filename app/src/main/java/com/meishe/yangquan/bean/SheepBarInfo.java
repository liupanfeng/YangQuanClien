package com.meishe.yangquan.bean;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/1/5 22:20
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class SheepBarInfo extends BaseInfo{

//    "id": 3,
//            "content": "哈哈哈，太累了123",
//            "initDate": "1608463228000",
//            "initUser": 2,
//            "nickname": "张三",
//            "iconUrl": "http://localhost:9999/user_icon/2020-12/06/1607242911519.jpg",
//            "shareAmount": 1,
//            "praiseAmount": 0,
//            "commentAmount": 0,
//            "hasPraised": false,
//            "images": [
//            "http://localhost:9999/sheep_bar_post/2020-12/20/1608463215907.jpg",
//            "http://localhost:9999/sheep_bar_post/2020-12/20/1608463191546.png"
//            ]

    private int id;

    private String content;

    private String nickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
