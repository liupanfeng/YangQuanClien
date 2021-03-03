package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUPictureInfo;
import com.meishe.yangquan.bean.BUShoppingUserInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.SheepBarPictureInfo;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 商版-主页-已经申请店铺列表
 *
 * @author 86188
 */
public class BUHomeAlreadyApplyShoppingListHolder extends BaseViewHolder {

    private RoundAngleImageView mIvBuPhoto;
    private TextView tv_bu_nickname;


    public BUHomeAlreadyApplyShoppingListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvBuPhoto = view.findViewById(R.id.riv_bu_photo);
        tv_bu_nickname = view.findViewById(R.id.tv_bu_nickname);
    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUShoppingUserInfo) {
                tv_bu_nickname.setText(((BUShoppingUserInfo) info).getNickname());
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                options.placeholder(R.mipmap.ic_message_list_photo_default);
                Glide.with(context)
                        .asBitmap()
                        .load(((BUShoppingUserInfo) info).getIconUrl())
                        .apply(options)
                        .into(mIvBuPhoto);
            }
//        mIvBuPhoto.setTag(info);
//        mIvBuPhoto.setOnClickListener(listener);

    }


}
