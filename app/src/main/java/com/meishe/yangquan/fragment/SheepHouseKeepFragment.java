package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 羊管家
 * @date 2020/12/11 15:34
 */
public class SheepHouseKeepFragment extends BaseRecyclerFragment{

    public static SheepHouseKeepFragment newInstance(String param1, String param2) {
        SheepHouseKeepFragment fragment = new SheepHouseKeepFragment();
        Bundle args = new Bundle();
        //使用bundle 进行数据传递
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在onCreate方法中获取参数
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_house_keep_fragment, container, false);
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
