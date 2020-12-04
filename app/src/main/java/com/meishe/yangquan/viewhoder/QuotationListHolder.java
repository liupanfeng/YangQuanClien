package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.QuotationInfo;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 行情列表
 *
 * @author 86188
 */
public class QuotationListHolder extends BaseViewHolder {

    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_specification;
    private TextView tv_price;

    public QuotationListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_name = view.findViewById(R.id.tv_name);
        tv_address = view.findViewById(R.id.tv_address);
        tv_specification = view.findViewById(R.id.tv_specification);
        tv_price = view.findViewById(R.id.tv_price);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        if (info instanceof QuotationInfo) {
            QuotationInfo quotationInfo = (QuotationInfo) info;
            tv_name.setText(quotationInfo.getName());
            tv_address.setText(quotationInfo.getPlace());
            tv_specification.setText(quotationInfo.getSpecification()+"");
            tv_price.setText(quotationInfo.getTodayPrice() + "");
        }
    }


}
