package com.meishe.yangquan.bean;


import java.util.List;

/**
 *  评论数据结果
 */
@Deprecated
public class CommentListResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private List<Comment> data;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
