package com.meishe.libbase.adpater.entity;

import java.io.Serializable;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * 多实体类
 * Many entity class
 *
 * @param <T> the type parameter
 */
public abstract class SectionMultiEntity<T> implements Serializable, MultiItemEntity {

    public boolean isHeader;
    public T t;
    public String header;

    public SectionMultiEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
    }

    public SectionMultiEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
    }
}
