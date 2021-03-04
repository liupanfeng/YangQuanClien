package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 羊吧 添加羊吧内容 图片列表
 *
 * @author 86188
 */
public class SheepBarPictureListHolder extends BaseViewHolder {

    private RoundAngleImageView mIvSheepBarMessage;


    public SheepBarPictureListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvSheepBarMessage = view.findViewById(R.id.iv_sheep_bar_message);
    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof CommonPictureInfo) {
            if (((CommonPictureInfo) info).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                String filePath = ((CommonPictureInfo) info).getFilePath();
                Drawable drawable = context.getResources().getDrawable(Integer.valueOf(filePath));
                mIvSheepBarMessage.setImageDrawable(drawable);
            } else if (((CommonPictureInfo) info).getType() == CommonPictureInfo.TYPE_CAPTURE_PIC) {
                Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(((CommonPictureInfo) info).getFilePath(), 1080);
//                Matrix matrix = new Matrix();
//                matrix.setScale(0.4f, 0.4f);
//                Bitmap showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
//                        tmpBitmap.getHeight(), matrix, true);
                mIvSheepBarMessage.setImageBitmap(tmpBitmap);
            } else if (((CommonPictureInfo) info).getType() == CommonPictureInfo.TYPE_URL_PIC) {
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                options.placeholder(R.mipmap.ic_message_list_photo_default);
                Glide.with(context)
                        .asBitmap()
                        .load(((CommonPictureInfo) info).getFilePath())
                        .apply(options)
                        .into(mIvSheepBarMessage);
                mIvSheepBarMessage.setTag(((CommonPictureInfo) info).getFilePath());
            }
        }
        mIvSheepBarMessage.setTag(info);
        mIvSheepBarMessage.setOnClickListener(listener);

    }


}
