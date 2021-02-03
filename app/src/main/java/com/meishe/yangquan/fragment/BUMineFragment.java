package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 商版我的页面
 */
public class BUMineFragment extends BaseRecyclerFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view =inflater.inflate(R.layout.fragment_bu_mine,container,false);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
