package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手-养殖过程
 * @date 2020/12/15 18:36
 */
public class SheepBreedHelperProcessFragment extends BaseRecyclerFragment implements View.OnClickListener {

    /*配料分析*/
    private RelativeLayout mRlFoodAnalysisOpen;
    private RelativeLayout mRlFoodAnalysisClose;
    private LinearLayout mLlFoodAnalysisContent;

    /*疫苗记录*/
    private RelativeLayout mRlVaccineRecordOpen;
    private RelativeLayout mRlVaccineRecordClose;
    private LinearLayout mLlVaccineRecordContent;

    /*剪毛记录*/
    private RelativeLayout mRlCutSheepHairOpen;
    private RelativeLayout mRlCutSheepHairClose;
    private LinearLayout mLlCutSheepHairContent;

    /*损耗记录*/
    private RelativeLayout mRlLossRecordOpen;
    private RelativeLayout mRlLossRecordClose;
    private LinearLayout mLlLossRecordContent;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_breed_helper_process_fragment, container, false);

        mRlFoodAnalysisOpen = view.findViewById(R.id.rl_food_analysis_open);
        mRlFoodAnalysisClose = view.findViewById(R.id.rl_food_analysis_close);
        mLlFoodAnalysisContent = view.findViewById(R.id.ll_food_analysis_content);

        mRlVaccineRecordOpen = view.findViewById(R.id.rl_vaccine_record_open);
        mRlVaccineRecordClose = view.findViewById(R.id.rl_vaccine_record_close);
        mLlVaccineRecordContent = view.findViewById(R.id.ll_vaccine_record_content);

        mRlCutSheepHairOpen = view.findViewById(R.id.rl_cut_sheep_hair_open);
        mRlCutSheepHairClose = view.findViewById(R.id.rl_cut_sheep_hair_close);
        mLlCutSheepHairContent = view.findViewById(R.id.ll_cut_sheep_hair_content);


        mRlLossRecordOpen = view.findViewById(R.id.rl_loss_record_open);
        mRlLossRecordClose = view.findViewById(R.id.rl_loss_record_close);
        mLlLossRecordContent = view.findViewById(R.id.ll_loss_record_content);


        return view;
    }

    @Override
    protected void initListener() {
        mRlFoodAnalysisOpen.setOnClickListener(this);
        mRlFoodAnalysisClose.setOnClickListener(this);

        mRlVaccineRecordOpen.setOnClickListener(this);
        mRlVaccineRecordClose.setOnClickListener(this);

        mRlCutSheepHairOpen.setOnClickListener(this);
        mRlCutSheepHairClose.setOnClickListener(this);

        mRlLossRecordOpen.setOnClickListener(this);
        mRlLossRecordClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        hideFoodAnalysisContent();
        hideVaccineRecordContent();
        hideCutSheepHairContent();
        hideLossRecordContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_food_analysis_open:
                showFoodAnalysisContent();
                break;
            case R.id.rl_food_analysis_close:
                hideFoodAnalysisContent();
                break;

            case R.id.rl_vaccine_record_open:
                showVaccineRecordContent();
                break;
            case R.id.rl_vaccine_record_close:
                hideVaccineRecordContent();
                break;

            case R.id.rl_cut_sheep_hair_open:
                showCutSheepHairContent();
                break;
            case R.id.rl_cut_sheep_hair_close:
                hideCutSheepHairContent();
                break;

            case R.id.rl_loss_record_open:
                showLossRecordContent();
                break;
            case R.id.rl_loss_record_close:
                hideLossRecordContent();
                break;

            default:
                break;

        }
    }

    private void hideFoodAnalysisContent() {
        mRlFoodAnalysisOpen.setVisibility(View.VISIBLE);
        mRlFoodAnalysisClose.setVisibility(View.GONE);
        mLlFoodAnalysisContent.setVisibility(View.GONE);
    }

    private void showFoodAnalysisContent() {
        mRlFoodAnalysisOpen.setVisibility(View.GONE);
        mRlFoodAnalysisClose.setVisibility(View.VISIBLE);
        mLlFoodAnalysisContent.setVisibility(View.VISIBLE);
    }

    private void hideVaccineRecordContent() {
        mRlVaccineRecordOpen.setVisibility(View.VISIBLE);
        mRlVaccineRecordClose.setVisibility(View.GONE);
        mLlVaccineRecordContent.setVisibility(View.GONE);
    }

    private void showVaccineRecordContent() {
        mRlVaccineRecordOpen.setVisibility(View.GONE);
        mRlVaccineRecordClose.setVisibility(View.VISIBLE);
        mLlVaccineRecordContent.setVisibility(View.VISIBLE);
    }

    private void hideCutSheepHairContent() {
        mRlCutSheepHairOpen.setVisibility(View.VISIBLE);
        mRlCutSheepHairClose.setVisibility(View.GONE);
        mLlCutSheepHairContent.setVisibility(View.GONE);
    }

    private void showCutSheepHairContent() {
        mRlCutSheepHairOpen.setVisibility(View.GONE);
        mRlCutSheepHairClose.setVisibility(View.VISIBLE);
        mLlCutSheepHairContent.setVisibility(View.VISIBLE);
    }

    private void hideLossRecordContent() {
        mRlLossRecordOpen.setVisibility(View.VISIBLE);
        mRlLossRecordClose.setVisibility(View.GONE);
        mLlLossRecordContent.setVisibility(View.GONE);
    }

    private void showLossRecordContent() {
        mRlLossRecordOpen.setVisibility(View.GONE);
        mRlLossRecordClose.setVisibility(View.VISIBLE);
        mLlLossRecordContent.setVisibility(View.VISIBLE);
    }

}
