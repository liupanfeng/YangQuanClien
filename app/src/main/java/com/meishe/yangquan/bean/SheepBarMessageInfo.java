package com.meishe.yangquan.bean;

import java.util.List;

/**
 * @author liupanfeng
 * @desc  羊吧列表
 * @date 2020/12/20 17:10
 */
public class SheepBarMessageInfo extends BaseInfo{

    private String photoPath;

    private String nickName;

    private String time;

    private String content;
    /*评论数量*/
    private int commentNumber;
    /*点赞数量*/
    private int priseNumber;
    /*羊吧图片列表*/
    private List<String> imageList;
    /*是否已经关注*/
    private boolean isFocus;




}
