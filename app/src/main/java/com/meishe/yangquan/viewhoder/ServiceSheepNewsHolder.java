package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepNews;

/**
 * 咨询新闻页面
 */
public class ServiceSheepNewsHolder extends BaseViewHolder {

    private TextView mTvServiceSheepName;
    private TextView mIvServiceSheepPrice;
    private TextView mIvServiceSheepRemarks;
    private Button mBtnCheckHistory;
    private View mInclude;

    public ServiceSheepNewsHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvServiceSheepName=view.findViewById(R.id.tv_service_sheep_name);
        mIvServiceSheepPrice=view.findViewById(R.id.tv_service_sheep_price);
        mIvServiceSheepRemarks=view.findViewById(R.id.tv_service_sheep_remarks);
        mBtnCheckHistory=view.findViewById(R.id.btn_check_history);
        mInclude=view.findViewById(R.id.item_label);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        if (info instanceof SheepNews){
            SheepNews sheepNews= (SheepNews) info;
            boolean isNeedShowLabel=sheepNews.isNeedShowLabel();
            if (isNeedShowLabel){
                mInclude.setVisibility(View.VISIBLE);
            }else{
                mInclude.setVisibility(View.GONE);
            }
            mTvServiceSheepName.setText(sheepNews.getSheepName());
            mIvServiceSheepPrice.setText(sheepNews.getSheepPrice()+"/斤");
            mIvServiceSheepRemarks.setText(sheepNews.getRemarks());
        }
        mBtnCheckHistory.setOnClickListener(listener);
        mBtnCheckHistory.setTag(info);
    }


}
