package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.utils.DateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.view.CircleImageView;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 信息列表holder
 */
public class MessageListHolder extends BaseViewHolder {

    private final RequestOptions options;
    private Button mBtnMessageStartConnect;
    private TextView mTvMessageComment;
    private ImageView mIvMessageComment;
    private RoundAngleImageView mIvMessageTopPart;
    private TextView mTvMessageNickName;
    private TextView mTvMessageDesc;
    private RoundAngleImageView mIvMessage;
    private TextView mTvMessageTime;


    public MessageListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mIvMessageComment=view.findViewById(R.id.iv_message_comment);
        mTvMessageComment=view.findViewById(R.id.tv_message_comment);
        mBtnMessageStartConnect=view.findViewById(R.id.btn_message_start_connect);
        mIvMessageTopPart=view.findViewById(R.id.iv_message_top_part);
        mTvMessageNickName=view.findViewById(R.id.tv_message_nickname);
        mTvMessageDesc=view.findViewById(R.id.tv_message_type_description);
        mIvMessage=view.findViewById(R.id.iv_message);
        mTvMessageTime=view.findViewById(R.id.tv_message_time);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        if (info instanceof Message){
            Message message= (Message) info;
            String photoUrl=message.getPhotoUrl();
            Glide.with(context)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE+photoUrl)
                    .apply(options)
                    .into(mIvMessageTopPart);
            mTvMessageNickName.setText(message.getNickname());
            String desc=message.getContent();
            if (!TextUtils.isEmpty(desc)){
                mTvMessageDesc.setText(desc);
            }else{
                mTvMessageDesc.setText("签名暂未添加，请到完善资料里边去完善签名信息！");
            }
            long createTime=message.getCreateTime();
            if (createTime>0){
                mTvMessageTime.setText(DateUtil.longToString(createTime,"yyyy-MM-dd HH:mm:ss"));
            }


            String contentUrl=message.getContentUrl();
            Glide.with(context)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE+contentUrl)
                    .apply(options)
                    .into(mIvMessage);

            mBtnMessageStartConnect.setOnClickListener(listener);
            mTvMessageComment.setOnClickListener(listener);
            mIvMessageComment.setOnClickListener(listener);
            mBtnMessageStartConnect.setTag(info);
            mTvMessageComment.setTag(info);
            mIvMessageComment.setTag(info);
        }

    }


}
