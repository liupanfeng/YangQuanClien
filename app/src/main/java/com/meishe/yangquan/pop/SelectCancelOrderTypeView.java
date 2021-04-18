package com.meishe.yangquan.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.BottomPopupView;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUGoodsSubTypeInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 15:46
 * @Description : 底部选择取消订单原因View
 */
public class SelectCancelOrderTypeView extends BottomPopupView {

    private OnAttachListener mAttachListener;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private MultiFunctionAdapter mAdapter;
    private String content;

    private String mTitle;


    public SelectCancelOrderTypeView(@NonNull Context context, String title) {
        super(context);
        this.mTitle = title;
        this.mContext = context;
    }

    public static SelectCancelOrderTypeView create(Context context, String title, OnAttachListener attachListener) {
        return (SelectCancelOrderTypeView) new XPopup.Builder(context)
                .asCustom(new SelectCancelOrderTypeView(context, title).
                        setAttachListener(attachListener));
    }

    @Override
    protected int getMaxWidth() {
        return ScreenUtils.getScreenWidth(mContext);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_select_cancel_order_type;
    }


    @Override
    protected void onCreate() {
        super.onCreate();

        TextView titleView = findViewById(R.id.tv_title);
        titleView.setText(mTitle);
        mRecyclerView = findViewById(R.id.recycler);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mRecyclerView.getContext(), RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mRecyclerView.getContext(), mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof BUGoodsSubTypeInfo) {
                    content = ((BUGoodsSubTypeInfo) baseInfo).getName();
                    mAdapter.setSelectPosition(position);
                }
            }
        });


        final List<BUGoodsSubTypeInfo> mData = new ArrayList<>();
        BUGoodsSubTypeInfo buGoodsSubTypeInfo = new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("地址填写错误");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo = new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("商品选错");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo = new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("商品无货");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo = new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("不想要了");
        mData.add(buGoodsSubTypeInfo);


        mAdapter.addAll(mData);
        findViewById(R.id.btn_commit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast("请选择取消订单原因！");
                    return;
                }
                if (mAttachListener != null) {
                    mAttachListener.onSelect(content);
                }
                dismiss();
            }
        });

    }

    public interface OnAttachListener {

        void onSelect(String option);

    }


    public SelectCancelOrderTypeView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }


}
