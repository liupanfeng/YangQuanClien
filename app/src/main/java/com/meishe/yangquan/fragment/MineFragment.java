package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.MineOrderActivity;
import com.meishe.yangquan.activity.SettingActivity;
import com.meishe.yangquan.activity.TestActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.view.RoundAngleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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
//        private String[] mSettingInfo = {"个人信息", "饲料金", "我的关注", "我的消息", "建议留言",
//            "我的积分","养殖档案", "我的收藏",
//            "系统消息", "支付密码"};
        private String[] mSettingInfo = {"个人信息", "饲料金", "我的关注", "我的消息",
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

//        private int[] mSettingIcon = {R.mipmap.ic_mine_personal_message,
//            R.mipmap.ic_mine_feed_gold, R.mipmap.ic_mine_my_focus,
//            R.mipmap.ic_mine_my_message,
//            R.mipmap.ic_mine_suggest,
//            R.mipmap.ic_mine_my_points,
//            R.mipmap.ic_mine_keep,
//            R.mipmap.ic_mine_my_collection,
//            R.mipmap.ic_mine_system_message,
//            R.mipmap.ic_mine_pay_password};
        private int[] mSettingIcon = {R.mipmap.ic_mine_personal_message,
            R.mipmap.ic_mine_feed_gold, R.mipmap.ic_mine_my_focus,
            R.mipmap.ic_mine_my_message,
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
        }else{
            getUserInfo();
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
                Bundle bundle=new Bundle();
                bundle.putInt(Constants.KEY_ORDER_STATE_TYPE,Constants.TYPE_ORDER_WAIT_PAY_TYPE);
                bundle.putInt(Constants.KEY_TAB_SELECT_INDEX,Constants.TYPE_ORDER_WAIT_PAY_TYPE);
                AppManager.getInstance().jumpActivity(getActivity(),MineOrderActivity.class,bundle);
                break;
            case R.id.rl_mine_comment:
                bundle=new Bundle();
                bundle.putInt(Constants.KEY_ORDER_STATE_TYPE,Constants.TYPE_ORDER_ALREADY_SEND_TYPE);
                bundle.putInt(Constants.KEY_TAB_SELECT_INDEX,Constants.TYPE_ORDER_ALREADY_SEND_TYPE);
                AppManager.getInstance().jumpActivity(getActivity(),MineOrderActivity.class,bundle);
                break;
            case R.id.rl_mine_receive:
//                bundle=new Bundle();
//                bundle.putInt(Constants.KEY_ORDER_STATE_TYPE,Constants.TYPE_ORDER_WAIT_SEND_TYPE);
//                bundle.putInt(Constants.KEY_TAB_SELECT_INDEX,Constants.TYPE_ORDER_WAIT_SEND_TYPE);
//                AppManager.getInstance().jumpActivity(getActivity(),MineOrderActivity.class,bundle);


                AppManager.getInstance().jumpActivity(getActivity(), TestActivity.class);


                break;
            case R.id.rl_mine_refund:
                bundle=new Bundle();
                bundle.putInt(Constants.KEY_ORDER_STATE_TYPE,Constants.TYPE_ORDER_FINISH_TYPE);
                bundle.putInt(Constants.KEY_TAB_SELECT_INDEX,Constants.TYPE_ORDER_FINISH_TYPE);
                AppManager.getInstance().jumpActivity(getActivity(),MineOrderActivity.class,bundle);
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
     * 获取用户信息
     */
    private void getUserInfo() {
        showLoading();
        String token = getToken();
        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_GET_USER_INFO, new BaseCallBack<UserResult>() {
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
            protected void onSuccess(Call call, Response response, UserResult result) {
                hideLoading();
                if (result != null) {
                    UserInfo user = result.getData();
                    if (user != null) {
                        UserManager.getInstance(App.getContext()).setUser(user);
                        updateUserUI();
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (e instanceof com.google.gson.JsonParseException) {
                    ToastUtil.showToast(mContext, mContext.getString(R.string.data_analysis_error));
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);

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
