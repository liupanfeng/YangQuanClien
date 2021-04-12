package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServerZan;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.LikesView;
import com.meishe.yangquan.view.RoundAngleImageView;

import java.util.List;

@Deprecated
public class ServiceTypeListHolder extends BaseViewHolder {

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
    public void bindViewHolder(Context context, BaseInfo info,int position, View.OnClickListener listener) {
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
            GlideUtil.getInstance().loadUrl(HttpUrl.URL_IMAGE+photoUrl,mIvServicePhoto);

            mBtnOrder.setOnClickListener(listener);
            mBtnOrder.setTag(info);


            List<ServerZan> zans = ((ServerCustomer) info).getZans();
            mLikeView.setList(zans);
            mLikeView.notifyDataSetChanged();

            //默认的点赞状态
            User user = null;
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
