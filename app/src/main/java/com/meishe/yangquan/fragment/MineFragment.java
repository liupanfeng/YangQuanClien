package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private String[] mSettingInfo = {"完善资料", "消息中心", "我的商机", "版本更新", "联系我们","关于"};
    private int[] mSettingIcon = {R.mipmap.ic_mine_wanshanziliao, R.mipmap.ic_mine_xiaoxizhongxin, R.mipmap.ic_mine_wodeshangji, R.mipmap.ic_mine_banbengegnxin,
            R.mipmap.ic_mine_lianxiwomen,R.mipmap.ic_about};
    private LinearLayout mLLNoLogin;
    private LinearLayout mLLLogin;
    private TextView mTvNumber;
    private ImageView mIvMinePhoto;
    private TextView mTvNickname;

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
        mLLNoLogin = view.findViewById(R.id.ll_no_login);
        mLLLogin = view.findViewById(R.id.ll_login);
        mIvMinePhoto = view.findViewById(R.id.iv_mine_photo);
        mTvNumber = view.findViewById(R.id.tv_number);
        mTvNickname = view.findViewById(R.id.tv_nickname);
        return view;
    }



    @Override
    protected void initListener() {
        mLLNoLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        MultiFunctionAdapter adapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        adapter.setFragment(this);
        mList.clear();
        for (int i = 0; i < 5; i++) {
            MineTypeInfo info = new MineTypeInfo();
            info.setName(mSettingInfo[i]);
            info.setIcon(mSettingIcon[i]);
            mList.add(info);
        }
        adapter.addAll(mList);

        updateUserUI();

    }

    private void updateUserUI() {
        if (UserManager.getInstance(getContext()).isNeedLogin()){
            mLLNoLogin.setVisibility(View.VISIBLE);
            mLLLogin.setVisibility(View.GONE);
        }else {
            mLLLogin.setVisibility(View.VISIBLE);
            mLLNoLogin.setVisibility(View.GONE);
        }

        User user=UserManager.getInstance(getContext()).getUser();
        if (user!=null){
            mTvNumber.setText(user.getPhoneNumber());
            mTvNickname.setText(user.getNickname());
            String photoUrl=user.getPhotoUrl();
            RequestOptions options = new RequestOptions();
            options.circleCrop();
            options.placeholder(R.mipmap.ic_photo_default);
            Glide.with(mContext)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE+photoUrl)
                    .apply(options)
                    .into(mIvMinePhoto);
        }else{
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
        switch (content){
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
        AppManager.getInstance().jumpActivity(getActivity(), LoginActivity.class);
    }

}
