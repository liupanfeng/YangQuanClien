package com.meishe.yangquan.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.activity.PublishServiceActivity;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.bean.TabInfo;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.UserType;
import com.meishe.yangquan.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseRecyclerFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List mFragmentList;
    private List mListTitle;
    private MViewPager mViewPager;
    private TabLayout mTabLayout;
    private Context mContext;
    private ArrayList<TabInfo> mTabList;
    private int defaultTab;
    private RelativeLayout mRlPublish;
    private int mCurrentPosition=0;

    private List<Fragment> list;


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

        list=new ArrayList<>();
        //出售羊
        MessageListFragmentList shushouyang= MessageListFragmentList.newInstance(1);
        //购买羊
        MessageListFragmentList goumaiyang= MessageListFragmentList.newInstance(2);
        //出售饲料
        MessageListFragmentList shushousiliao= MessageListFragmentList.newInstance(3);
        //出售兽药
        MessageListFragmentList chushoushouyao= MessageListFragmentList.newInstance(4);
        //羊五金
        MessageListFragmentList yangwujin= MessageListFragmentList.newInstance(5);
        //羊粪队伍
        MessageListFragmentList yangfendui= MessageListFragmentList.newInstance(6);
        //羊车队
        MessageListFragmentList yangchedui= MessageListFragmentList.newInstance(7);
        //剪羊毛
        MessageListFragmentList jianyangmao= MessageListFragmentList.newInstance(8);
        //疫苗
        MessageListFragmentList yimiao= MessageListFragmentList.newInstance(9);

        list.add(shushouyang);
        list.add(goumaiyang);
        list.add(shushousiliao);
        list.add(chushoushouyao);
        list.add(yangwujin);
        list.add(yangfendui);
        list.add(yangchedui);
        list.add(jianyangmao);
        list.add(yimiao);


        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),0,mContext, list,UserType.getMessageTypeName()));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setScroll(true);
        mViewPager.setCurrentItem(0);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mCurrentPosition=tab.getPosition();
                if (list!=null&&list.size()>mCurrentPosition){
                    MessageListFragmentList fragment = (MessageListFragmentList) list.get(mCurrentPosition);
                    fragment.clearData();

                }
                mViewPager.setCurrentItem(tab.getPosition());
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
        if (!UserManager.getInstance(mContext).isNeedLogin()){
            Bundle bundle=new Bundle();
            bundle.putInt("currentPosition",mCurrentPosition);
            AppManager.getInstance().jumpActivity(getActivity(), PublishServiceActivity.class,bundle);
        }else{
            AppManager.getInstance().jumpActivity(getActivity(), LoginActivity.class);
        }
    }
}
