package com.meishe.yangquan.viewhoder;

import android.content.Context;
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

/**
 * 羊吧
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
        mAdapter=adapter;

    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mIvSheepBarMessage=view.findViewById(R.id.iv_sheep_bar_message);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info,int position, View.OnClickListener listener) {
        if (info instanceof SheepBarMessageInfo){
            if (((SheepBarMessageInfo) info).getType()==SheepBarMessageInfo.TYPE_ADD_PIC){
                String filePath = ((SheepBarMessageInfo) info).getFilePath();
                Drawable drawable = context.getResources().getDrawable(Integer.valueOf(filePath));
                mIvSheepBarMessage.setImageDrawable(drawable);
            }
        }
    }


}
