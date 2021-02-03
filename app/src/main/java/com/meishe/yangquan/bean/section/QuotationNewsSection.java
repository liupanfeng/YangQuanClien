package com.meishe.yangquan.bean.section;

import com.meishe.libbase.adpater.entity.SectionEntity;
import com.meishe.yangquan.bean.IndustryNewsClip;
import com.meishe.yangquan.bean.IndustryNewsInfo;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/26
 * @Description : 资讯新闻section
 */
public class QuotationNewsSection  extends SectionEntity<IndustryNewsClip> {
    private IndustryNewsInfo industryNewsInfo;
    public QuotationNewsSection(IndustryNewsClip industryNewsClip) {
        super(industryNewsClip);
    }

    public IndustryNewsInfo getIndustryNewsInfo() {
        return industryNewsInfo;
    }

    public void setIndustryNewsInfo(IndustryNewsInfo industryNewsInfo) {
        this.industryNewsInfo = industryNewsInfo;
    }

}
