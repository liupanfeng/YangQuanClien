package com.meishe.yangquan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.fragment.SheepBreedHelperFragment;
import com.meishe.yangquan.utils.SheepBreedHelper;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.view.MViewPager;
import com.meishe.yangquan.wiget.IosDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 羊管家-养殖助手
 */
public class SheepBreedHelperActivity extends BaseActivity {


    private MViewPager mViewPage;
    private TabLayout mTabLayout;
    private List<Fragment> mFragments;
    private IosDialog mIosDialog;
    private LinearLayout mLlCreate;


    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_breed_helper;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mLlCreate = findViewById(R.id.ll_create);
        mViewPage = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    @Override
    public void initData() {
        if (SheepBreedHelper.getInstance(mContext).getTitleList()==null||SheepBreedHelper.getInstance(mContext).getTitleList().size()==0){
            showCreateDialog();
            return;
        }
        initTabLayout(SheepBreedHelper.getInstance(mContext).getTitleList());
    }

    private void initTabLayout(List<String> titleList) {
        mFragments =new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i);
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(title);
            mTabLayout.addTab(tab);
            SheepBreedHelperFragment sheepBreedHelperFragment=SheepBreedHelperFragment.newInstance(title);
            mFragments.add(sheepBreedHelperFragment);
        }
        mViewPage.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),0,mContext,mFragments,titleList));
        mTabLayout.setupWithViewPager(mViewPage);
    }

    private void showCreateDialog() {
        final IosDialog.DialogBuilder builder=new IosDialog.DialogBuilder(mContext);
        builder.setTitle("创建养殖档案");
        builder.setAsureText("确定");
        builder.setCancelText("取消");
        builder.setInputContent("请输入档案的名称");
        builder.addListener(new IosDialog.OnButtonClickListener() {
            @Override
            public void onAsureClick() {
                String title = mIosDialog.getmEtInputContent().getText().toString().trim();
                if (TextUtils.isEmpty(title)){
                    ToastUtil.showToast(mContext,"请输入档案名称");
                    return;
                }
                if (title.equals("请输入档案的名称")){
                    ToastUtil.showToast(mContext,"请更改自己档案名称");
                    return;
                }

                if (title.length()>4){
                    ToastUtil.showToast(mContext,"档案名称最多四个字");
                    return;
                }

                if (title.length()<2){
                    ToastUtil.showToast(mContext,"档案名称最少2个字");
                    return;
                }
                SheepBreedHelper.getInstance(mContext).setTitleList(title);
                initTabLayout(SheepBreedHelper.getInstance(mContext).getTitleList());
                mIosDialog.hide();
            }

            @Override
            public void onCancelClick() {
                mIosDialog.hide();
                if (SheepBreedHelper.getInstance(mContext).getTitleList().size()==0){
                    SheepBreedHelperActivity.this.finish();
                }
            }
        });
         mIosDialog = builder.create();
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("养殖助手");
    }

    @Override
    public void initListener() {
        mLlCreate.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_create:
                showCreateDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess(int type, Object object) {

    }

    @Override
    public void onError(Object obj) {

    }

    @Override
    public void onError(int type, Object obj) {

    }
}
