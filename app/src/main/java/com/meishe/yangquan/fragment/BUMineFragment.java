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
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.utils.AppManager;

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

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view =inflater.inflate(R.layout.fragment_bu_mine,container,false);
        rl_bu_shopping_message = view.findViewById(R.id.rl_bu_shopping_message);
        rl_bu_feed_gold = view.findViewById(R.id.rl_bu_feed_gold);
        rl_bu_recommend = view.findViewById(R.id.rl_bu_recommend);
        iv_mine_photo = view.findViewById(R.id.iv_mine_photo);
        tv_bu_mine_shopping_name = view.findViewById(R.id.tv_bu_mine_shopping_name);
        tv_bu_mine_real_name = view.findViewById(R.id.tv_bu_mine_real_name);
        return view;
    }

    @Override
    protected void initData() {
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

    private void initShoppingView(BUShoppingInfo buShoppingInfo) {
        tv_bu_mine_shopping_name.setText(buShoppingInfo.getName());
        tv_bu_mine_real_name.setText(buShoppingInfo.getOwnerName()+"的店铺");
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_mine_photo_default);
        Glide.with(mContext)
                .asBitmap()
                .load(buShoppingInfo.getShopInSideImageUrls().get(0))
                .apply(options)
                .into(iv_mine_photo);
    }

    @Override
    protected void initListener() {
        rl_bu_shopping_message.setOnClickListener(this);
        rl_bu_feed_gold.setOnClickListener(this);
        rl_bu_recommend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_bu_shopping_message:
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
