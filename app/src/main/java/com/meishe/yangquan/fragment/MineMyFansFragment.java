package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 我的-我的关注-粉丝
 * @date 2020/12/11 11:30
 */
public class MineMyFansFragment extends BaseRecyclerFragment {


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.mine_my_fans_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView();
        return view;
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
