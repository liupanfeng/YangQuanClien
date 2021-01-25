package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.IndustryNewsClip;
import com.meishe.yangquan.bean.SheepBarCommentSecondaryInfo;
import com.meishe.yangquan.utils.FormatCurrentData;

/**
 * 资讯详情页面
 *
 * @author 86188
 */
public class IndustryContentHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*资讯图片*/
    private ImageView iv_industry_picture;

    /*资讯内容*/
    private TextView tv_industry_content;


    public IndustryContentHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        iv_industry_picture = view.findViewById(R.id.iv_industry_picture);
        tv_industry_content = view.findViewById(R.id.tv_industry_content);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof IndustryNewsClip) {

            String clipType = ((IndustryNewsClip) info).getType();
            if ("text".equals(clipType)){
                //内容
                tv_industry_content.setText(((IndustryNewsClip) info).getContent());
                tv_industry_content.setVisibility(View.VISIBLE);
                iv_industry_picture.setVisibility(View.GONE);
            }else if ("image".equals(clipType)){
                tv_industry_content.setVisibility(View.GONE);
                iv_industry_picture.setVisibility(View.VISIBLE);
                String iconUrl = ((IndustryNewsClip) info).getContent();
                if (iconUrl != null) {
                    Glide.with(context)
                            .asBitmap()
                            .load(iconUrl)
                            .apply(options)
                            .into(iv_industry_picture);

                }
            }


        }

    }


}
