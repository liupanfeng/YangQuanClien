package com.meishe.yangquan.activity;


import android.view.View;

import com.meishe.libbase.adpater.BaseViewHolder;
import com.meishe.yangquan.R;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.ToastUtil;


/**
 * 商版-申请开店页面
 */
public class BUApplyShoppingActivity extends BaseActivity {


    private View btn_bu_to_base_info;
    private View btn_bu_to_real_name;
    private View btn_bu_to_business_license;

    @Override
    protected int initRootView() {
        return R.layout.activity_b_u_apply_shopping;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        btn_bu_to_base_info = findViewById(R.id.btn_bu_to_base_info);
        btn_bu_to_real_name = findViewById(R.id.btn_bu_to_real_name);
        btn_bu_to_business_license = findViewById(R.id.btn_bu_to_business_license);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("申请开店");
    }

    @Override
    public void initListener() {
        btn_bu_to_base_info.setOnClickListener(this);
        btn_bu_to_real_name.setOnClickListener(this);
        btn_bu_to_business_license.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bu_to_base_info:
                AppManager.getInstance().jumpActivity(mContext, BUShoppingBaseInfoActivity.class);
                //基本信息
                break;
            case R.id.btn_bu_to_real_name:
                //实名认证
                boolean baseMessageWriteSuccess = ShoppingInfoManager.getInstance().isBaseMessageWriteSuccess();
                if (baseMessageWriteSuccess){
                    AppManager.getInstance().jumpActivity(mContext, BURealNameAuthorActivity.class);
                }else {
                    ToastUtil.showToast("请先填写基本信息");
                }
                break;
            case R.id.btn_bu_to_business_license:
                //营业执照
                boolean realNameWriteSuccess = ShoppingInfoManager.getInstance().isRealNameWriteSuccess();
                if (realNameWriteSuccess){
                    AppManager.getInstance().jumpActivity(mContext, BUBusinessLicenseActivity.class);
                }else {
                    ToastUtil.showToast("请进行实名认证");
                }
                break;
        }
    }

    @Override
    protected void eventBusUpdateUI(MessageEvent event) {
        super.eventBusUpdateUI(event);
        switch (event.getEventType()){
            case MessageEvent.MESSAGE_TYPE_BU_APPLY_SHOPPING_SUCCESS:
                finish();
                break;
        }
    }
}