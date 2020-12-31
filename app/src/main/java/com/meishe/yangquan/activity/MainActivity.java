package com.meishe.yangquan.activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.bean.TabInfo;
import com.meishe.yangquan.fragment.BarSheepFragment;
import com.meishe.yangquan.fragment.FeedFragment;
import com.meishe.yangquan.fragment.HomeFragment;
import com.meishe.yangquan.fragment.MineFragment;
import com.meishe.yangquan.fragment.SheepHouseKeepFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.PageId;
import com.meishe.yangquan.utils.SpUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.BrandTextView;
import com.meishe.yangquan.view.MViewPager;
import com.meishe.yangquan.wiget.PrivacyPolicyDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BasePermissionActivity {

    private static final String TAG="MainActivity";
    private String url="http://192.168.10.55:8080/YangQuan/servlet/ServletDemo03";
    private List mFragmentList;
    private List mListTitle;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Context mContext;
    private ArrayList<TabInfo> mTabList;
    private int defaultTab;
    private PrivacyPolicyDialog privacyPolicyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        defaultTab=0;
        initView();
        initData();
    }

    @Override
    protected int initRootView() {
        return R.layout.activity_main;
    }

    @Override
    protected List<String> initPermissions() {
        return Util.getAllPermissionsList();
    }

    @Override
    protected void hasPermission() {
        showPrivacyDialog();
    }

    @Override
    protected void nonePermission() {

    }

    /**
     * 用户选择了不再提示
     */
    @Override
    protected void noPromptPermission() {
        startAppSettings();
    }


    // 启动应用的设置
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void initData() {
        if (hasAllPermission()) {
            //有了权限需要处理的事情
            showPrivacyDialog();
        } else {
            //检测权限
            checkPermissions();
        }
        mFragmentList = new ArrayList<>();
        mListTitle = new ArrayList<>();
        mTabList = new ArrayList<>();
        initTabList();
        for (TabInfo tabInfo:mTabList){
            setupChildView(tabInfo);
        }
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),0,mContext, mFragmentList, mListTitle));
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabWithIcons(mTabList);
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void release() {

    }

    @Override
    public void initView() {
        mTabLayout =  findViewById(R.id.tab_layout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(R.color.white, R.color.mainColor);
        mViewPager = (MViewPager) findViewById(R.id.viewpager);

    }

    private void initTabList() {
        mTabList.add(new TabInfo(PageId.PAGE_HOME_PAGE,
                App.getInstance().getString(R.string.tab_home_page),
                R.drawable.bg_tab_home_page));

//        mTabList.add(new TabInfo(PageId.PAGE_FEED_PAGE,
//                App.getInstance().getString(R.string.tab_feed_page),
//                R.drawable.bg_tab_feed_page));

        mTabList.add(new TabInfo(PageId.PAGE_SHEEP_HOUSE_KEEP,
                App.getInstance().getString(R.string.tab_sheep_house_keeper_page),
                R.drawable.bg_tab_sheep_house_keeper));

        mTabList.add(new TabInfo(PageId.PAGE_SHEEP_BAR_PAGE,
                App.getInstance().getString(R.string.tab_sheep_bar_page),
                R.drawable.bg_tab_sheep_bar));

        mTabList.add(new TabInfo(PageId.PAGE_MINE,
                App.getInstance().getString(R.string.tab_mine_page),
                R.drawable.bg_tab_mine));
    }


    private void setupTabWithIcons(List<TabInfo> tabList) {
        if (tabList == null || tabList.size() == 0) return;
        for (int i = 0; i < tabList.size(); i++) {
            TabInfo tabInfo = tabList.get(i);
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tabInfo == null || tab == null) continue;
            tab.setCustomView(createTabView(tabInfo));
        }
        View view = mTabLayout.getTabAt(defaultTab).getCustomView();
        view.setSelected(true);
        view.requestLayout();
    }

    private View createTabView(TabInfo tabInfo) {
        View view = getLayoutInflater().inflate(R.layout.layout_item_tab, null);
        ImageView iv_icon =  view.findViewById(R.id.resview_icon);
        BrandTextView tv_name =  view.findViewById(R.id.tv_name);
        iv_icon.setImageResource(tabInfo.getIcon());
        tv_name.setText(tabInfo.getTitle());
        return view;
    }


    private void setupChildView(TabInfo tabInfo) {
        int tabId = tabInfo.getTabId();
        switch (tabId) {
            case PageId.PAGE_HOME_PAGE:
                HomeFragment serviceFragment =  HomeFragment.newInstance("", "");
                mFragmentList.add(serviceFragment);
                break;
            case PageId.PAGE_FEED_PAGE:
                FeedFragment feedFragment = FeedFragment.newInstance("", "");
                mFragmentList.add(feedFragment);
                break;
            case PageId.PAGE_SHEEP_HOUSE_KEEP:
                SheepHouseKeepFragment sheepHouseKeepFragment = SheepHouseKeepFragment.newInstance("", "");
                mFragmentList.add(sheepHouseKeepFragment);
                break;
            case PageId.PAGE_SHEEP_BAR_PAGE:
                BarSheepFragment barSheepFragment = BarSheepFragment.newInstance("", "");
                mFragmentList.add(barSheepFragment);
                break;
            case PageId.PAGE_MINE:
                MineFragment mineFragment=MineFragment.newInstance("","");
                mFragmentList.add(mineFragment);
                break;

        }
    }


    /**
     * 展示隐私权限
     */
    private void showPrivacyDialog() {
        final SpUtil spUtil = SpUtil.getInstance(getApplicationContext());
        boolean isAgreePrivacy = spUtil.getBoolean(Constants.KEY_AGREE_PRIVACY, false);
        if (!isAgreePrivacy) {
            if (privacyPolicyDialog!=null&&privacyPolicyDialog.isShowing()){
                return;
            }
            privacyPolicyDialog = new PrivacyPolicyDialog(MainActivity.this, R.style.dialog);
            privacyPolicyDialog.setOnButtonClickListener(new PrivacyPolicyDialog.OnPrivacyClickListener() {
                @Override
                public void onButtonClick(boolean isAgree) {
                    spUtil.putBoolean(Constants.KEY_AGREE_PRIVACY, isAgree);
                    if (!isAgree) {
                        AppManager.getInstance().finishActivity();
                    }
                }

                @Override
                public void pageJumpToWeb(String clickTextContent) {
                    String serviceAgreement = getString(R.string.service_agreement);
                    String privacyPolicy = getString(R.string.privacy_policy);
                    String visitUrl = "";
                    if (clickTextContent.contains(serviceAgreement)) {
                        visitUrl = Constants.USER_AGREEMENTS;
                    } else if (clickTextContent.contains(privacyPolicy)) {
                        visitUrl = Constants.PRIVACY_POLICY_URL;
                    }
                    if (TextUtils.isEmpty(visitUrl)) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("URL", visitUrl);
                    AppManager.getInstance().jumpActivity(MainActivity.this, MainWebViewActivity.class, bundle);
                }
            });
            privacyPolicyDialog.show();
        }
    }



    private void getDataAsync() {

        OkHttpManager.getInstance().getRequest(url, new BaseCallBack<String>() {
            @Override
            protected void OnRequestBefore(Request request) {
                Log.d(TAG,"OnRequestBefore");
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                Log.d(TAG,"onFailure");
            }

            @Override
            protected void onSuccess(Call call, Response response, String user) {
                Log.d(TAG,"response.code()=="+response.code());
                Log.d(TAG,"response.message()=="+response.message());
            }

            @Override
            protected void onResponse(Response response) {
                Log.d(TAG,"onResponse");
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                Log.d(TAG,"onEror");
            }

            @Override
            protected void inProgress(int progress, long total, int id) {
                Log.d(TAG,"inProgress");
            }
        });
    }


    public void request(View view) {

       new Thread(){
           @Override
           public void run() {
               super.run();
               getDataAsync();
           }
       }.start();
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

    @Override
    public void onClick(View v) {

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
