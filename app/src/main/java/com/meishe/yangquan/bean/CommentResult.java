package com.meishe.yangquan.bean;


import java.util.List;

/**
 *  添加评论数据结果
 */
@Deprecated
public class CommentResult {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    private Comment data;

    public Comment getData() {
        return data;
    }

    public void setData(Comment data) {
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
