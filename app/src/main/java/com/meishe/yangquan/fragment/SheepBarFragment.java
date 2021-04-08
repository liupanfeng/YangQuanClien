package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishSheepBarActivity;
import com.meishe.yangquan.activity.SheepBarDetailActivity;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
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
import com.meishe.yangquan.view.CircleImageView;
import com.meishe.yangquan.view.RoundAngleImageView;

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
public class SheepBarFragment extends BaseRecyclerFragment implements View.OnClickListener {

    /*最新*/
    private static final int TYPE_MARKET_LIST_TYPE_TOP = 0;

    private View mIvPublishSheepBar;

    private LinearLayout mLlSignUp;
    /*置顶内容1*/
    private LinearLayout mLlTopMessageContainer1;
    /*置顶内容2*/
    private LinearLayout mLlTopMessageContainer2;

    private TextView mLlTopMessage1;
    private TextView mLlTopMessage2;
    private CircleImageView mIvBarSheepPhoto;
    private TextView mTvSheepBarNickname;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private List<SheepBarMessageInfo> mTopDatas;

    public static SheepBarFragment newInstance(String param1, String param2) {
        SheepBarFragment fragment = new SheepBarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bar_sheep_change, container, false);
        mIvPublishSheepBar = view.findViewById(R.id.iv_common_publish);
        mLlSignUp = view.findViewById(R.id.ll_sign_up);
        mLlTopMessageContainer1 = view.findViewById(R.id.ll_top_message_container_1);
        mLlTopMessageContainer2 = view.findViewById(R.id.ll_top_message_container_2);
        mLlTopMessage1 = view.findViewById(R.id.tv_top_message_1);
        mLlTopMessage2 = view.findViewById(R.id.tv_top_message_2);
        mIvBarSheepPhoto = view.findViewById(R.id.iv_bar_sheep_photo);
        mTvSheepBarNickname = view.findViewById(R.id.tv_sheep_bar_nickname);
        mSlidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        mViewPager = view.findViewById(R.id.vp_pager);

        return view;
    }

    @Override
    protected void initListener() {
        mIvPublishSheepBar.setOnClickListener(this);

        mLlTopMessageContainer1.setOnClickListener(this);

        mLlTopMessageContainer2.setOnClickListener(this);
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

        initTabLayout();

        /**
         * 进来首先获取指定信息
         */
        getSheepBarTopDataFromServer();


    }

    private void initTabLayout() {
        mFragmentList.clear();
        mTitleList.clear();
        SheepBarNewestFragment sheepBarNewestFragment = new SheepBarNewestFragment();
        mFragmentList.add(sheepBarNewestFragment);

        SheepBarRecommendFragment sheepBarRecommendFragment = new SheepBarRecommendFragment();
        mFragmentList.add(sheepBarRecommendFragment);

        mTitleList.add("最新");
        mTitleList.add("推荐");

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getChildFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_publish:
                //发布羊吧
                AppManager.getInstance().jumpActivity(getActivity(), PublishSheepBarActivity.class);
                break;

            case R.id.ll_top_message_container_1:
                if (CommonUtils.isEmpty(mTopDatas)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("sheep_bar_info", mTopDatas.get(0));
                AppManager.getInstance().jumpActivity(getActivity(), SheepBarDetailActivity.class, bundle);
                break;
            case R.id.ll_top_message_container_2:
                if ((!CommonUtils.isEmpty(mTopDatas)) && (mTopDatas.size() > 1)) {
                    bundle = new Bundle();
                    bundle.putSerializable("sheep_bar_info", mTopDatas.get(1));
                    AppManager.getInstance().jumpActivity(getActivity(), SheepBarDetailActivity.class, bundle);
                }
                break;
            default:
                break;
        }
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
        param.put("pageSize", 4);

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
                    mLlSignUp.setVisibility(View.GONE);
                    return;
                }
                if (sheepBarInfoResult.getCode() != 1) {
                    ToastUtil.showToast(sheepBarInfoResult.getMsg());
                    mLlSignUp.setVisibility(View.GONE);
                    return;
                }
                mTopDatas = sheepBarInfoResult.getData();
                if (CommonUtils.isEmpty(mTopDatas)) {
                    ToastUtil.showToast("没有获取到羊吧信息！");
                    mLlSignUp.setVisibility(View.GONE);
                    return;
                }
                mLlSignUp.setVisibility(View.VISIBLE);
                for (int i = 0; i < mTopDatas.size(); i++) {
                    SheepBarMessageInfo sheepBarMessageInfo = mTopDatas.get(i);
                    if (sheepBarMessageInfo == null) {
                        if (i == 0) {
                            return;
                        } else if (i == 1) {
                            mLlTopMessageContainer2.setVisibility(View.GONE);
                        }
                    }
                }

                if (mTopDatas.size() == 1) {
                    mLlTopMessageContainer2.setVisibility(View.GONE);
                    SheepBarMessageInfo sheepBarMessageInfo = mTopDatas.get(0);
                    if (sheepBarMessageInfo != null) {
                        mLlTopMessageContainer1.setVisibility(View.VISIBLE);
                        mLlTopMessage1.setText(sheepBarMessageInfo.getContent());
                    } else {
                        mLlTopMessageContainer1.setVisibility(View.GONE);
                    }
                } else if (mTopDatas.size() >= 2) {
                    SheepBarMessageInfo sheepBarMessageInfo = mTopDatas.get(0);
                    mLlTopMessageContainer1.setVisibility(View.VISIBLE);
                    mLlTopMessage1.setText(sheepBarMessageInfo.getContent());
                    sheepBarMessageInfo = mTopDatas.get(1);
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


}
