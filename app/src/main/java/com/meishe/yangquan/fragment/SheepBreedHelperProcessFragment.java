package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.HouseKeeperSourceAnalysisInfo;
import com.meishe.yangquan.bean.HouseKeeperSourceAnalysisResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.SheepHairInfo;
import com.meishe.yangquan.bean.SheepHairInfoResult;
import com.meishe.yangquan.bean.SheepLossInfo;
import com.meishe.yangquan.bean.SheepLossInfoResult;
import com.meishe.yangquan.bean.SheepVaccineInfo;
import com.meishe.yangquan.bean.SheepVaccineInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;

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
    private RecyclerView mLlRecyclerFoodAnalysis;
    /*配料分析*/
    private MultiFunctionAdapter mFoodAnalysisAdapter;

    /*疫苗记录*/
    private RelativeLayout mRlVaccineRecordOpen;
    private RelativeLayout mRlVaccineRecordClose;
    private LinearLayout mLlVaccineRecordContent;
    private LinearLayout mLlVaccineAdd;
    private List<SheepVaccineInfo> mSheepVaccineInfoList = new ArrayList<>();
    private RecyclerView mRecyclerViewVaccine;
    private MultiFunctionAdapter mSheepVaccineAdapter;

    /*剪毛记录*/
    private RelativeLayout mRlCutSheepHairOpen;
    private RelativeLayout mRlCutSheepHairClose;
    private LinearLayout mLlCutSheepHairContent;
    private List<SheepHairInfo> mSheepHairInfoList = new ArrayList<>();
    private RecyclerView mRecyclerViewCutHair;
    private LinearLayout mLlCutSheepAdd;
    private MultiFunctionAdapter mSheepHairAdapter;


    /*损耗记录*/
    private RelativeLayout mRlLossRecordOpen;
    private RelativeLayout mRlLossRecordClose;
    private LinearLayout mLlLossRecordContent;
    private List<SheepLossInfo> mSheepLossInfoList = new ArrayList<>();

    private RecyclerView mRecyclerViewLoss;
    private LinearLayout mLlLossAdd;
    private MultiFunctionAdapter mSheepLossAdapter;

    private int mBatchId;
    /*剩余出栏羊只数*/
    private int mCurrentCulturalQuantity;


    /*饲料名称以及包含的营养成分*/
    private List<HouseKeeperSourceAnalysisInfo> mSourceAnalysisInfos = new ArrayList<>();
    private Long mInitTime;
    /*当前估重*/
    private EditText mEtInputEstimateWeight;
    /*喂养天数*/
    private TextView mTvFeedingDays;


    public static SheepBreedHelperProcessFragment newInstance(int batchId, int currentCulturalQuantity,long initTime) {
        SheepBreedHelperProcessFragment helperBaseMessage = new SheepBreedHelperProcessFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY_BATCH_ID, batchId);
        bundle.putInt(Constants.TYPE_KEY_SHEEP_SURPLUS, currentCulturalQuantity);
        bundle.putLong(Constants.TYPE_KEY_SHEEP_INIT_TIME, initTime);
        helperBaseMessage.setArguments(bundle);
        return helperBaseMessage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBatchId = arguments.getInt(TYPE_KEY_BATCH_ID);
            mCurrentCulturalQuantity = arguments.getInt(Constants.TYPE_KEY_SHEEP_SURPLUS);
            mInitTime = arguments.getLong(Constants.TYPE_KEY_SHEEP_INIT_TIME);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_breed_helper_process_fragment, container, false);

        mRlFoodAnalysisOpen = view.findViewById(R.id.rl_food_analysis_open);
        mRlFoodAnalysisClose = view.findViewById(R.id.rl_food_analysis_close);
        mLlFoodAnalysisContent = view.findViewById(R.id.ll_food_analysis_content);
        mLlRecyclerFoodAnalysis = view.findViewById(R.id.recycler);
        mEtInputEstimateWeight = view.findViewById(R.id.et_input_estimate_weight);
        mTvFeedingDays = view.findViewById(R.id.tv_feeding_days);
        mTvFeedingDays.setText(FormatCurrentData.getTimeRangeDay(mInitTime));
        initFoodAnalysisRecyclerView();

        /*打疫苗*/
        mRlVaccineRecordOpen = view.findViewById(R.id.rl_vaccine_record_open);
        mRlVaccineRecordClose = view.findViewById(R.id.rl_vaccine_record_close);
        mLlVaccineRecordContent = view.findViewById(R.id.ll_vaccine_record_content);
        mRecyclerViewVaccine = view.findViewById(R.id.recycler_vaccine);
        mLlVaccineAdd = view.findViewById(R.id.ll_create_vaccine);
        initVaccineRecyclerView();

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
        mRecyclerViewLoss = view.findViewById(R.id.recycler_loss);
        mLlLossAdd = view.findViewById(R.id.ll_create_loss);
        initLossRecyclerView();


        return view;
    }

    /**
     * 损耗
     */
    private void initLossRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mSheepLossAdapter = new MultiFunctionAdapter(mContext, mRecyclerViewLoss);
        mRecyclerViewLoss.setLayoutManager(layoutManager);
        mRecyclerViewLoss.setAdapter(mSheepLossAdapter);
        mSheepLossAdapter.addAll(mSheepLossInfoList);
    }

    /**
     * 打疫苗
     */
    private void initVaccineRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mSheepVaccineAdapter = new MultiFunctionAdapter(mContext, mRecyclerViewVaccine);
        mRecyclerViewVaccine.setLayoutManager(layoutManager);
        mRecyclerViewVaccine.setAdapter(mSheepVaccineAdapter);
        mSheepVaccineAdapter.addAll(mSheepVaccineInfoList);
    }

    /**
     * 配料分析
     */
    private void initFoodAnalysisRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mFoodAnalysisAdapter = new MultiFunctionAdapter(mContext, mLlRecyclerFoodAnalysis);
        mLlRecyclerFoodAnalysis.setLayoutManager(layoutManager);
        mLlRecyclerFoodAnalysis.setAdapter(mFoodAnalysisAdapter);
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
    protected void initData() {
        mSheepHairInfoList.clear();
        hideFoodAnalysisContent();
        hideVaccineRecordContent();
        hideCutSheepHairContent();
        hideLossRecordContent();

        getFodderAnalysisData();
        getSheepLossData();
        getSheepCutHairData();
        getSheepVaccineData();
    }


    @Override
    protected void initListener() {
        mRlFoodAnalysisOpen.setOnClickListener(this);
        mRlFoodAnalysisClose.setOnClickListener(this);

        mRlVaccineRecordOpen.setOnClickListener(this);
        mRlVaccineRecordClose.setOnClickListener(this);
        mLlVaccineAdd.setOnClickListener(this);

        mRlCutSheepHairOpen.setOnClickListener(this);
        mRlCutSheepHairClose.setOnClickListener(this);
        mLlCutSheepAdd.setOnClickListener(this);


        mRlLossRecordOpen.setOnClickListener(this);
        mRlLossRecordClose.setOnClickListener(this);
        mLlLossAdd.setOnClickListener(this);

        mSheepLossAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof SheepLossInfo) {
                    saveSheepLossData((SheepLossInfo) baseInfo);
                }
            }
        });

        mSheepHairAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof SheepHairInfo) {
                    saveSheepHairData((SheepHairInfo) baseInfo);
                }
            }
        });


        mSheepVaccineAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof SheepVaccineInfo) {
                    saveSheepVaccineData((SheepVaccineInfo) baseInfo);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_food_analysis_open:
                showFoodAnalysisContent();
                break;
            case R.id.ll_create_cut_hair:
                //增加一条剪羊毛的记录
                SheepHairInfo sheepHairInfo = new SheepHairInfo();
                sheepHairInfo.setBatchId(mBatchId);
                sheepHairInfo.setAmount(mCurrentCulturalQuantity);
                mSheepHairAdapter.addItem(sheepHairInfo);
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

            case R.id.ll_create_loss:
                //新增损耗
                SheepLossInfo sheepLossInfo = new SheepLossInfo();
                sheepLossInfo.setBatchId(mBatchId);
                mSheepLossAdapter.addItem(sheepLossInfo);
                mSheepLossAdapter.notifyDataSetChanged();

                break;

            case R.id.ll_create_vaccine:
                //增加一条疫苗的记录
                SheepVaccineInfo sheepVaccineInfo = new SheepVaccineInfo();
                sheepVaccineInfo.setAmount(mCurrentCulturalQuantity);
                sheepVaccineInfo.setBatchId(mBatchId);
                mSheepVaccineAdapter.addItem(sheepVaccineInfo);
                mSheepVaccineAdapter.notifyDataSetChanged();

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
     * 获取损耗数据
     */
    public void getSheepLossData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }


        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("batchId", mBatchId);
        requestParam.put("recordType", Constants.TYPE_LOSS_RECORD_TYPE);


        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_OTHER_LIST, new BaseCallBack<SheepLossInfoResult>() {
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
            protected void onSuccess(Call call, Response response, SheepLossInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<SheepLossInfo> data = result.getData();
                    mSheepLossInfoList.clear();
                    mSheepLossInfoList.addAll(data);
                    mSheepLossAdapter.addAll(mSheepLossInfoList);
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
     * 获取剪毛数据
     */
    public void getSheepCutHairData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }


        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("batchId", mBatchId);
        requestParam.put("recordType", Constants.TYPE_CUT_HAIR_RECORD_TYPE);


        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_OTHER_LIST, new BaseCallBack<SheepHairInfoResult>() {
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
            protected void onSuccess(Call call, Response response, SheepHairInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<SheepHairInfo> data = result.getData();
                    mSheepHairInfoList.clear();
                    mSheepHairInfoList.addAll(data);
                    mSheepHairAdapter.addAll(mSheepHairInfoList);
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
     * 获取剪毛数据
     */
    public void getSheepVaccineData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }


        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("batchId", mBatchId);
        requestParam.put("recordType", Constants.TYPE_VACCINE_RECORD_TYPE);


        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_OTHER_LIST, new BaseCallBack<SheepVaccineInfoResult>() {
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
            protected void onSuccess(Call call, Response response, SheepVaccineInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<SheepVaccineInfo> data = result.getData();
                    mSheepVaccineInfoList.clear();
                    mSheepVaccineInfoList.addAll(data);
                    mSheepVaccineAdapter.addAll(mSheepVaccineInfoList);
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
     * 保存损耗数据
     *
     * @param sheepLossInfo
     */
    private void saveSheepLossData(SheepLossInfo sheepLossInfo) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        if (sheepLossInfo == null) {
            return;
        }


        HashMap<String, Object> requestParam = new HashMap<>();
        int id = sheepLossInfo.getId();
        if (id > 0) {
            requestParam.put("id", id);
        }
        int amount = sheepLossInfo.getAmount();
        if (amount <= 0) {
            ToastUtil.showToast("数量必须输入");
            return;
        }
        requestParam.put("batchId", sheepLossInfo.getBatchId());
        requestParam.put("recordType", Constants.TYPE_LOSS_RECORD_TYPE);
        requestParam.put("amount", amount);
        requestParam.put("price", sheepLossInfo.getPrice());
        requestParam.put("recordDate", sheepLossInfo.getRecordDate());


        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_OTHER_SAVE, new BaseCallBack<ServerResult>() {
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
                    ToastUtil.showToast("损耗记录保存成功");
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
     * 保存羊毛数据
     *
     * @param sheepHairInfo
     */
    private void saveSheepHairData(SheepHairInfo sheepHairInfo) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        if (sheepHairInfo == null) {
            return;
        }


        HashMap<String, Object> requestParam = new HashMap<>();
        int id = sheepHairInfo.getId();
        if (id > 0) {
            requestParam.put("id", id);
        }
        int amount = sheepHairInfo.getAmount();
        if (amount <= 0) {
            ToastUtil.showToast("数量必须输入");
            return;
        }
        requestParam.put("batchId", sheepHairInfo.getBatchId());
        requestParam.put("recordType", Constants.TYPE_CUT_HAIR_RECORD_TYPE);
        requestParam.put("amount", amount);
        requestParam.put("price", sheepHairInfo.getPrice());
        requestParam.put("recordDate", sheepHairInfo.getRecordDate());


        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_OTHER_SAVE, new BaseCallBack<ServerResult>() {
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
                    ToastUtil.showToast("剪羊毛记录保存成功");
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
     * 保存羊毛数据
     *
     * @param sheepVaccineInfo
     */
    private void saveSheepVaccineData(SheepVaccineInfo sheepVaccineInfo) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        if (sheepVaccineInfo == null) {
            return;
        }


        HashMap<String, Object> requestParam = new HashMap<>();
        int id = sheepVaccineInfo.getId();
        if (id > 0) {
            requestParam.put("id", id);
        }
        int amount = sheepVaccineInfo.getAmount();
        if (amount <= 0) {
            ToastUtil.showToast("数量必须输入");
            return;
        }

        String recordContent = sheepVaccineInfo.getRecordContent();
        if (TextUtils.isEmpty(recordContent)) {
            ToastUtil.showToast("防疫内容必须输入");
            return;
        }

        requestParam.put("batchId", sheepVaccineInfo.getBatchId());
        requestParam.put("recordType", Constants.TYPE_VACCINE_RECORD_TYPE);
        requestParam.put("amount", amount);
        requestParam.put("recordContent", recordContent);
        requestParam.put("price", sheepVaccineInfo.getPrice());
        requestParam.put("recordDate", sheepVaccineInfo.getRecordDate());


        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_OTHER_SAVE, new BaseCallBack<ServerResult>() {
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
                    ToastUtil.showToast("疫苗记录保存成功");
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
     * 获取饲料数据
     */
    private void getFodderAnalysisData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_ANALYSE_LIST, new BaseCallBack<HouseKeeperSourceAnalysisResult>() {
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
            protected void onSuccess(Call call, Response response, HouseKeeperSourceAnalysisResult result) {
                if (result != null && result.getCode() == 1) {
                    List<HouseKeeperSourceAnalysisInfo> data = result.getData();
                    mSourceAnalysisInfos.clear();
                    mSourceAnalysisInfos.addAll(data);
                    mFoodAnalysisAdapter.addAll(data);
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
