package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUGoodsInfo;
import com.meishe.yangquan.bean.BUGoodsSubTypeInfo;
import com.meishe.yangquan.bean.BUGoodsTypeInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.view.RoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 商版-添加商品-商品类型的选择
 *
 * @author 86188
 */
public class BUHomeGoodsTypeListHolder extends BaseViewHolder {

    private RoundAngleImageView mIvBuPhoto;
    private TextView tv_bu_goods_name;
    private RecyclerView child_recycler;
    private MultiFunctionAdapter mChildAdapter;
    private View mItem;

    public BUHomeGoodsTypeListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        mItem=itemView;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_bu_goods_name = view.findViewById(R.id.tv_bu_goods_name);
        child_recycler = view.findViewById(R.id.child_recycler);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(child_recycler.getContext(), RecyclerView.VERTICAL, false);
        mChildAdapter = new MultiFunctionAdapter(child_recycler.getContext(), child_recycler);
        child_recycler.setLayoutManager(layoutManager);
        child_recycler.setAdapter(mChildAdapter);

        mChildAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof BUGoodsSubTypeInfo) {
                    mChildAdapter.setSelectPosition(position);
                    BaseInfo info = mChildAdapter.getItem(position);
                    mAdapter.setChildRecyclerViewSelect(info);
                }
            }
        });

    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUGoodsTypeInfo) {
            String name = ((BUGoodsTypeInfo) info).getName();
            tv_bu_goods_name.setText(name);

            int selectPosition = mAdapter.getSelectPosition();
            if (selectPosition==position){
                child_recycler.setVisibility(View.VISIBLE);
                mChildAdapter.setSelectPosition(-1);
                mAdapter.setChildRecyclerViewSelect(null);
            }else{
                child_recycler.setVisibility(View.GONE);
            }

            mItem.setOnClickListener(listener);
            mItem.setTag(info);

            if ("饲料".equals(name)) {
                initFeedData();
            } else if ("玉米".equals(name)) {
                initCornData();
            } else if ("五金电料".equals(name)) {
                initToolsData();
            }
        }
    }

    /**
     * 初始化饲料数据
     */
    private void initFeedData() {
        final List<BUGoodsSubTypeInfo> mData = new ArrayList<>();
        BUGoodsSubTypeInfo buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("发酵料");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("预混料");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("羔羊颗粒");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("益生菌");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("浓缩料");
        mData.add(buGoodsSubTypeInfo);

        mChildAdapter.addAll(mData);

    }


    /**
     * 初始化玉米数据
     */
    private void initCornData() {
        final List<BUGoodsSubTypeInfo> mData = new ArrayList<>();
        BUGoodsSubTypeInfo buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("玉米一");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("玉米二");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("玉米三");
        mData.add(buGoodsSubTypeInfo);

        mChildAdapter.addAll(mData);

    }


    /**
     * 初始化五金电料数据
     */
    private void initToolsData() {
        final List<BUGoodsSubTypeInfo> mData = new ArrayList<>();
        BUGoodsSubTypeInfo buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("五金电料一");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("五金电料二");
        mData.add(buGoodsSubTypeInfo);

        buGoodsSubTypeInfo=new BUGoodsSubTypeInfo();
        buGoodsSubTypeInfo.setName("五金电料三");
        mData.add(buGoodsSubTypeInfo);

        mChildAdapter.addAll(mData);

    }


}
