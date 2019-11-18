package com.meishe.yangquan.activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.fragment.ServiceTypeListFragment;

/**
 * 服务列表界面
 */
public class ServiceTypeListActivity extends BaseActivity {


    private Toolbar mToolbar;
    private String mTitle;

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
        mTitle=bundle.getString("type");
        setToolBarReplaceActionBar();
        ServiceTypeListFragment listFragment=ServiceTypeListFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction().replace(R.id.service_type_container, listFragment).commit();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 设置toolbar
     */
    private void setToolBarReplaceActionBar() {
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    @Override
    public void release() {

    }
}
