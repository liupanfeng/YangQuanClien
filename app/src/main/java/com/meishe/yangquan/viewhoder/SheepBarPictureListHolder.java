package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.SheepBarPictureInfo;
import com.meishe.yangquan.utils.CropViewUtils;

/**
 * 羊吧 添加羊吧内容 图片列表
 *
 * @author 86188
 */
public class SheepBarPictureListHolder extends BaseViewHolder {

    /*资讯标题*/
    private TextView mTvTitle;
    /*资讯索引*/
    private TextView mTvIndex;

    private ImageView mIvSheepBarMessage;


    public SheepBarPictureListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvSheepBarMessage = view.findViewById(R.id.iv_sheep_bar_message);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepBarPictureInfo) {
            if (((SheepBarPictureInfo) info).getType() == SheepBarPictureInfo.TYPE_ADD_PIC) {
                String filePath = ((SheepBarPictureInfo) info).getFilePath();
                Drawable drawable = context.getResources().getDrawable(Integer.valueOf(filePath));
                mIvSheepBarMessage.setImageDrawable(drawable);
            } else if (((SheepBarPictureInfo) info).getType() == SheepBarPictureInfo.TYPE_CAPTURE_PIC) {
                Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(((SheepBarPictureInfo) info).getFilePath(), 1080);
//                Matrix matrix = new Matrix();
//                matrix.setScale(0.4f, 0.4f);
//                Bitmap showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
//                        tmpBitmap.getHeight(), matrix, true);
                mIvSheepBarMessage.setImageBitmap(tmpBitmap);
            }
        }

        mIvSheepBarMessage.setTag(info);
        mIvSheepBarMessage.setOnClickListener(listener);
    }


}
