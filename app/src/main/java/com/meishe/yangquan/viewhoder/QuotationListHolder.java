package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.QuotationInfo;

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
    private TextView tv_to_history;

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
        tv_to_history = view.findViewById(R.id.tv_to_history);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof QuotationInfo) {
            QuotationInfo quotationInfo = (QuotationInfo) info;
            tv_name.setText(quotationInfo.getName());
            tv_address.setText(quotationInfo.getPlace());
            tv_specification.setText(quotationInfo.getSpecification() + "");
            tv_price.setText(quotationInfo.getTodayPrice() + "");

            tv_to_history.setTag(info);
            tv_to_history.setOnClickListener(listener);

        }
    }


}
