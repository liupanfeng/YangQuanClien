package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.libbase.adpater.BaseQuickAdapter;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUPictureInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.bean.MineOrderCommentViewInfo;
import com.meishe.yangquan.bean.MineRefundProgressInfo;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.view.RatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-订单评论
 *
 * @author 86188
 */
public class MineOrderCommentListHolder extends BaseViewHolder {


    private static final int MAX_LENGTH = 100;
    private ImageView riv_goods_cover;
    private TextView tv_goods_title;
    private RatingBar rb_feed_start;
    private EditText mEtInput;
    private TextView tv_number_limit_title;
    private RecyclerView recyclerView;
    private MultiFunctionAdapter mPcitureAdapter;
    private List<BUPictureInfo> mCoverPictureList = new ArrayList<>();

    public MineOrderCommentListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        riv_goods_cover = view.findViewById(R.id.riv_goods_cover);
        tv_goods_title = view.findViewById(R.id.tv_goods_title);
        rb_feed_start = view.findViewById(R.id.rb_feed_start);
        mEtInput = view.findViewById(R.id.et_input);
        tv_number_limit_title = view.findViewById(R.id.tv_number_limit_title);
//        recyclerView = view.findViewById(R.id.recyclerView);
//
//        GridLayoutManager descLayoutManager =
//                new GridLayoutManager(recyclerView.getContext(), 4);
//        mPcitureAdapter = new MultiFunctionAdapter(recyclerView.getContext(), recyclerView);
//        recyclerView.setLayoutManager(descLayoutManager);
//        recyclerView.addItemDecoration(new CustomGridItemDecoration(ScreenUtils.dip2px(recyclerView.getContext(), 3)));
//        recyclerView.setAdapter(mPcitureAdapter);


    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineOrderCommentViewInfo) {
            List<String> coverUrl = ((MineOrderCommentViewInfo) info).getCoverUrl();
            if (CommonUtils.isEmpty(coverUrl)) {
                GlideUtil.getInstance().loadUrl(coverUrl.get(0), riv_goods_cover);
            }
            tv_goods_title.setText(((MineOrderCommentViewInfo) info).getTitle());
            rb_feed_start.setSelectedNumber(0);

//            mCoverPictureList.clear();
//            BUPictureInfo buPictureInfo = new BUPictureInfo();
//            buPictureInfo.setFilePath(String.valueOf(R.mipmap.ic_sheep_bar_add));
//            buPictureInfo.setType(CommonPictureInfo.TYPE_ADD_PIC);
//            mCoverPictureList.add(buPictureInfo);
//            mPcitureAdapter.addAll(mCoverPictureList);
//
//            mPcitureAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position, BaseInfo baseInfo) {
//                    if (baseInfo instanceof BUPictureInfo &&
//                            ((BUPictureInfo) baseInfo).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
//                        if (mCoverPictureList != null && mCoverPictureList.size() > 4) {
//                            ToastUtil.showToast(recyclerView.getContext(), "最多添加" + 4 + "张图片");
//                            return;
//                        }
//
//                        //发送
//                    }
//                }
//            });

            tv_number_limit_title.setText(MAX_LENGTH + "");

            mEtInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int length = s.length();
                    String str = s.toString();
                    if (length > MAX_LENGTH) {
                        mEtInput.setText(str.substring(0, MAX_LENGTH));
                        mEtInput.requestFocus();
                        mEtInput.setSelection(mEtInput.getText().length());
                    } else {
                        int i = MAX_LENGTH - length;
                        tv_number_limit_title.setText(String.valueOf(i));
                    }
                    ((MineOrderCommentViewInfo) info).setDescription(str);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            rb_feed_start.setOnStarChangeListener(new RatingBar.OnStarChangeListener() {
                @Override
                public void OnStarChanged(float selectedNumber, int position) {
                    ((MineOrderCommentViewInfo) info).setScore(selectedNumber);
                }
            });


        }

    }


}
