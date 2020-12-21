package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 饲料-玉米
 * @date 2020/12/21 14:36
 */
public class FeedCornFragment extends BaseRecyclerFragment{

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_feed_corn, container, false);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
