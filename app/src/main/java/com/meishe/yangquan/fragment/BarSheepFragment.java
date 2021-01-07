package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishSheepBarActivity;
import com.meishe.yangquan.activity.SheepBarDetailActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.SheepBarInfoResult;
import com.meishe.yangquan.bean.SheepBarInfoWarp;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.wiget.CustomTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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

        getSheepBarDataFromServer(mListType);

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
                AppManager.getInstance().jumpActivity(getActivity(), PublishSheepBarActivity.class);
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
        String token = getToken();
        if (Util.checkNull(token)){
            return;
        }
        HashMap<String,Object> param=new HashMap<>();
        param.put("listType",listType);
        param.put("content","test");
        param.put("pageNum",1);
        param.put("pageSize",30);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_INFO_LIST, new BaseCallBack<SheepBarInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, SheepBarInfoResult sheepBarInfoResult) {
                if (sheepBarInfoResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (sheepBarInfoResult.getCode() != 1) {
                    ToastUtil.showToast(sheepBarInfoResult.getMsg());
                    return;
                }
                SheepBarInfoWarp data = sheepBarInfoResult.getData();
                if (data == null) {
                    ToastUtil.showToast("SheepBarInfoWarp is null");
                    return;
                }
                ToastUtil.showToast(data.getCount()+"");
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        },param,token);


    }


}
