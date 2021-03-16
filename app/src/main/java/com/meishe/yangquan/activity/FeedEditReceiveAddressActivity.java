package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.FeedReceiverAddressInfo;

import java.io.Serializable;

/**
 * 编辑或者增加收货地址
 */
public class FeedEditReceiveAddressActivity extends BaseActivity {

    private EditText et_feed_receive_name;
    /*收货人电话*/
    private EditText et_feed_input_receive_phone_number;
    /*收货人所在区域*/
    private EditText et_feed_input_receive_area;
    /*详细地址*/
    private EditText et_feed_input_detail_address;
    /*操作类型 1=添加  2修改*/
    private int mType;
    private View btn_save_receive_address;

    @Override
    protected int initRootView() {
        return R.layout.activity_edit_receive_address;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        et_feed_receive_name = findViewById(R.id.et_feed_receive_name);
        et_feed_input_receive_phone_number = findViewById(R.id.et_feed_input_receive_phone_number);
        et_feed_input_receive_area = findViewById(R.id.et_feed_input_receive_area);
        et_feed_input_detail_address = findViewById(R.id.et_feed_input_detail_address);
        btn_save_receive_address = findViewById(R.id.btn_save_receive_address);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mType = extras.getInt("type");
                Serializable baseInfo = extras.getSerializable("baseInfo");
                if (baseInfo instanceof FeedReceiverAddressInfo) {
                    String receiverName = ((FeedReceiverAddressInfo) baseInfo).getReceiverName();
                    et_feed_receive_name.setText(receiverName);
                    et_feed_input_receive_phone_number.setText(((FeedReceiverAddressInfo) baseInfo).getReceiverPhone());
                    et_feed_input_receive_area.setText(((FeedReceiverAddressInfo) baseInfo).getArea());
                    et_feed_input_detail_address.setText(((FeedReceiverAddressInfo) baseInfo).getDetailAddress());
                }
            }
        }
    }

    @Override
    public void initTitle() {
        if (mType == 1) {
            mTvTitle.setText("新建收货地址");
        } else if (mType == 2) {
            mTvTitle.setText("编辑收货地址");
        }
    }

    @Override
    public void initListener() {
        btn_save_receive_address.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save_receive_address) {

        }
    }
}