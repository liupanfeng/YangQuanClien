package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.SheepBarCommentInfo;
import com.meishe.yangquan.bean.SheepBarCommentSecondaryInfo;
import com.meishe.yangquan.bean.SheepBarCommentSecondaryInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 羊吧 二级评论列表
 *
 * @author 86188
 */
public class SheepBarCommentSecondaryListHolder extends BaseViewHolder {

    private final RequestOptions options;
    private View mItemView;


    /*头像*/
    private ImageView mIvSheepBarPhoto;

    /*昵称*/
    private TextView tv_sheep_bar_nickname;
    /*更新时间*/
    private TextView tv_sheep_bar_time;

    /*羊吧内容*/
    private TextView tv_sheep_bar_content;
    /*点赞*/
    private TextView tv_sheep_bar_prised;
    private RecyclerView child_recycler;
    private MultiFunctionAdapter mChildAdapter;


    public SheepBarCommentSecondaryListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        mItemView = itemView;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvSheepBarPhoto = view.findViewById(R.id.iv_sheep_bar_photo);
        tv_sheep_bar_nickname = view.findViewById(R.id.tv_sheep_bar_nickname);
        tv_sheep_bar_time = view.findViewById(R.id.tv_sheep_bar_time);
        tv_sheep_bar_prised = view.findViewById(R.id.tv_sheep_bar_prised);
        tv_sheep_bar_content = view.findViewById(R.id.tv_sheep_bar_content);

    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepBarCommentSecondaryInfo) {

            String iconUrl = ((SheepBarCommentSecondaryInfo) info).getIconUrl();
            if (iconUrl != null) {
                Glide.with(context)
                        .asBitmap()
                        .load(iconUrl)
                        .apply(options)
                        .into(mIvSheepBarPhoto);

            }

            tv_sheep_bar_nickname.setText("@"+((SheepBarCommentSecondaryInfo) info).getNickname());
            tv_sheep_bar_time.setText(FormatCurrentData.getTimeRange(((SheepBarCommentSecondaryInfo) info).getInitDate()));
            tv_sheep_bar_content.setText(((SheepBarCommentSecondaryInfo) info).getContent());
            tv_sheep_bar_prised.setCompoundDrawablesWithIntrinsicBounds(((SheepBarCommentSecondaryInfo) info).isHasPraised() ? context.getResources().getDrawable(R.mipmap.ic_heart_selected):
                    context.getResources().getDrawable(R.mipmap.ic_heart_unselected),null,null,null);
            tv_sheep_bar_prised.setText(((SheepBarCommentSecondaryInfo) info).getPraiseAmount()+"");

            tv_sheep_bar_prised.setTag(info);
            tv_sheep_bar_prised.setOnClickListener(listener);

            tv_sheep_bar_content.setTag(info);
            tv_sheep_bar_content.setOnClickListener(listener);

        }

    }


}
