package com.meishe.yangquan.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.MessagePublishActivity;
import com.meishe.yangquan.bean.TabInfo;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserType;
import com.meishe.yangquan.view.MViewPager;
import com.meishe.yangquan.wiget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseRecyclerFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List mFragmentList;
    private List mListTitle;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Context mContext;
    private ArrayList<TabInfo> mTabList;
    private int defaultTab;
    private RelativeLayout mRlPublish;

    public MessageFragment() {
    }

    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mRlPublish = view.findViewById(R.id.rl_publish_port);
        mViewPager = (MViewPager) view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    protected void initListener() {
        mRlPublish.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < UserType.getMessageTypeName().size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(UserType.getMessageTypeName().get(i));
            mTabLayout.addTab(tab);
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getContext(), UserType.getUserTypeName().get(tab.getPosition()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        AppManager.getInstance().jumpActivity(getActivity(), MessagePublishActivity.class);
    }
}
