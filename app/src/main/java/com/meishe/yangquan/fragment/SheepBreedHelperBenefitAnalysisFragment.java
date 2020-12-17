package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手-效益分析
 * @date 2020/12/15 18:36
 */
public class SheepBreedHelperBenefitAnalysisFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private RelativeLayout mRlBenefitAnalysisOpen;
    private RelativeLayout mRlBenefitAnalysisClose;
    private LinearLayout mLlBenefitAnalysisContent;

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
        switch (view.getId()){
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
