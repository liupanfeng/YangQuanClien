package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BUMineFeedGoldActivity;
import com.meishe.yangquan.activity.BUMineRecommendActivity;
import com.meishe.yangquan.activity.BUMineShoppingMessageActivity;
import com.meishe.yangquan.activity.MineFeedGoldActivity;
import com.meishe.yangquan.activity.SettingActivity;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_SHOPPING_INFO;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 商版我的页面
 */
public class BUMineFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private View rl_bu_shopping_message;
    private View rl_bu_feed_gold;
    private View rl_bu_recommend;
    private ImageView iv_mine_photo;
    private TextView tv_bu_mine_shopping_name;
    /*真实名称*/
    private TextView tv_bu_mine_real_name;
    private View iv_bu_mine_setting;
    /*头像部分的容器*/
    private View rl_bu_photo_container;

    public static BUMineFragment newInstance(){
        return new BUMineFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view =inflater.inflate(R.layout.fragment_bu_mine,container,false);
        rl_bu_shopping_message = view.findViewById(R.id.rl_bu_shopping_message);
        rl_bu_feed_gold = view.findViewById(R.id.rl_bu_feed_gold);
        rl_bu_recommend = view.findViewById(R.id.rl_bu_recommend);
        iv_mine_photo = view.findViewById(R.id.iv_mine_photo);
        iv_bu_mine_setting = view.findViewById(R.id.iv_bu_mine_setting);
        tv_bu_mine_shopping_name = view.findViewById(R.id.tv_bu_mine_shopping_name);
        tv_bu_mine_real_name = view.findViewById(R.id.tv_bu_mine_real_name);
        rl_bu_photo_container = view.findViewById(R.id.rl_bu_photo_container);
        return view;
    }

    @Override
    protected void initData() {
        UserInfo user = UserManager.getInstance(getContext()).getUser();
        if (user!=null){
            GlideUtil.getInstance().loadPhotoUrl(user.getIconUrl(),iv_mine_photo);
        }
        BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
        if (buShoppingInfo== null){
            return;
        }
        int authState = buShoppingInfo.getAuthState();
        if (authState!=1){
            return;
        }
        initShoppingView(buShoppingInfo);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }

    private void initShoppingView(BUShoppingInfo buShoppingInfo) {
        tv_bu_mine_shopping_name.setText(buShoppingInfo.getName());
        tv_bu_mine_real_name.setText(buShoppingInfo.getOwnerName()+"的店铺");
    }

    @Override
    protected void initListener() {
        rl_bu_shopping_message.setOnClickListener(this);
        rl_bu_feed_gold.setOnClickListener(this);
        rl_bu_recommend.setOnClickListener(this);
        iv_bu_mine_setting.setOnClickListener(this);
        rl_bu_photo_container.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_bu_shopping_message:
            case R.id.rl_bu_photo_container:
                BUShoppingInfo buShoppingInfo = UserManager.getInstance(mContext).getBuShoppingInfo();
                if (buShoppingInfo==null){
                    ToastUtil.showToast("请先申请开店…");
                    return;
                }
                //店铺信息
                AppManager.getInstance().jumpActivity(getActivity(), BUMineShoppingMessageActivity.class);
                break;
            case R.id.rl_bu_feed_gold:
                //饲料金
                AppManager.getInstance().jumpActivity(getActivity(), MineFeedGoldActivity.class);
                break;
            case R.id.rl_bu_recommend:
                //反馈建议
                AppManager.getInstance().jumpActivity(getActivity(), BUMineRecommendActivity.class);
                break;

            case R.id.iv_bu_mine_setting:
                //设置
                AppManager.getInstance().jumpActivity(getActivity(), SettingActivity.class);
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
            case MESSAGE_TYPE_UPDATE_SHOPPING_INFO:
                initData();
                break;
        }
    }

}
