package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手-基本信息
 * @date 2020/12/15 18:36
 */
public class SheepBreedHelperBaseMessage extends BaseRecyclerFragment implements View.OnClickListener {


    private RelativeLayout mRlOpen;
    private RelativeLayout mRlPickUp;
    private LinearLayout mLlBaseMessage;
    private LinearLayout mLlOpenClose;
    private RelativeLayout mRlOutOpen;
    private RelativeLayout mRlOpenClose;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_breed_helper_base_message_fragment, container, false);
        mRlOpen = view.findViewById(R.id.rl_open);
        mRlPickUp = view.findViewById(R.id.rl_pick_up);
        mLlBaseMessage = view.findViewById(R.id.ll_base_message);
        mRlOutOpen = view.findViewById(R.id.rl_out_open);
        mRlOpenClose= view.findViewById(R.id.rl_open_close);
        mLlOpenClose = view.findViewById(R.id.ll_open_close);
        return view;
    }

    @Override
    protected void initListener() {
        mRlOpen.setOnClickListener(this);
        mRlPickUp.setOnClickListener(this);
        mRlOutOpen.setOnClickListener(this);
        mRlOpenClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        hideEnterPickUp();
        hideOutPickUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_open:
                showEnterPickUp();
                break;
            case R.id.rl_pick_up:
                hideEnterPickUp();
                break;
            case R.id.rl_out_open:
                showOutPickUp();
                break;
            case R.id.rl_open_close:
                hideOutPickUp();
                break;
            default:
                break;

        }
    }

    private void hideOutPickUp() {
        mRlOutOpen.setVisibility(View.VISIBLE);
        mRlOpenClose.setVisibility(View.GONE);
        mLlOpenClose.setVisibility(View.GONE);
    }

    private void showOutPickUp() {
        mRlOutOpen.setVisibility(View.GONE);
        mRlOpenClose.setVisibility(View.VISIBLE);
        mLlOpenClose.setVisibility(View.VISIBLE);
    }

    private void hideEnterPickUp() {
        mRlOpen.setVisibility(View.VISIBLE);
        mRlPickUp.setVisibility(View.GONE);
        mLlBaseMessage.setVisibility(View.GONE);
    }

    private void showEnterPickUp() {
        mRlOpen.setVisibility(View.GONE);
        mRlPickUp.setVisibility(View.VISIBLE);
        mLlBaseMessage.setVisibility(View.VISIBLE);
    }
}
