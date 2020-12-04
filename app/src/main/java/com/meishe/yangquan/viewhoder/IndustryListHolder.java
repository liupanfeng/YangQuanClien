package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.IndustryInfo;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 行业资讯
 * @author 86188
 */
public class IndustryListHolder extends BaseViewHolder {

    /*资讯标题*/
    private TextView mTvTitle;
    /*资讯索引*/
    private TextView mTvIndex;


    public IndustryListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;

    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvIndex=view.findViewById(R.id.tv_index);
        mTvTitle=view.findViewById(R.id.tv_title);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info,int position, View.OnClickListener listener) {
        if (info instanceof IndustryInfo){
            mTvIndex.setText((position+1)+"");
            mTvTitle.setText(((IndustryInfo) info).getTitle());
        }
    }


}
