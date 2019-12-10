package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.view.RoundAngleImageView;

public class ServiceTypeListHolder extends BaseViewHolder {

    private final RequestOptions options;
    private TextView mTvServiceType;
    private ImageView mIvServiceType;
    private LinearLayout mLlServiceType;
    private Button mBtnOrder;                       //马上预约
    private RoundAngleImageView mIvServicePhoto;
    private TextView mTvServiceNickname;
    private TextView mTvServiceDesc;

    public ServiceTypeListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mIvServiceType=view.findViewById(R.id.iv_service_zan);
        mBtnOrder=view.findViewById(R.id.btn_order);
        mIvServicePhoto=view.findViewById(R.id.iv_service_photo);
        mTvServiceNickname=view.findViewById(R.id.tv_service_nickname);
        mTvServiceDesc=view.findViewById(R.id.tv_service_type_description);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        if (info instanceof ServerCustomer){
            ServerCustomer serverCustomer= (ServerCustomer) info;
            String desc=serverCustomer.getAutograph();
            if (TextUtils.isEmpty(desc)){
                mTvServiceDesc.setText("签名暂未添加，请到完善资料里边去完善签名信息！");
            }else {
                mTvServiceDesc.setText(desc);
            }
            mTvServiceNickname.setText(serverCustomer.getNickname());

            String photoUrl=serverCustomer.getPhotoUrl();
            Glide.with(context)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE+photoUrl)
                    .apply(options)
                    .into(mIvServicePhoto);

            mBtnOrder.setOnClickListener(listener);
            mBtnOrder.setTag(info);

            mIvServiceType.setOnClickListener(listener);
            mIvServiceType.setTag(info);
        }

    }


}
