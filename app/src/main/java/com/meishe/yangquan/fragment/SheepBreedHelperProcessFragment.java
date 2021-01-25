package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.SheepHairInfo;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手-养殖过程
 * @date 2020/12/15 18:36
 */
public class SheepBreedHelperProcessFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private static final String TYPE_KEY_BATCH_ID = "batch_id";

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
    private List<SheepHairInfo> mSheepHairInfoList = new ArrayList<>();


    /*损耗记录*/
    private RelativeLayout mRlLossRecordOpen;
    private RelativeLayout mRlLossRecordClose;
    private LinearLayout mLlLossRecordContent;
    private int mBatchId;
    private LinearLayout mLlCutSheepAdd;

    private RecyclerView mRecyclerViewCutHair;
    /*剪羊毛*/
    private MultiFunctionAdapter mSheepHairAdapter;


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
        View view = inflater.inflate(R.layout.sheep_breed_helper_process_fragment, container, false);

        mRlFoodAnalysisOpen = view.findViewById(R.id.rl_food_analysis_open);
        mRlFoodAnalysisClose = view.findViewById(R.id.rl_food_analysis_close);
        mLlFoodAnalysisContent = view.findViewById(R.id.ll_food_analysis_content);

        mRlVaccineRecordOpen = view.findViewById(R.id.rl_vaccine_record_open);
        mRlVaccineRecordClose = view.findViewById(R.id.rl_vaccine_record_close);
        mLlVaccineRecordContent = view.findViewById(R.id.ll_vaccine_record_content);

        /*剪羊毛*/
        mRlCutSheepHairOpen = view.findViewById(R.id.rl_cut_sheep_hair_open);
        mRlCutSheepHairClose = view.findViewById(R.id.rl_cut_sheep_hair_close);
        mLlCutSheepHairContent = view.findViewById(R.id.ll_cut_sheep_hair_content);
        mLlCutSheepAdd = view.findViewById(R.id.ll_create_cut_hair);
        mRecyclerViewCutHair = view.findViewById(R.id.recycler_cut_hair);
        initHairRecyclerView();

        mRlLossRecordOpen = view.findViewById(R.id.rl_loss_record_open);
        mRlLossRecordClose = view.findViewById(R.id.rl_loss_record_close);
        mLlLossRecordContent = view.findViewById(R.id.ll_loss_record_content);


        return view;
    }

    /**
     * 剪羊毛
     */
    private void initHairRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mSheepHairAdapter = new MultiFunctionAdapter(mContext, mRecyclerViewCutHair);
        mRecyclerViewCutHair.setLayoutManager(layoutManager);
        mRecyclerViewCutHair.setAdapter(mSheepHairAdapter);
        mSheepHairAdapter.addAll(mSheepHairInfoList);
    }

    @Override
    protected void initListener() {
        mRlFoodAnalysisOpen.setOnClickListener(this);
        mRlFoodAnalysisClose.setOnClickListener(this);

        mRlVaccineRecordOpen.setOnClickListener(this);
        mRlVaccineRecordClose.setOnClickListener(this);

        mRlCutSheepHairOpen.setOnClickListener(this);
        mRlCutSheepHairClose.setOnClickListener(this);
        mRlCutSheepHairClose.setOnClickListener(this);
        mLlCutSheepAdd.setOnClickListener(this);


        mRlLossRecordOpen.setOnClickListener(this);
        mRlLossRecordClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mSheepHairInfoList.clear();
        hideFoodAnalysisContent();
        hideVaccineRecordContent();
        hideCutSheepHairContent();
        hideLossRecordContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_food_analysis_open:
                showFoodAnalysisContent();
                break;
            case R.id.ll_create_cut_hair:
                //增加一条剪羊毛的记录
                mSheepHairAdapter.addItem(new SheepHairInfo());
                mSheepHairAdapter.notifyDataSetChanged();

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

    /**
     * 隐藏配料分析
     */
    private void hideFoodAnalysisContent() {
        mRlFoodAnalysisOpen.setVisibility(View.VISIBLE);
        mRlFoodAnalysisClose.setVisibility(View.GONE);
        mLlFoodAnalysisContent.setVisibility(View.GONE);
    }

    /**
     * 打开配料分析
     */
    private void showFoodAnalysisContent() {
        mRlFoodAnalysisOpen.setVisibility(View.GONE);
        mRlFoodAnalysisClose.setVisibility(View.VISIBLE);
        mLlFoodAnalysisContent.setVisibility(View.VISIBLE);
        getFodderAnalysisData();
    }

    /**
     * 隐藏疫苗记录
     */
    private void hideVaccineRecordContent() {
        mRlVaccineRecordOpen.setVisibility(View.VISIBLE);
        mRlVaccineRecordClose.setVisibility(View.GONE);
        mLlVaccineRecordContent.setVisibility(View.GONE);
    }

    /**
     * 展示疫苗记录
     */
    private void showVaccineRecordContent() {
        mRlVaccineRecordOpen.setVisibility(View.GONE);
        mRlVaccineRecordClose.setVisibility(View.VISIBLE);
        mLlVaccineRecordContent.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏剪羊毛
     */
    private void hideCutSheepHairContent() {
        mRlCutSheepHairOpen.setVisibility(View.VISIBLE);
        mRlCutSheepHairClose.setVisibility(View.GONE);
        mLlCutSheepHairContent.setVisibility(View.GONE);
    }

    /**
     * 展示剪羊毛
     */
    private void showCutSheepHairContent() {
        mRlCutSheepHairOpen.setVisibility(View.GONE);
        mRlCutSheepHairClose.setVisibility(View.VISIBLE);
        mLlCutSheepHairContent.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏损耗
     */
    private void hideLossRecordContent() {
        mRlLossRecordOpen.setVisibility(View.VISIBLE);
        mRlLossRecordClose.setVisibility(View.GONE);
        mLlLossRecordContent.setVisibility(View.GONE);
    }

    /**
     * 展示损耗
     */
    private void showLossRecordContent() {
        mRlLossRecordOpen.setVisibility(View.GONE);
        mRlLossRecordClose.setVisibility(View.VISIBLE);
        mLlLossRecordContent.setVisibility(View.VISIBLE);
    }

    /**
     * 获取配料分析数据
     */
    private void getFodderAnalysisData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_ANALYSE_LIST, new BaseCallBack<ServerResult>() {
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
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast(mContext, "发布成功");
                    BitmapUtils.deleteCacheFile();
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
