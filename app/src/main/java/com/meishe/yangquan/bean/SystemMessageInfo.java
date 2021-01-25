package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/23
 * @Description : 我的-系统消息
 */
public class SystemMessageInfo extends BaseInfo{

//    "id": 2,
//            "title": "全体消息",
//            "content": "恭喜你中奖了",
//            "initDate": 1607257789000

    private int id;

    private String title;

    private String content;

    private long initDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }


}
