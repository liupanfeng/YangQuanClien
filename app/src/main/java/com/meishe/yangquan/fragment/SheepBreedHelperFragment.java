package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手
 * @date 2020/12/15 14:13
 */
public class SheepBreedHelperFragment extends BaseRecyclerFragment {

    private static final String ARGUMENT_KEY_TITLE = "key_title";
    private String mTitle;
    private TextView mTvTitle;

    public static SheepBreedHelperFragment newInstance(String title) {
        SheepBreedHelperFragment fragment = new SheepBreedHelperFragment();
        Bundle args = new Bundle();
        //使用bundle 进行数据传递
        args.putString(ARGUMENT_KEY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在onCreate方法中获取参数
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARGUMENT_KEY_TITLE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_breed_helper_fragment, container, false);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
