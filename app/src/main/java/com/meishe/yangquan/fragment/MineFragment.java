package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.SettingActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.view.RoundAngleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO;


public class MineFragment extends BaseRecyclerFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;

//    private String[] mSettingInfo = {"个人信息", "饲料金", "我的关注", "我的消息",
//            "我的积分", "养殖档案", "我的收藏",
//            "系统消息", "支付密码"};
        private String[] mSettingInfo = {"个人信息", "饲料金", "我的关注", "我的消息", "建议留言",
            "我的积分","养殖档案", "我的收藏",
            "系统消息", "支付密码"};
//    private int[] mSettingIcon = {
//            R.mipmap.ic_mine_personal_message,
//            R.mipmap.ic_mine_feed_gold, R.mipmap.ic_mine_my_focus,
//            R.mipmap.ic_mine_my_message,
//            R.mipmap.ic_mine_my_points,
//            R.mipmap.ic_mine_keep,
//            R.mipmap.ic_mine_my_collection,
//            R.mipmap.ic_mine_system_message,
//            R.mipmap.ic_mine_pay_password};

        private int[] mSettingIcon = {R.mipmap.ic_mine_personal_message,
            R.mipmap.ic_mine_feed_gold, R.mipmap.ic_mine_my_focus,
            R.mipmap.ic_mine_my_message,
            R.mipmap.ic_mine_suggest,
            R.mipmap.ic_mine_my_points,
            R.mipmap.ic_mine_keep,
            R.mipmap.ic_mine_my_collection,
            R.mipmap.ic_mine_system_message,
            R.mipmap.ic_mine_pay_password};

    private RoundAngleImageView mIvMinePhoto;

    private TextView mTvNickname;
    /*待支付*/
    private RelativeLayout mRlMinePay;
    /*待收货*/
    private RelativeLayout mRlMineReceive;
    /*待评论*/
    private RelativeLayout mRlMineCommont;
    /*待退款*/
    private RelativeLayout mRlMineRefund;
    /*设置*/
    private ImageView mIvSetting;

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
    public void onDestroy() {
        super.onDestroy();
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

        mTvNickname = view.findViewById(R.id.tv_mine_nickname);
        mIvSetting = view.findViewById(R.id.iv_mine_setting);
        mIvMinePhoto = view.findViewById(R.id.iv_mine_photo);

        return view;
    }


    @Override
    protected void initListener() {
        mRlMinePay.setOnClickListener(this);
        mRlMineReceive.setOnClickListener(this);
        mRlMineCommont.setOnClickListener(this);
        mRlMineRefund.setOnClickListener(this);
        mIvSetting.setOnClickListener(this);
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

        updateUserUI();

    }

    private void updateUserUI() {
        UserInfo user = UserManager.getInstance(getContext()).getUser();
        if (user != null) {
            mTvNickname.setText(user.getNickname());
            String photoUrl = user.getIconUrl();
            RequestOptions options = new RequestOptions();
            options.circleCrop();
            options.placeholder(R.mipmap.ic_default_photo);
            Glide.with(mContext)
                    .asBitmap()
                    .load(photoUrl)
                    .apply(options)
                    .into(mIvMinePhoto);
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
            case R.id.iv_mine_setting:
                //设置
                AppManager.getInstance().jumpActivity(getActivity(), SettingActivity.class);
                break;
            default:
                break;
        }
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
            case MESSAGE_TYPE_UPDATE_USER_INFO:
                updateUserUI();
                break;
        }
    }

}
