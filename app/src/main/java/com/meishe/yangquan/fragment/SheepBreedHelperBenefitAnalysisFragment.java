package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手-效益分析
 * @date 2020/12/15 18:36
 */
public class SheepBreedHelperBenefitAnalysisFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private static final String TYPE_KEY_BATCH_ID = "batch_id";

    private RelativeLayout mRlBenefitAnalysisOpen;
    private RelativeLayout mRlBenefitAnalysisClose;
    private LinearLayout mLlBenefitAnalysisContent;

    /*批次id */
    private int mBatchId;


    public static SheepBreedHelperProcessFragment newInstance(int batchId) {
        SheepBreedHelperProcessFragment helperBaseMessage = new SheepBreedHelperProcessFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY_BATCH_ID, batchId);
        helperBaseMessage.setArguments(bundle);
        return helperBaseMessage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBatchId = arguments.getInt(TYPE_KEY_BATCH_ID);
        }
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_breed_helper_benefit_analysis_fragment, container, false);
        mRlBenefitAnalysisOpen = view.findViewById(R.id.rl_benefit_analysis_open);
        mRlBenefitAnalysisClose = view.findViewById(R.id.rl_benefit_analysis_close);
        mLlBenefitAnalysisContent = view.findViewById(R.id.ll_benefit_content);
        return view;
    }

    @Override
    protected void initListener() {
        mRlBenefitAnalysisOpen.setOnClickListener(this);
        mRlBenefitAnalysisClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        openBenefitAnalysis();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_benefit_analysis_open:
                hideBenefitAnalysis();
                break;

            case R.id.rl_benefit_analysis_close:
                openBenefitAnalysis();
                break;
            default:
                break;
        }
    }

    private void openBenefitAnalysis() {
        mRlBenefitAnalysisOpen.setVisibility(View.VISIBLE);
        mRlBenefitAnalysisClose.setVisibility(View.GONE);
        mLlBenefitAnalysisContent.setVisibility(View.GONE);
    }

    private void hideBenefitAnalysis() {
        mRlBenefitAnalysisOpen.setVisibility(View.GONE);
        mRlBenefitAnalysisClose.setVisibility(View.VISIBLE);
        mLlBenefitAnalysisContent.setVisibility(View.VISIBLE);
    }
}
