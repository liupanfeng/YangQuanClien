package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUShopDataInfo;
import com.meishe.yangquan.divider.CustomGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 商版工作台主页面
 */
public class BUHomeFragment extends BaseRecyclerFragment {

    private TextView mTvTips;
    /*申请开店*/
    private Button mBtnApplyOpenShop;
    /*未开店容器*/
    private View mViewNoShop;
    /*已开店容器*/
    private View mViewOpenShop;

    /*店铺数据*/
    private RecyclerView mShopDataRecycler;
    /*羊管家*/
    private MultiFunctionAdapter mGridAdapter;

    private String[] mShopData = {"出售中", "待付", "待发货", "待评价", "退货中",
            "今日总访客", "今日订单数量", "今日成交额", "收藏我的", "累计订单数量", "累计成交额"};



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_home, container, false);
        mViewNoShop = view.findViewById(R.id.view_no_shop);
        mViewOpenShop = view.findViewById(R.id.view_open_shop);

        mViewNoShop.setVisibility(View.GONE);
        mViewOpenShop.setVisibility(View.VISIBLE);

        mRecyclerView = view.findViewById(R.id.recycler);
        mShopDataRecycler = view.findViewById(R.id.bu_shop_data_recycler);
        mTvTips = view.findViewById(R.id.tv_bu_tips);
        mBtnApplyOpenShop = view.findViewById(R.id.btn_bu_apply_open_shop);


        initShopDataRecycler();

        return view;
    }

    /**
     * 初始化 店铺数据recycler
     */
    private void initShopDataRecycler() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(mContext, 3);
        mGridAdapter = new MultiFunctionAdapter(mContext, mShopDataRecycler);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(5);
        mShopDataRecycler.addItemDecoration(customGridItemDecoration);
        mShopDataRecycler.setLayoutManager(gridLayoutManager);
        mShopDataRecycler.setAdapter(mGridAdapter);

    }

    @Override
    protected void initData() {
        initShopData();
    }

    /**
     * 初始化店铺数据
     */
    private void initShopData() {
        List<BUShopDataInfo> buShopDataInfoArrayList = new ArrayList<>();

        for (int i = 0; i < mShopData.length; i++) {
            String shopData = mShopData[i];
            BUShopDataInfo buShopDataInfo = new BUShopDataInfo();
            buShopDataInfo.setName(shopData);
            buShopDataInfo.setAmount(i);
            buShopDataInfoArrayList.add(buShopDataInfo);
        }
        mGridAdapter.addAll(buShopDataInfoArrayList);

    }

    @Override
    protected void initListener() {

    }


}
