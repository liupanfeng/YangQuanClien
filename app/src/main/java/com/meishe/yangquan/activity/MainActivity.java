package com.meishe.yangquan.activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.FragmentTabAdapter;
import com.meishe.yangquan.bean.TabInfo;
import com.meishe.yangquan.fragment.BUHomeFragment;
import com.meishe.yangquan.fragment.BUMessageFragment;
import com.meishe.yangquan.fragment.BUMineFragment;
import com.meishe.yangquan.fragment.SheepBarFragment;
import com.meishe.yangquan.fragment.FeedFragment;
import com.meishe.yangquan.fragment.HomeFragment;
import com.meishe.yangquan.fragment.MineFragment;
import com.meishe.yangquan.fragment.SheepHouseKeepFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.PageId;
import com.meishe.yangquan.utils.SharedPreferencesUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.BrandTextView;
import com.meishe.yangquan.wiget.PrivacyPolicyDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BasePermissionActivity {

    private static final String TAG = "MainActivity";
    private String url = "http://192.168.10.55:8080/YangQuan/servlet/ServletDemo03";
    private Context mContext;
    private PrivacyPolicyDialog privacyPolicyDialog;
    /*1 是用户版 2 是商户版 */
    private int mLoginType;

    //改版----突起
    private ArrayList<Fragment> fragments;
    private FragmentTabAdapter tabAdapter;
    private TextView tvTabOne;
    private TextView tvTabTwo;
    private TextView tvTabThree;
    private TextView tvTabFour;
    private TextView tvTabFive;
    private TextView tvTabSix;
    private TextView tvTabSeven;
    private TextView tvTabEight;


    private ImageView ivTabOne;
    private ImageView ivTabTwo;
    private ImageView ivTabThree;
    private ImageView ivTabFour;
    private ImageView ivTabFive;

    private ImageView ivTabSix;
    private ImageView ivTabSeven;
    private ImageView ivTabEight;


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
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mLoginType = extras.getInt(Constants.LOGIN_TYPE);
                SharedPreferencesUtil.getInstance(mContext).putInt(Constants.LOGIN_TYPE, mLoginType);
            } else {
                mLoginType = SharedPreferencesUtil.getInstance(mContext).getInt(Constants.LOGIN_TYPE);
            }
        } else {
            mLoginType = SharedPreferencesUtil.getInstance(mContext).getInt(Constants.LOGIN_TYPE);
        }
