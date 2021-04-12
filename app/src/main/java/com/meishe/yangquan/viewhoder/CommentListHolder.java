package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 评论
 */
@Deprecated
public class CommentListHolder extends BaseViewHolder {


    private RoundAngleImageView iv_comment_photo;
    private TextView tv_comment_nickname;
    private TextView tv_comment_content;
    private TextView tv_comment_time;

    public CommentListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        iv_comment_photo=view.findViewById(R.id.iv_comment_photo);
        tv_comment_nickname=view.findViewById(R.id.tv_comment_nickname);
        tv_comment_content=view.findViewById(R.id.tv_comment_content);
        tv_comment_time=view.findViewById(R.id.tv_comment_time);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        if (info instanceof Comment){
            Comment comment= (Comment) info;

            String photoUrl=comment.getPhotoUrl();
            GlideUtil.getInstance().loadUrl(HttpUrl.URL_IMAGE+photoUrl,iv_comment_photo);
            tv_comment_nickname.setText(comment.getNickName());
            byte[] bytes=Base64.decode(comment.getContent(), Base64.DEFAULT);
            tv_comment_content.setText(new String(bytes));
            tv_comment_time.setText(FormatCurrentData.getTimeRange(comment.getCreateTime()+""));

        }
    }


}
