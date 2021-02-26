package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BUMineFeedGoldActivity;
import com.meishe.yangquan.activity.BUMineRecommendActivity;
import com.meishe.yangquan.activity.BUMineShoppingMessageActivity;
import com.meishe.yangquan.utils.AppManager;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 商版我的页面
 */
public class BUMineFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private View rl_bu_shopping_message;
    private View rl_bu_feed_gold;
    private View rl_bu_recommend;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view =inflater.inflate(R.layout.fragment_bu_mine,container,false);
        rl_bu_shopping_message = view.findViewById(R.id.rl_bu_shopping_message);
        rl_bu_feed_gold = view.findViewById(R.id.rl_bu_feed_gold);
        rl_bu_recommend = view.findViewById(R.id.rl_bu_recommend);
        return view;
    }

    @Override
    protected void initData() {

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
                AppManager.getInstance().jumpActivity(getActivity(), BUMineFeedGoldActivity.class);
                break;
            case R.id.rl_bu_recommend:
                //反馈建议
                AppManager.getInstance().jumpActivity(getActivity(), BUMineRecommendActivity.class);
                break;
        }
    }
}