//        mLoginType = 2;
        if (hasAllPermission()) {
            //有了权限需要处理的事情
            showPrivacyDialog();
        } else {
            //检测权限
            checkPermissions();
        }

        //////////////////改版//////////////////////


        if (mLoginType==1){

            findViewById(R.id.ll_tab_one).setOnClickListener(this);
            findViewById(R.id.ll_tab_one).setVisibility(View.VISIBLE);

            findViewById(R.id.ll_tab_two).setOnClickListener(this);
            findViewById(R.id.ll_tab_two).setVisibility(View.VISIBLE);

            findViewById(R.id.ll_tab_three).setOnClickListener(this);
            findViewById(R.id.ll_tab_three).setVisibility(View.VISIBLE);

            findViewById(R.id.ll_tab_four).setOnClickListener(this);
            findViewById(R.id.ll_tab_four).setVisibility(View.VISIBLE);

            findViewById(R.id.ll_tab_five).setOnClickListener(this);
            findViewById(R.id.ll_tab_five).setVisibility(View.VISIBLE);


            tvTabOne = findViewById(R.id.tv_tab_one);
            tvTabTwo = findViewById(R.id.tv_tab_two);
            tvTabThree = findViewById(R.id.tv_tab_three);
            tvTabFour = findViewById(R.id.tv_tab_four);
            tvTabFive = findViewById(R.id.tv_tab_five);


            ivTabOne = findViewById(R.id.iv_tab_one);
            ivTabTwo = findViewById(R.id.iv_tab_two);
            ivTabThree = findViewById(R.id.iv_tab_three);
            ivTabFour = findViewById(R.id.iv_tab_four);
            ivTabFive = findViewById(R.id.iv_tab_five);

            tvTabOne.setTextColor(getResources().getColor(R.color.mainColor));
            ivTabOne.setImageResource(R.mipmap.ic_tab_home_checked);

            fragments = new ArrayList<>();
            fragments.add(HomeFragment.newInstance("", ""));
            fragments.add(FeedFragment.newInstance("", ""));
            fragments.add(SheepHouseKeepFragment.newInstance("", ""));
            fragments.add(SheepBarFragment.newInstance("", ""));
            fragments.add( MineFragment.newInstance("", ""));
            tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fl_layout);

        }else if (mLoginType==2){

            findViewById(R.id.ll_tab_six).setOnClickListener(this);
            findViewById(R.id.ll_tab_six).setVisibility(View.VISIBLE);

            findViewById(R.id.ll_tab_seven).setOnClickListener(this);
            findViewById(R.id.ll_tab_seven).setVisibility(View.VISIBLE);

            findViewById(R.id.ll_tab_eight).setOnClickListener(this);
            findViewById(R.id.ll_tab_eight).setVisibility(View.VISIBLE);

            tvTabSix = findViewById(R.id.tv_tab_six);
            tvTabSeven = findViewById(R.id.tv_tab_seven);
            tvTabEight = findViewById(R.id.tv_tab_eight);


            ivTabSix = findViewById(R.id.iv_tab_six);
            ivTabSeven = findViewById(R.id.iv_tab_seven);
            ivTabEight = findViewById(R.id.iv_tab_eight);

            tvTabSix.setTextColor(getResources().getColor(R.color.mainColor));
            ivTabSix.setImageResource(R.mipmap.ic_bu_home_select);

            fragments = new ArrayList<>();
            fragments.add(BUHomeFragment.newInstance());
            fragments.add(BUMessageFragment.newInstance());
            fragments.add(BUMineFragment.newInstance());
            tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fl_layout);
        }
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {



    }


    @Override
    public void initListener() {
        tabAdapter.setOnTabChangeListener(new FragmentTabAdapter.OnTabChangeListener() {
            @Override
            public void OnTabChanged(int index) {
                if (mLoginType==1){
                    performUser(index);
                }else if (mLoginType==2){
                    performBusiness(index);
                }
            }
        });


    }

    /**
     * 处理商户的View
     * @param index
     */
    private void performBusiness(int index) {
        tvTabSix.setTextColor(getResources().getColor(R.color.tab_color_normal));
        tvTabSeven.setTextColor(getResources().getColor(R.color.tab_color_normal));
        tvTabEight.setTextColor(getResources().getColor(R.color.tab_color_normal));

        ivTabSix.setImageResource(R.mipmap.ic_bu_home_unselect);
        ivTabSeven.setImageResource(R.mipmap.ic_bu_message_unselect);
        ivTabEight.setImageResource(R.mipmap.ic_bu_mine_unselect);

        switch (index) {
            case 0:
                tvTabSix.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabSix.setImageResource(R.mipmap.ic_bu_home_select);
                break;
            case 1:
                tvTabSeven.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabSeven.setImageResource(R.mipmap.ic_bu_message_select);
                break;
            case 2:
                tvTabEight.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabEight.setImageResource(R.mipmap.ic_bu_mine_select);
                break;
        }
    }

    /**
     * 处理用户的View
     * @param index
     */
    private void performUser(int index) {

        tvTabOne.setTextColor(getResources().getColor(R.color.tab_color_normal));
        tvTabTwo.setTextColor(getResources().getColor(R.color.tab_color_normal));
        tvTabThree.setTextColor(getResources().getColor(R.color.tab_color_normal));
        tvTabFour.setTextColor(getResources().getColor(R.color.tab_color_normal));
        tvTabFive.setTextColor(getResources().getColor(R.color.tab_color_normal));

        ivTabOne.setImageResource(R.mipmap.ic_tab_home_unchecked);
        ivTabTwo.setImageResource(R.mipmap.ic_tab_feed_unchecked);
        ivTabThree.setImageResource(R.mipmap.ic_tab_keeper_checked);
        ivTabFour.setImageResource(R.mipmap.ic_tab_sheep_bar_unchecked);
        ivTabFive.setImageResource(R.mipmap.ic_tab_mine_unchecked);


        switch (index){
            case 0:
                tvTabOne.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabOne.setImageResource(R.mipmap.ic_tab_home_checked);
                break;
            case 1:
                tvTabTwo.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabTwo.setImageResource(R.mipmap.ic_tab_feed_checked);
                break;
            case 2:
                tvTabThree.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabThree.setImageResource(R.mipmap.ic_tab_keeper_checked);
                break;
            case 3:
                tvTabFour.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabFour.setImageResource(R.mipmap.ic_tab_sheep_bar_checked);
                break;
            case 4:
                tvTabFive.setTextColor(getResources().getColor(R.color.mainColor));
                ivTabFive.setImageResource(R.mipmap.ic_tab_mine_checked);
                break;
        }
    }

    @Override
    public void release() {

    }


    private void setupChildView(TabInfo tabInfo) {
        int tabId = tabInfo.getTabId();
        switch (tabId) {
            case PageId.PAGE_HOME_PAGE:
                HomeFragment serviceFragment = HomeFragment.newInstance("", "");
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
                SheepBarFragment barSheepFragment = SheepBarFragment.newInstance("", "");
                mFragmentList.add(barSheepFragment);
                break;
            case PageId.PAGE_MINE:
                MineFragment mineFragment = MineFragment.newInstance("", "");
                mFragmentList.add(mineFragment);
                break;
            /////////////////////////////////商版////////////////////////////////////////
            case PageId.PAGE_BU_HOME:
                BUHomeFragment buHomeFragment = new BUHomeFragment();
                mFragmentList.add(buHomeFragment);
                break;
            case PageId.PAGE_BU_MESSAGE:
                BUMessageFragment buMessageFragment = new BUMessageFragment();
                mFragmentList.add(buMessageFragment);
                break;
            case PageId.PAGE_BU_MINE:
                BUMineFragment buMineFragment = new BUMineFragment();
                mFragmentList.add(buMineFragment);
                break;


        }
    }


    /**
     * 展示隐私权限
     */
    private void showPrivacyDialog() {
        final SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance(getApplicationContext());
        boolean isAgreePrivacy = sharedPreferencesUtil.getBoolean(Constants.KEY_AGREE_PRIVACY, false);
        if (!isAgreePrivacy) {
            if (privacyPolicyDialog != null && privacyPolicyDialog.isShowing()) {
                return;
            }
            privacyPolicyDialog = new PrivacyPolicyDialog(MainActivity.this, R.style.dialog);
            privacyPolicyDialog.setOnButtonClickListener(new PrivacyPolicyDialog.OnPrivacyClickListener() {
                @Override
                public void onButtonClick(boolean isAgree) {
                    sharedPreferencesUtil.putBoolean(Constants.KEY_AGREE_PRIVACY, isAgree);
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
                Log.d(TAG, "OnRequestBefore");
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure");
            }

            @Override
            protected void onSuccess(Call call, Response response, String user) {
                Log.d(TAG, "response.code()==" + response.code());
                Log.d(TAG, "response.message()==" + response.message());
            }

            @Override
            protected void onResponse(Response response) {
                Log.d(TAG, "onResponse");
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                Log.d(TAG, "onEror");
            }

            @Override
            protected void inProgress(int progress, long total, int id) {
                Log.d(TAG, "inProgress");
            }
        });
    }


    public void request(View view) {

        new Thread() {
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
        switch (v.getId()){
            case R.id.ll_tab_one:
                tabAdapter.setCurrentFragment(0);
                break;
            case R.id.ll_tab_two:
                tabAdapter.setCurrentFragment(1);
                break;
            case R.id.ll_tab_three:
                tabAdapter.setCurrentFragment(2);
                break;
            case R.id.ll_tab_four:
                tabAdapter.setCurrentFragment(3);
                break;
            case R.id.ll_tab_five:
                tabAdapter.setCurrentFragment(4);
                break;
            case R.id.ll_tab_six:
                tabAdapter.setCurrentFragment(0);
                break;
            case R.id.ll_tab_seven:
                tabAdapter.setCurrentFragment(1);
                break;
            case R.id.ll_tab_eight:
                tabAdapter.setCurrentFragment(2);
                break;
        }
    }


//    public void updateApp(){
//        new UpdateAppManager
//                .Builder()
//                //当前Activity
//                .setActivity(this)
//                //更新地址
//                .setUpdateUrl("http://59.110.142.42:9999/app_version/2021-02/01/1612169724199.jpg")
//                .handleException(new ExceptionHandler() {
//                    @Override
//                    public void onException(Exception e) {
//                        e.printStackTrace();
//                    }
//                })
//                //实现httpManager接口的对象
//                .setHttpManager(new UpdateAppHttpUtil())
//                .build()
//                .update();
//    }
//    /**
//     * 自定义对话框
//     *
//     * @param updateApp
//     * @param updateAppManager
//     */
//    private void showDiyDialog(final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
//        String targetSize = updateApp.getTargetSize();
//        String updateLog = updateApp.getUpdateLog();
//
//        String msg = "";
//
//        if (!TextUtils.isEmpty(targetSize)) {
//            msg = "新版本大小：" + targetSize + "\n\n";
//        }
//
//        if (!TextUtils.isEmpty(updateLog)) {
//            msg += updateLog;
//        }
//
//        new AlertDialog.Builder(this)
//                .setTitle(String.format("是否升级到%s版本？", updateApp.getNewVersion()))
//                .setMessage(msg)
//                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //显示下载进度
//                        if (isShowDownloadProgress) {
//                            updateAppManager.download(new DownloadService.DownloadCallback() {
//                                @Override
//                                public void onStart() {
//                                    HProgressDialogUtils.showHorizontalProgressDialog(MainActivity.this, "下载进度", false);
//                                }
//
//                                /**
//                                 * 进度
//                                 *
//                                 * @param progress  进度 0.00 -1.00 ，总大小
//                                 * @param totalSize 总大小 单位B
//                                 */
//                                @Override
//                                public void onProgress(float progress, long totalSize) {
//                                    HProgressDialogUtils.setProgress(Math.round(progress * 100));
//                                }
//
//                                /**
//                                 *
//                                 * @param total 总大小 单位B
//                                 */
//                                @Override
//                                public void setMax(long total) {
//
//                                }
//
//
//                                @Override
//                                public boolean onFinish(File file) {
//                                    HProgressDialogUtils.cancel();
//                                    return true;
//                                }
//
//                                @Override
//                                public void onError(String msg) {
//                                    ToastUtil.showToast(msg);
//                                    HProgressDialogUtils.cancel();
//
//                                }
//
//                                @Override
//                                public boolean onInstallAppAndAppOnForeground(File file) {
//                                    return false;
//                                }
//                            });
//                        } else {
//                            //不显示下载进度
//                            updateAppManager.download();
//                        }
//
//
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create()
//                .show();
//    }


}
