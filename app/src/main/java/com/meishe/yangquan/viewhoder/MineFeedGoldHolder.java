package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 我的-饲料金列表
 *
 * @author 86188
 */
public class MineFeedGoldHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*养殖档案标题*/
    private TextView tv_title;
    /*养殖档案建立时间*/
    private TextView tv_time;

    private View mItemView;

    public MineFeedGoldHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mItemView=itemView;
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_title = view.findViewById(R.id.tv_title);
        tv_time = view.findViewById(R.id.tv_time);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineBreedingArchivesInfo) {
            tv_title.setText(((MineBreedingArchivesInfo) info).getTitle());
            tv_time.setText(FormatDateUtil.longToString(((MineBreedingArchivesInfo) info).getInitDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
            mItemView.setOnClickListener(listener);
            mItemView.setTag(info);
        }

    }


}
