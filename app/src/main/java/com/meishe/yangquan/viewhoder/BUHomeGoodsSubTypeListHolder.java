package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUGoodsSubTypeInfo;
import com.meishe.yangquan.bean.BUGoodsTypeInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 商版-添加商品-商品类型的选择
 *
 * @author 86188
 */
public class BUHomeGoodsSubTypeListHolder extends BaseViewHolder {

    private RoundAngleImageView mIvBuPhoto;
    private TextView tv_bu_goods_name;
    private ImageView iv_bu_goods_select;

    private View mItemView;

    public BUHomeGoodsSubTypeListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        mItemView = itemView;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_bu_goods_name = view.findViewById(R.id.tv_bu_goods_sub_name);
        iv_bu_goods_select = view.findViewById(R.id.iv_bu_goods_select);

    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUGoodsSubTypeInfo) {
            String name = ((BUGoodsSubTypeInfo) info).getName();
            tv_bu_goods_name.setText(name);
            iv_bu_goods_select.setOnClickListener(listener);
            iv_bu_goods_select.setTag(info);
            int selectPosition = mAdapter.getSelectPosition();
            if (position == selectPosition) {
                iv_bu_goods_select.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
            } else {
                iv_bu_goods_select.setBackgroundResource(R.mipmap.ic_bu_home_circle);
            }

            mItemView.setOnClickListener(listener);
            mItemView.setTag(info);
        }
    }
}
