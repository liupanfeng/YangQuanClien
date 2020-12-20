package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.IndustryInfo;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.utils.CropViewUtils;

/**
 * 羊吧
 *
 * @author 86188
 */
public class SheepBarListHolder extends BaseViewHolder {

    /*资讯标题*/
    private TextView mTvTitle;
    /*资讯索引*/
    private TextView mTvIndex;

    private ImageView mIvSheepBarMessage;


    public SheepBarListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvSheepBarMessage = view.findViewById(R.id.iv_sheep_bar_message);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepBarMessageInfo) {
            if (((SheepBarMessageInfo) info).getType() == SheepBarMessageInfo.TYPE_ADD_PIC) {
                String filePath = ((SheepBarMessageInfo) info).getFilePath();
                Drawable drawable = context.getResources().getDrawable(Integer.valueOf(filePath));
                mIvSheepBarMessage.setImageDrawable(drawable);
            } else if (((SheepBarMessageInfo) info).getType() == SheepBarMessageInfo.TYPE_CAPTURE_PIC) {
                Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(((SheepBarMessageInfo) info).getFilePath(), 1080);
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
