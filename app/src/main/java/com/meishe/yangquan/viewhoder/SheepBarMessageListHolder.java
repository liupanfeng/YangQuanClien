package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceInfo;
import com.meishe.yangquan.bean.SheepBarCommentInfo;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.bean.SheepBarPictureInfo;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.FormatDateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 羊吧 内容列表
 *
 * @author 86188
 */
public class SheepBarMessageListHolder extends BaseViewHolder {

    private final RequestOptions options;
    private View mItemView;

    /*资讯标题*/
    private TextView mTvTitle;
    /*资讯索引*/
    private TextView mTvIndex;

    /*yang*/
    private ImageView mIvSheepBarPhoto;

    /*昵称*/
    private TextView tv_sheep_bar_nickname;
    /*更新时间*/
    private TextView tv_sheep_bar_time;
    /*关注按钮*/
    private LinearLayout ll_sheep_bar_focus;
    /*羊吧内容*/
    private TextView tv_sheep_bar_content;
    /*y羊吧图片*/
    private RecyclerView recycler;
    /*评论数量*/
    private TextView tv_sheep_bar_comment_number;
    /*点赞数量*/
    private TextView tv_sheep_bar_prise_number;

    private MultiFunctionAdapter mGrideAdapter;

    public SheepBarMessageListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
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
        ll_sheep_bar_focus = view.findViewById(R.id.ll_sheep_bar_focus);
        tv_sheep_bar_content = view.findViewById(R.id.tv_sheep_bar_content);
        recycler = view.findViewById(R.id.recycler);
        tv_sheep_bar_comment_number = view.findViewById(R.id.tv_sheep_bar_comment_number);
        tv_sheep_bar_prise_number = view.findViewById(R.id.tv_sheep_bar_prise_number);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(recycler.getContext(), 3);
        mGrideAdapter = new MultiFunctionAdapter(recycler.getContext(), recycler);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(15);
        recycler.addItemDecoration(customGridItemDecoration);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(mGrideAdapter);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepBarMessageInfo) {
//            if (((SheepBarPictureInfo) info).getType() == SheepBarPictureInfo.TYPE_ADD_PIC) {
//                String filePath = ((SheepBarPictureInfo) info).getFilePath();
//                Drawable drawable = context.getResources().getDrawable(Integer.valueOf(filePath));
//                mIvSheepBarMessage.setImageDrawable(drawable);
//            } else if (((SheepBarPictureInfo) info).getType() == SheepBarPictureInfo.TYPE_CAPTURE_PIC) {
//                Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(((SheepBarPictureInfo) info).getFilePath(), 1080);
////                Matrix matrix = new Matrix();
////                matrix.setScale(0.4f, 0.4f);
////                Bitmap showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
////                        tmpBitmap.getHeight(), matrix, true);
//                mIvSheepBarMessage.setImageBitmap(tmpBitmap);
//            }

            String iconUrl = ((SheepBarMessageInfo) info).getIconUrl();
            if (iconUrl != null) {
                Glide.with(context)
                        .asBitmap()
                        .load(iconUrl)
                        .apply(options)
                        .into(mIvSheepBarPhoto);

            }


            tv_sheep_bar_nickname.setText("@" + ((SheepBarMessageInfo) info).getNickname());
            tv_sheep_bar_time.setText(FormatCurrentData.getTimeRange(((SheepBarMessageInfo) info).getInitDate()));
            tv_sheep_bar_content.setText(((SheepBarMessageInfo) info).getContent());
            tv_sheep_bar_comment_number.setText(((SheepBarMessageInfo) info).getCommentAmount() + "");
            tv_sheep_bar_prise_number.setText(((SheepBarMessageInfo) info).getPraiseAmount() + "");

            tv_sheep_bar_prise_number.setCompoundDrawablesWithIntrinsicBounds(((SheepBarMessageInfo) info).isHasPraised() ?
                    context.getResources().getDrawable(R.mipmap.ic_sheep_bar_select_prise) :
                    context.getResources().getDrawable(R.mipmap.ic_sheep_bar_prise), null, null, null);

            List<String> images = ((SheepBarMessageInfo) info).getImages();
            List<SheepBarPictureInfo> list = new ArrayList<>();
            if (!CommonUtils.isEmpty(images)) {
                for (int i = 0; i < images.size(); i++) {
                    SheepBarPictureInfo sheepBarMessageInfo = new SheepBarPictureInfo();
                    sheepBarMessageInfo.setFilePath(images.get(i));
                    sheepBarMessageInfo.setType(SheepBarPictureInfo.TYPE_URL_PIC);
                    list.add(sheepBarMessageInfo);
                }
                mGrideAdapter.addAll(list);
            }else{
                mGrideAdapter.addAll(null);
            }

            ll_sheep_bar_focus.setTag(info);
            ll_sheep_bar_focus.setOnClickListener(listener);

            tv_sheep_bar_prise_number.setTag(info);
            tv_sheep_bar_prise_number.setOnClickListener(listener);


            mItemView.setTag(info);
            mItemView.setOnClickListener(listener);
        }

    }
}
