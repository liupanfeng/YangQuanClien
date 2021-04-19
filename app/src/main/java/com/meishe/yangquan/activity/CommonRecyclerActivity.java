package com.meishe.yangquan.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meishe.yangquan.R;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;

/**
 * 通用的列表 activity
 * 养殖档案、
 */
public class CommonRecyclerActivity extends BaseActivity {

    private int mType;

    public static void newCommonRecyclerActivity(Context context,int type, int subType){
        Intent intent=new Intent(context,CommonRecyclerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.COMMON_TYPE,type);
        bundle.putInt(Constants.COMMON_SUB_TYPE,subType);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int initRootView() {
        return R.layout.activity_common_recycler;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            int type = extras.getInt(Constants.COMMON_TYPE);
            int subType = extras.getInt(Constants.COMMON_SUB_TYPE);
            mType=type;
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, CommonListFragment.newInstance(false,type,subType));
            fragmentTransaction.commit();
        }
    }

    @Override
    public void initTitle() {
        String title="";
        switch (mType){
            case Constants.TYPE_COMMON_MY_MESSAGE:
                title="我的消息";
                break;
            case Constants.TYPE_COMMON_BREEDING_ARCHIVE_TYPE:
                title="养殖档案";
                break;
        }
        mTvTitle.setText(title);
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //不用这个回导致这个manage不准
                AppManager.getInstance().finishActivity();
            }
        });

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

    }
}
