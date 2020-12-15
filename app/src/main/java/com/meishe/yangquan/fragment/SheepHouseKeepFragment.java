package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.SheepBreedHelperActivity;
import com.meishe.yangquan.activity.SheepBreedServerActivity;
import com.meishe.yangquan.utils.AppManager;

/**
 * @author liupanfeng
 * @desc 羊管家
 * @date 2020/12/11 15:34
 */
public class SheepHouseKeepFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvBack;

    private LinearLayout mLlBreedHelper;
    private LinearLayout mLlBreedServer;

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
        mTvTitle = view.findViewById(R.id.tv_title);
        mIvBack = view.findViewById(R.id.iv_back);
        mLlBreedHelper = view.findViewById(R.id.ll_breed_helper);
        mLlBreedServer = view.findViewById(R.id.ll_breed_server);
        initTitle();
        return view;
    }

    private void initTitle() {
        mTvTitle.setText("羊管家");
        mIvBack.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        mLlBreedHelper.setOnClickListener(this);
        mLlBreedServer.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_breed_helper:
                AppManager.getInstance().jumpActivity(getActivity(), SheepBreedHelperActivity.class);
                break;
            case R.id.ll_breed_server:
                AppManager.getInstance().jumpActivity(getActivity(), SheepBreedServerActivity.class);
                break;
            default:
                break;
        }
    }
}
