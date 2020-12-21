package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BarSheepPublishActivity;
import com.meishe.yangquan.activity.SheepBarDetailActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.wiget.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 羊吧页面
 */
public class BarSheepFragment extends BaseRecyclerFragment implements View.OnClickListener {

    /*最新*/
    private static final int TYPE_MARKET_LIST_TYPE_NEWEST = 1;
    /*推荐*/
    private static final int TYPE_MARKET_LIST_TYPE_RECOMMEND = 2;

    /*最新 推荐*/
    private CustomTextView mTvMarketNewest;
    private CustomTextView mTvMarketCommand;

    private ImageView mIvPublishSheepBar;

    private int mListType = TYPE_MARKET_LIST_TYPE_NEWEST;

    private List<SheepBarMessageInfo> mData = new ArrayList<>();


    public static BarSheepFragment newInstance(String param1, String param2) {
        BarSheepFragment fragment = new BarSheepFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bar_sheep, container, false);
        mTvMarketNewest = view.findViewById(R.id.tv_bar_sheep_newest);
        mTvMarketCommand = view.findViewById(R.id.tv_bar_sheep_command);
        mIvPublishSheepBar = view.findViewById(R.id.iv_publish_sheep_bar);
        mRecyclerView = view.findViewById(R.id.recycler);
        return view;
    }

    @Override
    protected void initListener() {
        mTvMarketNewest.setOnClickListener(this);
        mTvMarketCommand.setOnClickListener(this);
        mIvPublishSheepBar.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof SheepBarMessageInfo){
                    AppManager.getInstance().jumpActivity(getActivity(), SheepBarDetailActivity.class);
                }
            }
        });
    }

    @Override
    protected void initData() {
        selectNewest();
        initRecyclerView();
        SheepBarMessageInfo sheepBarMessageInfo = new SheepBarMessageInfo();
        mData.add(sheepBarMessageInfo);
        SheepBarMessageInfo sheepBarMessageInfo1 = new SheepBarMessageInfo();
        mData.add(sheepBarMessageInfo1);
        SheepBarMessageInfo sheepBarMessageInfo2 = new SheepBarMessageInfo();
        mData.add(sheepBarMessageInfo2);
        mAdapter.addAll(mData);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bar_sheep_newest:
                selectNewest();
                break;
            case R.id.tv_bar_sheep_command:
                selectCommand();
                break;
            case R.id.iv_publish_sheep_bar:
                //发布羊吧
                AppManager.getInstance().jumpActivity(getActivity(), BarSheepPublishActivity.class);
                break;
            default:
                break;
        }
    }

    private void selectCommand() {
        mListType = TYPE_MARKET_LIST_TYPE_RECOMMEND;
        mTvMarketCommand.setSelected(true);
        mTvMarketNewest.setSelected(false);
        getSheepBarDataFromServer(mListType);
    }

    private void selectNewest() {
        mListType = TYPE_MARKET_LIST_TYPE_NEWEST;
        mTvMarketNewest.setSelected(true);
        mTvMarketCommand.setSelected(false);
        getSheepBarDataFromServer(mListType);
    }

    /**
     * 获取羊吧数据
     *
     * @param listType 列表类型
     */
    private void getSheepBarDataFromServer(int listType) {

    }


}
