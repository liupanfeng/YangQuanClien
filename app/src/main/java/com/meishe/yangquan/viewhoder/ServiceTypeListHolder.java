package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServerZan;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.LikesView;
import com.meishe.yangquan.view.RoundAngleImageView;

import java.util.List;

public class ServiceTypeListHolder extends BaseViewHolder {

    private final RequestOptions options;
    private Button mBtnOrder;                       //马上预约
    private RoundAngleImageView mIvServicePhoto;
    private TextView mTvServiceNickname;
    private TextView mTvServiceDesc;
    private TextView mTvZanNumber;
    private LikesView mLikeView;
    private CheckBox mCbServiceZan;

    public ServiceTypeListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mCbServiceZan = view.findViewById(R.id.cb_service_zan);
        mBtnOrder = view.findViewById(R.id.btn_order);
        mIvServicePhoto = view.findViewById(R.id.iv_service_photo);
        mTvServiceNickname = view.findViewById(R.id.tv_service_nickname);
        mTvServiceDesc = view.findViewById(R.id.tv_service_type_description);
        mTvZanNumber = view.findViewById(R.id.tv_zan_number);
        mLikeView = view.findViewById(R.id.lv_zan);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        if (info instanceof ServerCustomer) {
            ServerCustomer serverCustomer = (ServerCustomer) info;
            String desc = serverCustomer.getAutograph();
            if (TextUtils.isEmpty(desc)) {
                mTvServiceDesc.setText("签名暂未添加，请到完善资料里边去完善签名信息！");
            } else {
                mTvServiceDesc.setText(Util.decodeString(desc));
            }
            mTvServiceNickname.setText(serverCustomer.getNickname());

            String photoUrl = serverCustomer.getPhotoUrl();
            Glide.with(context)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE + photoUrl)
                    .apply(options)
                    .into(mIvServicePhoto);

            mBtnOrder.setOnClickListener(listener);
            mBtnOrder.setTag(info);


            List<ServerZan> zans = ((ServerCustomer) info).getZans();
            mLikeView.setList(zans);
            mLikeView.notifyDataSetChanged();

            //默认的点赞状态
            User user = UserManager.getInstance(context).getUser();
            if (user != null) {
                int userType = user.getUserType();
                if (userType == 1) {
                    mBtnOrder.setVisibility(View.VISIBLE);
                } else {
                    mBtnOrder.setVisibility(View.GONE);
                }
                long userId = user.getUserId();
                for (ServerZan serverZan : zans) {
                    if (userId == serverZan.getUserId()) {
                        ((ServerCustomer) info).setIschecked(true);
                        break;
                    }
                }
            }


            mCbServiceZan.setChecked(((ServerCustomer) info).isIschecked());

            mCbServiceZan.setOnClickListener(listener);
            mCbServiceZan.setTag(info);
        }

    }


}
