package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.HomeMarketPictureInfo;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 主页-市场 图片列表
 * @author 86188
 */
public class HomeMarketPictureListHolder extends BaseViewHolder {


    private RoundAngleImageView mIvSheepBarMessage;


    public HomeMarketPictureListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
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
                GlideUtil.getInstance().loadUrl(((HomeMarketPictureInfo) info).getFilePath(),mIvSheepBarMessage);
                mIvSheepBarMessage.setTag(((HomeMarketPictureInfo) info).getFilePath());
            }
        }

        mIvSheepBarMessage.setTag(info);
        mIvSheepBarMessage.setOnClickListener(listener);

    }


}
