package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.SheepBenefitAnalysisInfo;
import com.meishe.yangquan.bean.SheepBenefitAnalysisInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.SharedPreferencesUtil;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.wiget.IosDialog;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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
    private IosDialog mIosDialog;
    /*总收入*/
    private TextView tv_total_incoming;
    /*总收入-单只*/
    private TextView tv_total_incoming_each;

    /*羊苗费用支出*/
    private TextView tv_totalLittleSheepExpense;
    /*羊苗费用支出-单只*/
    private TextView tv_eachLittleSheepExpense;
    /*羊毛费用支出*/
    private TextView tv_totalWoolExpense;
    /*羊毛费用支出-单只*/
    private TextView tv_eachWoolExpense;
    /*饲料费用支出*/
    private TextView tv_totalFodderExpense;
    /*饲料费用支出-单只*/
    private TextView tv_eachFodderExpense;
    /*疫苗费用支出*/
    private TextView tv_totalVaccineExpense;
    /*疫苗费用支出-单只*/
    private TextView tv_eachVaccineExpense;
    /*损耗费用支出*/
    private TextView tv_totalLostExpense;
    /*损耗费用支出-单只*/
    private TextView tv_eachLostExpense;
    /*总费用支出*/
    private TextView tv_totalExpense;
    /*总费用支出-单只*/
    private TextView tv_teachExpense;
    /*总利润*/
    private TextView tv_totalProfit;
    /*总利润-单只*/
    private TextView tv_eachProfit;
    /*日增重*/
    private TextView tv_dailyGain;
    /*出腔率*/
    private TextView tv_cavityRatio;
    /*料肉比（成品羊）*/
    private TextView tv_fodderSheepRatio;
    /*料肉比（羊腔）*/
    private TextView tv_fodderCavityRatio;

    private boolean mIsOnCreate;

    /*页面类型 1:养殖助手 2：养殖档案*/
    private int mType;

    public static SheepBreedHelperBenefitAnalysisFragment newInstance(int batchId, int type) {
        SheepBreedHelperBenefitAnalysisFragment helperBaseMessage = new SheepBreedHelperBenefitAnalysisFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY_BATCH_ID, batchId);
        bundle.putInt(Constants.TYPE_KEY_SHEEP_TYPE, type);
        helperBaseMessage.setArguments(bundle);
        return helperBaseMessage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsOnCreate = true;
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBatchId = arguments.getInt(TYPE_KEY_BATCH_ID);
            mType = arguments.getInt(Constants.TYPE_KEY_SHEEP_TYPE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsOnCreate = false;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_breed_helper_benefit_analysis_fragment, container, false);
        mRlBenefitAnalysisOpen = view.findViewById(R.id.rl_benefit_analysis_open);
        mRlBenefitAnalysisClose = view.findViewById(R.id.rl_benefit_analysis_close);
        mLlBenefitAnalysisContent = view.findViewById(R.id.ll_benefit_content);
        tv_total_incoming = view.findViewById(R.id.tv_total_incoming);
        tv_total_incoming_each = view.findViewById(R.id.tv_total_incoming_each);
        tv_totalLittleSheepExpense = view.findViewById(R.id.tv_totalLittleSheepExpense);
        tv_eachLittleSheepExpense = view.findViewById(R.id.tv_eachLittleSheepExpense);
        tv_totalWoolExpense = view.findViewById(R.id.tv_totalWoolExpense);
        tv_eachWoolExpense = view.findViewById(R.id.tv_eachWoolExpense);
        tv_totalFodderExpense = view.findViewById(R.id.tv_totalFodderExpense);
        tv_eachFodderExpense = view.findViewById(R.id.tv_eachFodderExpense);
        tv_totalVaccineExpense = view.findViewById(R.id.tv_totalVaccineExpense);
        tv_eachVaccineExpense = view.findViewById(R.id.tv_eachVaccineExpense);
        tv_totalLostExpense = view.findViewById(R.id.tv_totalLostExpense);
        tv_eachLostExpense = view.findViewById(R.id.tv_eachLostExpense);
        tv_totalExpense = view.findViewById(R.id.tv_totalExpense);
        tv_teachExpense = view.findViewById(R.id.tv_teachExpense);
        tv_totalProfit = view.findViewById(R.id.tv_totalProfit);
        tv_eachProfit = view.findViewById(R.id.tv_eachProfit);
        tv_dailyGain = view.findViewById(R.id.tv_dailyGain);
        tv_cavityRatio = view.findViewById(R.id.tv_cavityRatio);
        tv_fodderSheepRatio = view.findViewById(R.id.tv_fodderSheepRatio);
        tv_fodderCavityRatio = view.findViewById(R.id.tv_fodderCavityRatio);
        return view;
    }

    @Override
    protected void initListener() {
        mRlBenefitAnalysisOpen.setOnClickListener(this);
        mRlBenefitAnalysisClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        closeBenefitAnalysis();
        if (mType == 2) {
            getBenefitAnalysisData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!mIsOnCreate) {
            return;
        }
        if (getUserVisibleHint()) {
            if (mType == 2) {
                getBenefitAnalysisData();
                return;
            }
            String totalPrice = SharedPreferencesUtil.getInstance(mContext).getString("total_price_" + mBatchId);
            if (TextUtils.isEmpty(totalPrice)) {
                showInputIncomingDialog();
            } else {
                getBenefitAnalysisData();
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_benefit_analysis_open:
                showBenefitAnalysis();
                break;
            case R.id.rl_benefit_analysis_close:
                closeBenefitAnalysis();
                break;
            default:
                break;
        }
    }

    /**
     * 更新UI
     *
     * @param data
     */
    private void updateUI(SheepBenefitAnalysisInfo data) {
        if (data != null) {
            tv_total_incoming.setText(data.getTotalIncome() + "");
            tv_total_incoming_each.setText(data.getEachIncome() + "");
            tv_totalLittleSheepExpense.setText(data.getTotalLittleSheepExpense() + "");
            tv_eachLittleSheepExpense.setText(data.getEachLittleSheepExpense() + "");
            tv_totalWoolExpense.setText(data.getTotalWoolExpense() + "");
            tv_eachWoolExpense.setText(data.getEachWoolExpense() + "");
            tv_totalFodderExpense.setText(data.getTotalFodderExpense() + "");
            tv_eachFodderExpense.setText(data.getTotalFodderExpense() + "");
            tv_totalVaccineExpense.setText(data.getTotalVaccineExpense() + "");
            tv_eachVaccineExpense.setText(data.getEachVaccineExpense() + "");
            tv_totalLostExpense.setText(data.getTotalLostExpense() + "");
            tv_eachLostExpense.setText(data.getEachLostExpense() + "");
            tv_totalExpense.setText(data.getTotalExpense() + "");
            tv_teachExpense.setText(Util.float2StringTwoPosition(data.getTotalExpense() / data.getSheepCount()));
            tv_totalProfit.setText(data.getTotalProfit() + "");
            tv_eachProfit.setText(Util.float2StringTwoPosition(data.getTotalProfit() / data.getSheepCount()));
            tv_dailyGain.setText(data.getDailyGain() + "");
            tv_cavityRatio.setText(data.getCavityRatio() + "");
            tv_fodderSheepRatio.setText(data.getFodderSheepRatio() + "");
            tv_fodderCavityRatio.setText(data.getFodderCavityRatio() + "");
        }
    }

    private void closeBenefitAnalysis() {
        mRlBenefitAnalysisOpen.setVisibility(View.VISIBLE);
        mRlBenefitAnalysisClose.setVisibility(View.GONE);
        mLlBenefitAnalysisContent.setVisibility(View.GONE);
    }

    private void showBenefitAnalysis() {
        mRlBenefitAnalysisOpen.setVisibility(View.GONE);
        mRlBenefitAnalysisClose.setVisibility(View.VISIBLE);
        mLlBenefitAnalysisContent.setVisibility(View.VISIBLE);
    }

    private void showInputIncomingDialog() {
        final IosDialog.DialogBuilder builder = new IosDialog.DialogBuilder(mContext);
        builder.setTitle("输入总收入");
        builder.setAsureText("确定");
        builder.setCancelText("取消");
        builder.setInputContent("请输入总收入");
        builder.addListener(new IosDialog.OnButtonClickListener() {
            @Override
            public void onAsureClick() {
                String totalPrice = mIosDialog.getmEtInputContent().getText().toString().trim();
                if (TextUtils.isEmpty(totalPrice)) {
                    ToastUtil.showToast(mContext, "请输入总收入");
                    return;
                }
                mIosDialog.hide();
                saveIncomingData(totalPrice);
            }

            @Override
            public void onCancelClick() {
                mIosDialog.hide();
            }
        });
        mIosDialog = builder.create();
    }

    private void saveIncomingData(final String totalPrice) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("batchId", mBatchId);
        requestParam.put("income", totalPrice);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_INCOMING_SAVE, new BaseCallBack<SheepBenefitAnalysisInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "服务异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, SheepBenefitAnalysisInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    SheepBenefitAnalysisInfo data = result.getData();
                    if (data != null) {
                        SharedPreferencesUtil.getInstance(mContext).putString("total_price_" + mBatchId, totalPrice);
                        updateUI(data);
                    }
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "服务异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }


    /**
     * 获取效益分析接口
     */
    private void getBenefitAnalysisData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("batchId", mBatchId);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_BENEFIT_ANALYSIS, new BaseCallBack<SheepBenefitAnalysisInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "服务异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, SheepBenefitAnalysisInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    SheepBenefitAnalysisInfo data = result.getData();
                    if (data != null) {
                        updateUI(data);
                    }
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "服务异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }


}
