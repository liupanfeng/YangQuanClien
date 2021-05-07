package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedCommentInfo;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.GlideUtil;

import java.text.DateFormat;

/**
 * 商品详情-订单评论
 *
 * @author 86188
 */
public class FeedCommentListHolder extends BaseViewHolder {

    private ImageView iv_feed_comment_photo;
    private TextView tv_feed_comment_nickname;
    private TextView tv_feed_goods_time;
    private TextView tv_feed_goods_comment_content;

    public FeedCommentListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {

        iv_feed_comment_photo = view.findViewById(R.id.iv_feed_comment_photo);
        tv_feed_comment_nickname = view.findViewById(R.id.tv_feed_comment_nickname);
        tv_feed_goods_time = view.findViewById(R.id.tv_feed_goods_time);
        /*评论内容*/
        tv_feed_goods_comment_content = view.findViewById(R.id.tv_feed_goods_comment_content);


    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof FeedCommentInfo.FeedCommentElementInfo) {
            GlideUtil.getInstance().loadUrl(((FeedCommentInfo.FeedCommentElementInfo) info).getIconUrl(),iv_feed_comment_photo);
            tv_feed_comment_nickname.setText(((FeedCommentInfo.FeedCommentElementInfo) info).getNickname());
            tv_feed_goods_time.setText(FormatDateUtil.longToString(((FeedCommentInfo.FeedCommentElementInfo) info).getInitDate(),FormatDateUtil.FORMAT_TYPE));
            tv_feed_goods_comment_content.setText(((FeedCommentInfo.FeedCommentElementInfo) info).getDescription());
        }

    }


}
