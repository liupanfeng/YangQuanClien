package com.meishe.yangquan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.libbase.adpater.BaseSectionQuickAdapter;
import com.meishe.libbase.adpater.BaseViewHolder;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.IndustryNewsClip;
import com.meishe.yangquan.bean.IndustryNewsInfo;
import com.meishe.yangquan.bean.section.QuotationNewsSection;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/26
 * @Description : 资讯新闻详情页面
 */
public class QuotationNewsAdapter extends BaseSectionQuickAdapter<QuotationNewsSection, BaseViewHolder> {

    private final RequestOptions mOptions;
    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * 与QuickAdapter#QuickAdapter(Context,int)相同，但与
     * 一些初始化数据
     */
    public QuotationNewsAdapter(Context context) {
        super(R.layout.item_industry_content, R.layout.header_quotation_news, null);
        this.mContext = context;
        mOptions = new RequestOptions();
        mOptions.centerCrop();
    }

    @Override
    protected void convertHead(BaseViewHolder helper, QuotationNewsSection item) {
        IndustryNewsInfo industryNewsInfo = item.getIndustryNewsInfo();
        if (industryNewsInfo == null) {
            return;
        }

        helper.setText(R.id.tv_industry_title, industryNewsInfo.getTitle());
        helper.setText(R.id.tv_author_and_time, industryNewsInfo.getAuthor() + " | " + FormatDateUtil.
                longToString(industryNewsInfo.getPublishDate(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
        helper.setText(R.id.tv_from, "来源于：" + industryNewsInfo.getNewsSource());

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, QuotationNewsSection item) {
        IndustryNewsClip industryNewsClip = item.t;
        if (industryNewsClip == null) {
            return;
        }


        String clipType = industryNewsClip.getType();
        if ("text".equals(clipType)) {
            //内容
            helper.getView(R.id.tv_industry_content).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_industry_picture).setVisibility(View.GONE);
            helper.setText(R.id.tv_industry_content, (industryNewsClip.getContent()));

        } else if ("image".equals(clipType)) {
            helper.getView(R.id.tv_industry_content).setVisibility(View.GONE);
            helper.getView(R.id.iv_industry_picture).setVisibility(View.VISIBLE);
            String iconUrl = industryNewsClip.getContent();
            if (iconUrl != null) {
                Glide.with(mContext)
                        .asBitmap()
                        .load(iconUrl)
                        .apply(mOptions)
                        .into((ImageView) helper.getView(R.id.iv_industry_picture));

            }
        }


    }
}
