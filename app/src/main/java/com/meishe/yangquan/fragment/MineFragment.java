package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.MsgEvent;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.meishe.yangquan.utils.Constants.MESSAGE_EVENT_UPDATE_USER_UI;


public class MineFragment extends BaseRecyclerFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;

    private String[] mSettingInfo = {"个人信息", "饲料金", "我的关注", "我的消息", "建议留言",
            "我的积分","养殖档案", "我的收藏",
            "系统消息", "支付密码"};
    private int[] mSettingIcon = {R.mipmap.ic_mine_personal_message,
            R.mipmap.ic_mine_feed_gold, R.mipmap.ic_mine_my_focus,
            R.mipmap.ic_mine_my_message,
            R.mipmap.ic_mine_suggest,
            R.mipmap.ic_mine_my_points,
            R.mipmap.ic_mine_keep,
            R.mipmap.ic_mine_my_collection,
            R.mipmap.ic_mine_system_message,
            R.mipmap.ic_mine_pay_password};
    private LinearLayout mLLNoLogin;
    private LinearLayout mLLLogin;
    private TextView mTvNumber;
    private ImageView mIvMinePhoto;
    private TextView mTvNickname;
    /*待支付*/
    private RelativeLayout mRlMinePay;
    /*待收货*/
    private RelativeLayout mRlMineReceive;
    /*待评论*/
    private RelativeLayout mRlMineCommont;
    /*待退款*/
    private RelativeLayout mRlMineRefund;

    public MineFragment() {
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        mRecyclerView = view.findViewById(R.id.mine_recycler);
        mRlMinePay = view.findViewById(R.id.rl_mine_pay);
        mRlMineReceive = view.findViewById(R.id.rl_mine_receive);
        mRlMineCommont = view.findViewById(R.id.rl_mine_comment);
        mRlMineRefund = view.findViewById(R.id.rl_mine_refund);
        mTvNickname = view.findViewById(R.id.tv_nickname);
        return view;
    }


    @Override
    protected void initListener() {
        mRlMinePay.setOnClickListener(this);
        mRlMineReceive.setOnClickListener(this);
        mRlMineCommont.setOnClickListener(this);
        mRlMineRefund.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(manager);
        MultiFunctionAdapter adapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        adapter.setFragment(this);
        mList.clear();
        for (int i = 0; i < mSettingInfo.length; i++) {
            MineTypeInfo info = new MineTypeInfo();
            info.setName(mSettingInfo[i]);
            info.setIcon(mSettingIcon[i]);
            mList.add(info);
        }
        adapter.addAll(mList);

//        updateUserUI();

    }

    private void updateUserUI() {
        if (UserManager.getInstance(getContext()).isNeedLogin()) {
            mLLNoLogin.setVisibility(View.VISIBLE);
            mLLLogin.setVisibility(View.GONE);
        } else {
            mLLLogin.setVisibility(View.VISIBLE);
            mLLNoLogin.setVisibility(View.GONE);
        }

        User user = UserManager.getInstance(getContext()).getUser();
        if (user != null) {
            mTvNumber.setText(user.getPhoneNumber());
            mTvNickname.setText(user.getNickname());
            String photoUrl = user.getPhotoUrl();
            RequestOptions options = new RequestOptions();
            options.circleCrop();
            options.placeholder(R.mipmap.ic_photo_default);
            Glide.with(mContext)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE + photoUrl)
                    .apply(options)
                    .into(mIvMinePhoto);
        } else {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.circleCrop();
            options.placeholder(R.mipmap.ic_photo_default);
            Glide.with(mContext)
                    .asBitmap()
                    .load("")
                    .apply(options)
                    .into(mIvMinePhoto);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(MsgEvent carrier) {
        String content = carrier.getEventMessage();
        switch (content) {
            case MESSAGE_EVENT_UPDATE_USER_UI:
                updateUserUI();
                break;
        }
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

    @Override
    public void onClick(View v) {
//        AppManager.getInstance().jumpActivity(getActivity(), LoginActivity.class);
        switch (v.getId()) {
            case R.id.rl_mine_pay:
                break;
            case R.id.rl_mine_comment:
                break;
            case R.id.rl_mine_receive:
                break;
            case R.id.rl_mine_refund:
                break;
            default:
                break;
        }
    }

}
