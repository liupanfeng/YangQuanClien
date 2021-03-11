package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.SheepBarDetailActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.SheepBarInfoResult;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_SHEEP_BAR;
import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO;
import static com.meishe.yangquan.utils.Constants.TYPE_LIST_TYPE_NEWEST;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/18
 * @Description : 羊吧最新页面
 */
public class SheepBarNewestFragment extends BaseRecyclerFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_sheep_bar_newest, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                int id = view.getId();
                switch (id) {
                    case R.id.ll_sheep_bar_focus:
                        //关注
                        if (baseInfo instanceof SheepBarMessageInfo) {
                            focusUserServer(position, (SheepBarMessageInfo) baseInfo);
                        }
                        return;
                    case R.id.tv_sheep_bar_prise_number:
                        //点赞
                        if (baseInfo instanceof SheepBarMessageInfo) {
                            postInteractSheepBar(position, (SheepBarMessageInfo) baseInfo, 1);
                        }
                        return;
                }
                if (baseInfo instanceof SheepBarMessageInfo) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("sheep_bar_info", baseInfo);
                    AppManager.getInstance().jumpActivity(getActivity(), SheepBarDetailActivity.class, bundle);
                }
            }
        });


        /*下拉刷新*/
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                mIsLoadFinish = true;
                mPageNum = 1;
                getSheepBarDataFromServer();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsLoadFinish = true;
                getSheepBarDataFromServer();
            }
        });

    }

    @Override
    protected void initData() {
        mList.clear();
        initRecyclerView();
        mPageNum = 1;
        getSheepBarDataFromServer();
    }


    /**
     * 关注用户
     */
    private void focusUserServer(final int position, final SheepBarMessageInfo sheepBarMessageInfo) {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("focusUserId", sheepBarMessageInfo.getInitUser());
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FOCUS, new BaseCallBack<SheepBarInfoResult>() {
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

                if (sheepBarMessageInfo.isHasFocused()) {
                    sheepBarMessageInfo.setHasFocused(false);
                } else {
                    sheepBarMessageInfo.setHasFocused(true);
                }
                mAdapter.notifyItemChanged(position);

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
        }, param, token);

    }


    /**
     * 获取羊吧数据
     */
    private void getSheepBarDataFromServer() {
        if (!mIsLoadFinish) {
            return;
        }
        mIsLoadFinish = false;

        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("listType", TYPE_LIST_TYPE_NEWEST);
        param.put("content", "");
        param.put("pageNum", mPageNum);
        param.put("pageSize", mPageSize);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_INFO_LIST, new BaseCallBack<SheepBarInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, SheepBarInfoResult sheepBarInfoResult) {
                hideUIState();
                if (sheepBarInfoResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (sheepBarInfoResult.getCode() != 1) {
                    ToastUtil.showToast(sheepBarInfoResult.getMsg());
                    return;
                }
                List<SheepBarMessageInfo> datas = sheepBarInfoResult.getData();
                if (CommonUtils.isEmpty(datas)) {
                    return;
                }

                /*页码增加*/
                mPageNum++;
                mList.addAll(mList.size(), datas);
                mAdapter.addAll(mList);
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
        }, param, token);
    }


    /**
     * 互动接口 点赞帖子，点赞评论，收藏帖子，分享帖子
     *
     * @param interactType 1 给帖子点赞
     *                     2 评论点赞
     *                     3 收藏帖子
     *                     4 分享贴子
     */
    private void postInteractSheepBar(final int position, final SheepBarMessageInfo sheepBarMessageInfo,
                                      final int interactType) {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        showLoading();
        HashMap<String, Object> param = new HashMap<>();
        param.put("id", sheepBarMessageInfo.getId());
        param.put("interactType", interactType);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_INTERACT, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                hideLoading();
                if (serverResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (serverResult.getCode() != 1) {
                    ToastUtil.showToast(serverResult.getMsg());
                    return;
                }
                switch (interactType) {
                    case 1:
                        if (sheepBarMessageInfo.isHasPraised()) {
                            sheepBarMessageInfo.setHasPraised(false);
                            int priseAmount = sheepBarMessageInfo.getPraiseAmount();
                            sheepBarMessageInfo.setPraiseAmount(priseAmount - 1);
                        } else {
                            sheepBarMessageInfo.setHasPraised(true);
                            int priseAmount = sheepBarMessageInfo.getPraiseAmount();
                            sheepBarMessageInfo.setPraiseAmount(priseAmount + 1);
                        }
                        mAdapter.notifyItemChanged(position);
                        break;
                    case 2:
                        ToastUtil.showToast("评论点赞！");
                        break;
                    case 3:
                        ToastUtil.showToast("收藏帖子！");
                        break;
                    case 4:
                        ToastUtil.showToast("分享贴子！");
                        break;
                    default:
                        break;
                }

            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this); //解除注册
    }


    /**
     * On message event.
     * 消息事件
     *
     * @param event the event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int eventType = event.getEventType();
        switch (eventType){
            case MESSAGE_TYPE_UPDATE_SHEEP_BAR:
                initData();
                break;
        }
    }

}
