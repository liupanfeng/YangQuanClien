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
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.HomeMarketPictureInfo;
import com.meishe.yangquan.bean.SheepBarPictureInfo;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 主页-市场 图片列表
 *
 * @author 86188
 */
public class HomemarketPictureListHolder extends BaseViewHolder {


    private RoundAngleImageView mIvSheepBarMessage;


    public HomemarketPictureListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvSheepBarMessage = view.findViewById(R.id.iv_sheep_bar_message);
    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof HomeMarketPictureInfo) {
            if (((HomeMarketPictureInfo) info).getType() == HomeMarketPictureInfo.TYPE_ADD_PIC) {
                String filePath = ((HomeMarketPictureInfo) info).getFilePath();
                Drawable drawable = context.getResources().getDrawable(Integer.valueOf(filePath));
                mIvSheepBarMessage.setImageDrawable(drawable);
            } else if (((HomeMarketPictureInfo) info).getType() == HomeMarketPictureInfo.TYPE_CAPTURE_PIC) {
                Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(((HomeMarketPictureInfo) info).getFilePath(), 1080);
//                Matrix matrix = new Matrix();
//                matrix.setScale(0.4f, 0.4f);
//                Bitmap showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
//                        tmpBitmap.getHeight(), matrix, true);
                mIvSheepBarMessage.setImageBitmap(tmpBitmap);
            } else if (((HomeMarketPictureInfo) info).getType() == HomeMarketPictureInfo.TYPE_URL_PIC) {
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                options.placeholder(R.mipmap.ic_message_list_photo_default);
                Glide.with(context)
                        .asBitmap()
                        .load(((HomeMarketPictureInfo) info).getFilePath())
                        .apply(options)
                        .into(mIvSheepBarMessage);
                mIvSheepBarMessage.setTag(((HomeMarketPictureInfo) info).getFilePath());
            }
        }
        mIvSheepBarMessage.setTag(info);
        mIvSheepBarMessage.setOnClickListener(listener);

    }


}
