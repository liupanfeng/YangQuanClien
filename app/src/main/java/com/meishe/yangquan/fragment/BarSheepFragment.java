package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishSheepBarActivity;
import com.meishe.yangquan.activity.SheepBarDetailActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.SheepBarInfoResult;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.RoundAngleImageView;
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
    private static final int TYPE_MARKET_LIST_TYPE_TOP = 0;
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
    private LinearLayout mLlSignUp;
    /*置顶内容1*/
    private LinearLayout mLlTopMessageContainer1;
    /*置顶内容2*/
    private LinearLayout mLlTopMessageContainer2;

    private TextView mLlTopMessage1;
    private TextView mLlTopMessage2;
    private RoundAngleImageView mIvBarSheepPhoto;
    private TextView mTvSheepBarNickname;


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
        mLlSignUp = view.findViewById(R.id.ll_sign_up);
        mLlTopMessageContainer1 = view.findViewById(R.id.ll_top_message_container_1);
        mLlTopMessageContainer2 = view.findViewById(R.id.ll_top_message_container_2);
        mLlTopMessage1 = view.findViewById(R.id.tv_top_message_1);
        mLlTopMessage2 = view.findViewById(R.id.tv_top_message_2);
        mIvBarSheepPhoto = view.findViewById(R.id.iv_bar_sheep_photo);
        mTvSheepBarNickname = view.findViewById(R.id.tv_sheep_bar_nickname);
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
                int id = view.getId();
                switch (id) {
                    case R.id.ll_sheep_bar_focus:
                        //关注
                        ToastUtil.showToast("关注");

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


    }

    @Override
    protected void initData() {
        //获取用户数据
        UserInfo user = UserManager.getInstance(mContext).getUser();
        if (user != null) {
            //更新头像
            String iconUrl = user.getIconUrl();
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.mipmap.ic_message_list_photo_default);
            Glide.with(mContext)
                    .asBitmap()
                    .load(iconUrl)
                    .apply(options)
                    .into(mIvBarSheepPhoto);

            //更新昵称
            mTvSheepBarNickname.setText(user.getNickname());
        }

        selectNewest();
        initRecyclerView();

        /**
         * 进来首先获取指定信息
         */
        getSheepBarTopDataFromServer();
        getSheepBarDataFromServer(mListType);

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
     * @param listType 列表类型  1最新  2 推荐
     */
    private void getSheepBarDataFromServer(int listType) {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("listType", listType);
        param.put("content", "");
        param.put("pageNum", 1);
        param.put("pageSize", 30);

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
                List<SheepBarMessageInfo> datas = sheepBarInfoResult.getData();
                if (CommonUtils.isEmpty(datas)) {
                    return;
                }
                mAdapter.addAll(datas);
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
     * 获取置顶信息
     */
    private void getSheepBarTopDataFromServer() {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("listType", TYPE_MARKET_LIST_TYPE_TOP);
        param.put("content", "");
        param.put("pageNum", 1);
        param.put("pageSize", 10);

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
                    mLlSignUp.setVisibility(View.INVISIBLE);
                    return;
                }
                if (sheepBarInfoResult.getCode() != 1) {
                    ToastUtil.showToast(sheepBarInfoResult.getMsg());
                    mLlSignUp.setVisibility(View.INVISIBLE);
                    return;
                }
                List<SheepBarMessageInfo> datas = sheepBarInfoResult.getData();
                if (CommonUtils.isEmpty(datas)) {
                    ToastUtil.showToast("没有获取到羊吧信息！");
                    mLlSignUp.setVisibility(View.INVISIBLE);
                    return;
                }
                mLlSignUp.setVisibility(View.VISIBLE);
                for (int i = 0; i < datas.size(); i++) {
                    SheepBarMessageInfo sheepBarMessageInfo = datas.get(i);
                    if (sheepBarMessageInfo == null) {
                        if (i == 0) {
                            return;
                        } else if (i == 1) {
                            mLlTopMessageContainer2.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                if (datas.size() == 1) {
                    mLlTopMessageContainer2.setVisibility(View.INVISIBLE);
                    SheepBarMessageInfo sheepBarMessageInfo = datas.get(0);
                    if (sheepBarMessageInfo != null) {
                        mLlTopMessageContainer1.setVisibility(View.VISIBLE);
                        mLlTopMessage1.setText(sheepBarMessageInfo.getContent());
                    } else {
                        mLlTopMessageContainer1.setVisibility(View.INVISIBLE);
                    }
                } else if (datas.size() >= 2) {
                    SheepBarMessageInfo sheepBarMessageInfo = datas.get(0);
                    mLlTopMessageContainer1.setVisibility(View.VISIBLE);
                    mLlTopMessage1.setText(sheepBarMessageInfo.getContent());
                    sheepBarMessageInfo = datas.get(1);
                    mLlTopMessageContainer2.setVisibility(View.VISIBLE);
                    mLlTopMessage2.setText(sheepBarMessageInfo.getContent());
                }
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
     * @param id           对应的id
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
        HashMap<String, Object> param = new HashMap<>();
        param.put("id", sheepBarMessageInfo.getId());
        param.put("interactType", interactType);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_INTERACT, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
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

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);


    }


}
