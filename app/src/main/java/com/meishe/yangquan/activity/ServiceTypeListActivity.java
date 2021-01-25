package com.meishe.yangquan.activity;
import android.os.Bundle;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.fragment.ServiceTypeListFragment;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.umeng.analytics.MobclickAgent;

/**
 * 服务列表界面
 */
public class ServiceTypeListActivity extends BaseActivity {


    private String mTitle;
    private int mUserType;

    @Override
    protected int initRootView() {
        return R.layout.activity_service_type_list;
    }

    @Override
    public void initView() {
         mToolbar =findViewById(R.id.toolbar);
    }

    @Override
    public void initData() {
        Bundle bundle=getIntent().getExtras();
        mTitle=bundle.getString("name");
        mUserType=bundle.getInt("type");

        ServiceTypeListFragment listFragment=ServiceTypeListFragment.newInstance(mUserType);
        getSupportFragmentManager().beginTransaction().replace(R.id.service_type_container, listFragment).commit();
    }

    @Override
    public void initTitle() {
        mToolbar.setMyTitle(mTitle);
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setLeftButtonVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
    }

    @Override
    public void initListener() {

    }




    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

    }


    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {
        @Override
        public void onClick() {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
