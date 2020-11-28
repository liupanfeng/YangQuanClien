package com.meishe.yangquan.bean;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/11/28 17:57
 */
public class ImageCodeInfo extends BaseInfo{

    private String base64;

    private String uuid;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
