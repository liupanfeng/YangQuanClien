package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServiceTypeInfo;

public class ServiceTypeListHolder extends BaseViewHolder {

    private TextView mTvServiceType;
    private ImageView mIvServiceType;
    private LinearLayout mLlServiceType;
    private Button mBtnOrder;                       //马上预约

    public ServiceTypeListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
//        mTvServiceType=view.findViewById(R.id.tv_service_type);
        mIvServiceType=view.findViewById(R.id.iv_service_zan);
        mBtnOrder=view.findViewById(R.id.btn_order);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
//        ServerCustomer serverCustomer= (ServerCustomer) info;
//        mTvServiceType.setText(serverCustomer.getDescription());
        mBtnOrder.setOnClickListener(listener);
        mBtnOrder.setTag(info);

        mIvServiceType.setOnClickListener(listener);
        mIvServiceType.setTag(info);

    }


}
