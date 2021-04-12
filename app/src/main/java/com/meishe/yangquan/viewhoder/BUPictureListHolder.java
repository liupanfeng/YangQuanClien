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
import com.meishe.yangquan.bean.BUPictureInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 商版-图片列表
 *
 * @author 86188
 */
public class BUPictureListHolder extends BaseViewHolder {

    private RoundAngleImageView mIvBuPicture;


    public BUPictureListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvBuPicture = view.findViewById(R.id.iv_bu_picture);
    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUPictureInfo) {
            if (((BUPictureInfo) info).getType() == BUPictureInfo.TYPE_ADD_PIC) {
                String filePath = ((BUPictureInfo) info).getFilePath();
                Drawable drawable = context.getResources().getDrawable(Integer.valueOf(filePath));
                mIvBuPicture.setImageDrawable(drawable);
            } else if (((BUPictureInfo) info).getType() == BUPictureInfo.TYPE_CAPTURE_PIC) {
                Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(((BUPictureInfo) info).getFilePath(), 1080);
//                Matrix matrix = new Matrix();
//                matrix.setScale(0.4f, 0.4f);
//                Bitmap showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
//                        tmpBitmap.getHeight(), matrix, true);
                mIvBuPicture.setImageBitmap(tmpBitmap);
            } else if (((BUPictureInfo) info).getType() == CommonPictureInfo.TYPE_URL_PIC) {
                GlideUtil.getInstance().loadUrl(((BUPictureInfo) info).getFilePath(),mIvBuPicture);
                mIvBuPicture.setTag(((BUPictureInfo) info).getFilePath());
            }
        }
        mIvBuPicture.setTag(info);
        mIvBuPicture.setOnClickListener(listener);

    }


}
