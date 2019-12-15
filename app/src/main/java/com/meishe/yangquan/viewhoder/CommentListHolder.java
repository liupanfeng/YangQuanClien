package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.utils.DateUtil;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 评论
 */
public class CommentListHolder extends BaseViewHolder {


    private final RequestOptions options;
    private RoundAngleImageView iv_comment_photo;
    private TextView tv_comment_nickname;
    private TextView tv_comment_content;
    private TextView tv_comment_time;

    public CommentListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;

        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        iv_comment_photo=view.findViewById(R.id.iv_comment_photo);
        tv_comment_nickname=view.findViewById(R.id.tv_comment_nickname);
        tv_comment_content=view.findViewById(R.id.tv_comment_content);
        tv_comment_time=view.findViewById(R.id.tv_comment_time);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        if (info instanceof Comment){
            Comment comment= (Comment) info;

            String photoUrl=comment.getPhotoUrl();
            Glide.with(context)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE+photoUrl)
                    .apply(options)
                    .into(iv_comment_photo);

            tv_comment_nickname.setText(comment.getNickName());
            byte[] bytes=Base64.decode(comment.getContent(), Base64.DEFAULT);
            tv_comment_content.setText(new String(bytes));
            tv_comment_time.setText(FormatCurrentData.getTimeRange(comment.getCreateTime()+""));

        }
    }


}
