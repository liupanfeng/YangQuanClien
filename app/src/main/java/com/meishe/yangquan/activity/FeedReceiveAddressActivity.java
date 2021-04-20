package com.meishe.yangquan.activity;


import android.content.Intent;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedReceiverAddressInfo;
import com.meishe.yangquan.pop.EditReceiveAddressView;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户版-收货地址-页面
 * 由于设计图的原因这个页面暂时先不要
 */
@Deprecated
public class FeedReceiveAddressActivity extends BaseActivity {


    private View btn_add_receive_address;
    private List<BaseInfo> mData = new ArrayList<>();

    @Override
    protected int initRootView() {
        return R.layout.activity_receive_address;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerView = findViewById(R.id.recycler);
        btn_add_receive_address = findViewById(R.id.btn_add_receive_address);
        initRecyclerView();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("收货地址");
    }

    @Override
    public void initListener() {
        btn_add_receive_address.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position, BaseInfo baseInfo) {
                if (view.getId() == R.id.iv_feed_address_edit && baseInfo instanceof FeedReceiverAddressInfo) {
//                    EditReceiveAddressView editReceiveAddressView = EditReceiveAddressView.create(mContext,
//                            (FeedReceiverAddressInfo) baseInfo, new EditReceiveAddressView.OnReceiveAddressListener() {
//                        @Override
//                        public void onReceiveAddress(int type, BaseInfo baseInfo) {
//                            mAdapter.notifyItemChanged(position);
//                        }
//                    });
//                    if (!editReceiveAddressView.isShow()) {
//                        editReceiveAddressView.show();
//                    }
                    return;
                }

                if (baseInfo instanceof FeedReceiverAddressInfo) {
                    Intent i = new Intent();
                    i.putExtra("result", baseInfo);
                    setResult(3, i);
                    finish();
                }
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_receive_address) {
            EditReceiveAddressView editReceiveAddressView = EditReceiveAddressView.create(mContext, null, new EditReceiveAddressView.OnReceiveAddressListener() {
                @Override
                public void onReceiveAddress(int type, BaseInfo baseInfo) {
                    mData.add(0, baseInfo);
                    mAdapter.addAll(mData);
                }

                @Override
                public void onClickArea() {

                }
            });
            if (!editReceiveAddressView.isShow()) {
                editReceiveAddressView.show();
            }
        }
    }


}