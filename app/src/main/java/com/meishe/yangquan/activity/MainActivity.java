package com.meishe.yangquan.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.bean.TabInfo;
import com.meishe.yangquan.fragment.MessageFragment;
import com.meishe.yangquan.fragment.MineFragment;
import com.meishe.yangquan.fragment.ServiceFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.PageId;
import com.meishe.yangquan.view.BrandTextView;
import com.meishe.yangquan.view.MViewPager;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private String url="http://192.168.10.55:8080/YangQuan/servlet/ServletDemo03";
    private List mFragmentList;
    private List mListTitle;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Context mContext;
    private ArrayList<TabInfo> mTabList;
    private int defaultTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        defaultTab=0;
        initView();
        initData();
    }

    private void initData() {
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

    private void initView() {
        mTabLayout =  findViewById(R.id.tab_layout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(R.color.white, R.color.mainColor);
        mViewPager = (MViewPager) findViewById(R.id.viewpager);

    }

    private void initTabList() {
        mTabList.add(new TabInfo(PageId.PAGE_SERVICE,
                App.getInstance().getString(R.string.tab_service),
                R.drawable.bg_tab_service));
        mTabList.add(new TabInfo(PageId.PAGE_MESSAGE,
                App.getInstance().getString(R.string.tab_message),
                R.drawable.bg_tab_message));
        mTabList.add(new TabInfo(PageId.PAGE_MINE,
                App.getInstance().getString(R.string.tab_mine),
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
            case PageId.PAGE_SERVICE:
                ServiceFragment serviceFragment =  ServiceFragment.newInstance("", "");
                mFragmentList.add(serviceFragment);
                break;
            case PageId.PAGE_MESSAGE:
                MessageFragment messageFragment = MessageFragment.newInstance("", "");
                mFragmentList.add(messageFragment);
                break;
            case PageId.PAGE_MINE:
                MineFragment mineFragment=MineFragment.newInstance("","");
                mFragmentList.add(mineFragment);
                break;

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
}
