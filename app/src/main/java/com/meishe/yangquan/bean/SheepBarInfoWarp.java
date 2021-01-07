package com.meishe.yangquan.bean;

import java.util.List;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/1/5 22:22
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class SheepBarInfoWarp extends BaseInfo{

    private int count;

    private List<SheepBarInfo> elements;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SheepBarInfo> getElements() {
        return elements;
    }

    public void setElements(List<SheepBarInfo> elements) {
        this.elements = elements;
    }
}
