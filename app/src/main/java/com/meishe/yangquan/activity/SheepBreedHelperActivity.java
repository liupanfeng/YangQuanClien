package com.meishe.yangquan.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.bean.BatchInfo;
import com.meishe.yangquan.bean.BatchInfoResult;
import com.meishe.yangquan.bean.BatchResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.fragment.SheepBreedHelperFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.view.MViewPager;
import com.meishe.yangquan.wiget.IosDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO;

/**
 * 羊管家-养殖助手
 */
public class SheepBreedHelperActivity extends BaseActivity {


    private MViewPager mViewPage;
    private TabLayout mTabLayout;
    private List<Fragment> mFragments;
    private IosDialog mIosDialog;
    private LinearLayout mLlCreate;

    private List<BatchInfo> mBatchData = new ArrayList<>();


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
        mBatchData.clear();
//        if (SheepBreedHelper.getInstance(mContext).getTitleList() == null || SheepBreedHelper.getInstance(mContext).getTitleList().size() == 0) {
//            showCreateDialog();
//            return;
//        }
//        initTabLayout(SheepBreedHelper.getInstance(mContext).getTitleList());

        getDispatchFromServer();
    }

    /**
     * 获取批次信息
     */
    private void getDispatchFromServer() {
        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_BATCH_LIST, new BaseCallBack<BatchInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCreateDialog();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, BatchInfoResult batchInfoResult) {
                if (batchInfoResult == null) {
                    showCreateDialog();
                    return;
                }

                if (batchInfoResult.getCode() != 1) {
                    showCreateDialog();
                    return;
                }

                List<BatchInfo> data = batchInfoResult.getData();
                if (data == null) {
                    showCreateDialog();
                    return;
                }
                mBatchData.clear();
                mBatchData.addAll(data);
                if (mBatchData.size() == 0) {
                    showCreateDialog();
                    return;
                }

                initTabLayout(mBatchData);

            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, null, token);
    }

    private void initTabLayout(List<BatchInfo> batchData) {
        mFragments = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < batchData.size(); i++) {
            BatchInfo batchInfo = batchData.get(i);
            if (batchInfo == null) {
                continue;
            }

            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(batchInfo.getTitle());
            titleList.add(batchInfo.getTitle());

            mTabLayout.addTab(tab);
            SheepBreedHelperFragment sheepBreedHelperFragment = SheepBreedHelperFragment.newInstance(batchInfo.getId(),
                    batchInfo.getCurrentCulturalQuantity(),batchInfo.getInitDate());
            mFragments.add(sheepBreedHelperFragment);
        }
        mViewPage.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), 0, mContext, mFragments, titleList));
        mTabLayout.setupWithViewPager(mViewPage);
    }

    private void showCreateDialog() {
        final IosDialog.DialogBuilder builder = new IosDialog.DialogBuilder(mContext);
        builder.setTitle("创建养殖档案");
        builder.setAsureText("确定");
        builder.setCancelText("取消");
        builder.setInputContent("请输入档案的名称");
        builder.addListener(new IosDialog.OnButtonClickListener() {
            @Override
            public void onAsureClick() {
                String title = mIosDialog.getmEtInputContent().getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    ToastUtil.showToast(mContext, "请输入档案名称");
                    return;
                }
                if (title.equals("请输入档案的名称")) {
                    ToastUtil.showToast(mContext, "请更改自己档案名称");
                    return;
                }

                if (title.length() > 4) {
                    ToastUtil.showToast(mContext, "档案名称最多四个字");
                    return;
                }

                if (title.length() < 2) {
                    ToastUtil.showToast(mContext, "档案名称最少2个字");
                    return;
                }
                mIosDialog.hide();
                createDispatchFromServer(title);

//                SheepBreedHelper.getInstance(mContext).setTitleList(title);
//                initTabLayout(SheepBreedHelper.getInstance(mContext).getTitleList());

            }

            @Override
            public void onCancelClick() {
                mIosDialog.hide();
                if (mBatchData.size() == 0) {
                    SheepBreedHelperActivity.this.finish();
                }
            }
        });
        mIosDialog = builder.create();
    }


    /**
     * 创建档案
     */
    public void createDispatchFromServer(final String title) {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("title", title);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_CREATE_BATCH, new BaseCallBack<BatchResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, BatchResult batchResult) {
                if (batchResult != null) {
                    int code = batchResult.getCode();
                    if (code != 1) {
                        ToastUtil.showToast(mContext, response.message());
                    }
                    BatchInfo batchInfo = new BatchInfo();
                    batchInfo.setTitle(title);
                    batchInfo.setId(batchResult.getData());
                    mBatchData.add(batchInfo);
                    initTabLayout(mBatchData);
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("养殖助手");
    }

    @Override
    public void initListener() {
        mLlCreate.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_create:
                showCreateDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void eventBusUpdateUI(MessageEvent event) {
        super.eventBusUpdateUI(event);
        int eventType = event.getEventType();
        switch (eventType) {
            case MessageEvent.MESSAGE_TYPE_UPDATE_BREEDING_ARCHIVING:
                getDispatchFromServer();
                break;
        }

    }
}
