package com.meishe.yangquan.bean;

/**
 * 羊吧 上传图片
 */
public class SheepBarPictureInfo extends BaseInfo {

    public static final int TYPE_ADD_PIC = 1;                   //增加

    public static final int TYPE_CAPTURE_PIC = 2;               //相机的图片


    private int type;

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
