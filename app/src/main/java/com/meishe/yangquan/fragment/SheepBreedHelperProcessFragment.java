package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FodderInfo;
import com.meishe.yangquan.bean.FoodRecordInfo;
import com.meishe.yangquan.bean.FoodRecordInfoResult;
import com.meishe.yangquan.bean.HouseKeeperSourceAnalysisInfo;
import com.meishe.yangquan.bean.HouseKeeperSourceAnalysisResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.SheepHairInfo;
import com.meishe.yangquan.bean.SheepHairInfoResult;
import com.meishe.yangquan.bean.SheepLossInfo;
import com.meishe.yangquan.bean.SheepLossInfoResult;
import com.meishe.yangquan.bean.SheepNutritionInfo;
import com.meishe.yangquan.bean.SheepNutritionInfoResult;
import com.meishe.yangquan.bean.SheepVaccineInfo;
import com.meishe.yangquan.bean.SheepVaccineInfoResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.SharedPreferencesUtil;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /*饲料记录*/
    private FoodRecordInfo mFoodRecordInfo;

    /*保存饲料分析*/
    private Button btn_food_analysis;
    /*名称map*/
    private Map<Integer, String> mFoodAnalysisNameMap = new HashMap<>();

    /*蛋白map*/
    private Map<Integer, Float> mFoodAnalysisProteinMap = new HashMap<>();
    /*能量map*/
    private Map<Integer, Float> mFoodAnalysisEnergyMap = new HashMap<>();
    /*钙map*/
    private Map<Integer, Float> mFoodAnalysisCalciumMap = new HashMap<>();
    /*盐map*/
    private Map<Integer, Float> mFoodAnalysisSaltMap = new HashMap<>();
    /*磷map*/
    private Map<Integer, Float> mFoodAnalysisPhosphorusMap = new HashMap<>();
    /*小苏打map*/
    private Map<Integer, Float> mFoodAnalysisSodaMap = new HashMap<>();

    /*合计重量*/
    private TextView tv_total_weight;
    /*合计总价*/
    private TextView tv_total_price;
    private ArrayList<FodderInfo> mFoodAnalsisList;
    /*蛋白含量*/
    private TextView tv_protein;
    /*蛋白含量 建议*/
    private TextView tv_protein_advise;

    /*能量含量*/
    private TextView tv_energy;
    /*能量含量 建议*/
    private TextView tv_energy_advise;

    /*钙含量*/
    private TextView tv_calcium;
    /*钙含量 建议*/
    private TextView tv_calcium_advise;

    /*盐"含量*/
    private TextView tv_salt;
    /*盐含量 建议*/
    private TextView tv_salt_advise;

    /*磷含量*/
    private TextView tv_phosphorus;
    /*磷含量 建议*/
    private TextView tv_phosphorus_advise;

    /*小苏打含量*/
    private TextView tv_soda;
    /*小苏打 建议*/
    private TextView tv_soda_advise;
    /*疫苗合计价格*/
    private TextView tv_vaccine_total_price;
    /*羊毛总价*/
    private TextView tv_hair_total_price;
    /*损耗合计*/
    private TextView tv_loss_total_price;
    /*求助*/
    private Button btn_help;

    /*饲料记录的id*/
    private int mFodderRecordId;
    /*建议*/
    private TextView tv_sheep_food_advise;


    public static SheepBreedHelperProcessFragment newInstance(int batchId, int currentCulturalQuantity, long initTime) {
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
        btn_food_analysis = view.findViewById(R.id.btn_food_analysis);

        tv_total_weight = view.findViewById(R.id.tv_total_weight);
        tv_total_price = view.findViewById(R.id.tv_total_price);

        tv_protein = view.findViewById(R.id.tv_protein);
        tv_protein_advise = view.findViewById(R.id.tv_protein_advise);

        tv_energy = view.findViewById(R.id.tv_energy);
        tv_energy_advise = view.findViewById(R.id.tv_energy_advise);

        tv_calcium = view.findViewById(R.id.tv_calcium);
        tv_calcium_advise = view.findViewById(R.id.tv_calcium_advise);

        tv_salt = view.findViewById(R.id.tv_salt);
        tv_salt_advise = view.findViewById(R.id.tv_salt_advise);

        tv_phosphorus = view.findViewById(R.id.tv_phosphorus);
        tv_phosphorus_advise = view.findViewById(R.id.tv_phosphorus_advise);

        tv_soda = view.findViewById(R.id.tv_soda);
        tv_soda_advise = view.findViewById(R.id.tv_soda_advise);

        tv_sheep_food_advise = view.findViewById(R.id.tv_sheep_food_advise);

        btn_help = view.findViewById(R.id.btn_help);

        initFoodAnalysisRecyclerView();

        /*打疫苗*/
        mRlVaccineRecordOpen = view.findViewById(R.id.rl_vaccine_record_open);
        mRlVaccineRecordClose = view.findViewById(R.id.rl_vaccine_record_close);
        mLlVaccineRecordContent = view.findViewById(R.id.ll_vaccine_record_content);
        mRecyclerViewVaccine = view.findViewById(R.id.recycler_vaccine);
        tv_vaccine_total_price = view.findViewById(R.id.tv_vaccine_total_price);
        mLlVaccineAdd = view.findViewById(R.id.ll_create_vaccine);
        initVaccineRecyclerView();

        /*剪羊毛*/
        mRlCutSheepHairOpen = view.findViewById(R.id.rl_cut_sheep_hair_open);
        mRlCutSheepHairClose = view.findViewById(R.id.rl_cut_sheep_hair_close);
        mLlCutSheepHairContent = view.findViewById(R.id.ll_cut_sheep_hair_content);
        mLlCutSheepAdd = view.findViewById(R.id.ll_create_cut_hair);
        mRecyclerViewCutHair = view.findViewById(R.id.recycler_cut_hair);
        tv_hair_total_price = view.findViewById(R.id.tv_hair_total_price);
        initHairRecyclerView();

        mRlLossRecordOpen = view.findViewById(R.id.rl_loss_record_open);
        mRlLossRecordClose = view.findViewById(R.id.rl_loss_record_close);
        mLlLossRecordContent = view.findViewById(R.id.ll_loss_record_content);
        mRecyclerViewLoss = view.findViewById(R.id.recycler_loss);
        mLlLossAdd = view.findViewById(R.id.ll_create_loss);
        tv_loss_total_price = view.findViewById(R.id.tv_loss_total_price);
        initLossRecyclerView();

        if (mCurrentCulturalQuantity == 0) {
            String stringAmount = SharedPreferencesUtil.getInstance(mContext).getString("sheep_amount_" + mBatchId);
            if (!TextUtils.isEmpty(stringAmount)) {
                mCurrentCulturalQuantity = Integer.valueOf(stringAmount);
            }
        }
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
        mFoodRecordInfo = new FoodRecordInfo();
        try {
            mTvFeedingDays.setText(FormatCurrentData.daysBetween(new Date(mInitTime), new Date(System.currentTimeMillis())) + "");
            mFoodRecordInfo.setFeedDays(FormatCurrentData.daysBetween(new Date(mInitTime), new Date(System.currentTimeMillis())));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mFoodRecordInfo.setBatchId(mBatchId);

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
        btn_food_analysis.setOnClickListener(this);
        btn_help.setOnClickListener(this);

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

        mEtInputEstimateWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.length();
                if (length > 0) {
                    try {
                        String inputWeight = charSequence.toString().trim();
                        Float weight = Float.valueOf(inputWeight);
                        mFoodRecordInfo.setSheepWeight(weight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    mFoodRecordInfo.setSheepWeight(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
            case R.id.btn_help:
                //求助
                applyHelp();
                break;

            case R.id.btn_food_analysis:
                /*合计总重量*/
                float totalWeight = 0;
                /*合计总价*/
                float totalPrice = 0;

                /*总蛋白含量*/
                float totalProtein = 0;
                /*总能量含量*/
                float totalEnergy = 0;

                /*总钙含量*/
                float totalCalcium = 0;

                /*总盐含量*/
                float totalSalt = 0;
                /*总磷含量*/
                float totalPhosphorus = 0;
                /*总小苏打含量*/
                float totalSoda = 0;
                List<BaseInfo> data = mFoodAnalysisAdapter.getData();
                if (data != null) {
                    for (int i = 0; i < data.size(); i++) {
                        BaseInfo baseInfo = data.get(i);
                        if (baseInfo instanceof FodderInfo) {
                            float weight = ((FodderInfo) baseInfo).getWeight();
                            totalWeight += weight;
                            totalPrice += ((FodderInfo) baseInfo).getTotalPrice();

                            int fodderId = ((FodderInfo) baseInfo).getFodderId();
                            /*计算蛋白总量*/
                            totalProtein += weight * mFoodAnalysisProteinMap.get(fodderId);
                            /*计算能量总量*/
                            totalEnergy += weight * mFoodAnalysisEnergyMap.get(fodderId);
                            /*计算钙总量*/
                            totalCalcium += weight * mFoodAnalysisCalciumMap.get(fodderId);
                            /*计算盐总量*/
                            totalSalt += weight * mFoodAnalysisSaltMap.get(fodderId);
                            /*计算磷总量*/
                            totalPhosphorus += weight * mFoodAnalysisPhosphorusMap.get(fodderId);
                            /*计算小苏打总量*/
                            totalSoda += weight * mFoodAnalysisSodaMap.get(fodderId);

                        }
                    }

                    for (int i = 0; i < data.size(); i++) {
                        BaseInfo baseInfo = data.get(i);
                        if (baseInfo instanceof FodderInfo) {
                            DecimalFormat df = new DecimalFormat("#.00");
                            String format = df.format(((FodderInfo) baseInfo).getWeight() / totalWeight);
                            Float percent = Float.valueOf(format);
                            /*设置日配比*/
                            ((FodderInfo) baseInfo).setPercent(percent);
                        }
                    }
                }


                tv_total_weight.setText(totalWeight + "");
                tv_total_price.setText(totalPrice + "");

                mFoodRecordInfo.setFodders(mFoodAnalsisList);
                saveFoodAnalysisData();
                break;

            default:
                break;

        }
    }

    /**
     * 求助
     */
    private void applyHelp() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("fodderRecordId", mFodderRecordId);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_APPLY_HELP, new BaseCallBack<ServerResult>() {
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
                    ToastUtil.showToast(mContext, "求助成功");
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
     * 保存饲料数据
     */
    public void saveFoodAnalysisData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(mFoodRecordInfo);
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("info", json);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_SAVE, new BaseCallBack<ServerResult>() {
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
                    ToastUtil.showToast(mContext, "保存成功");
                    getNutritionAnalysisData();
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
     * 获取营养分析数据
     */
    public void getNutritionAnalysisData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(mFoodRecordInfo);
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("batchId", mBatchId);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_ANALYSE, new BaseCallBack<SheepNutritionInfoResult>() {
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
            protected void onSuccess(Call call, Response response, SheepNutritionInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<SheepNutritionInfo> data = result.getData();
                    if (CommonUtils.isEmpty(data)) {
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i < data.size(); i++) {
                        SheepNutritionInfo sheepNutritionInfo = data.get(i);
                        if (sheepNutritionInfo == null) {
                            continue;
                        }
                        stringBuilder.append(sheepNutritionInfo.getSuggest() + "\n");
                        if (sheepNutritionInfo.getName().equals("蛋白质")) {
                            tv_protein.setText(sheepNutritionInfo.getUserValue());
                        } else if (sheepNutritionInfo.getName().equals("能量")) {
                            tv_energy.setText(sheepNutritionInfo.getUserValue());
                        } else if (sheepNutritionInfo.getName().equals("钙")) {
                            tv_calcium.setText(sheepNutritionInfo.getUserValue());
                        } else if (sheepNutritionInfo.getName().equals("盐")) {
                            tv_salt.setText(sheepNutritionInfo.getUserValue());
                        } else if (sheepNutritionInfo.getName().equals("磷")) {
                            tv_phosphorus.setText(sheepNutritionInfo.getUserValue());
                        } else if (sheepNutritionInfo.getName().equals("小苏打")) {
                            tv_soda.setText(sheepNutritionInfo.getUserValue());
                        }
                    }

                    tv_sheep_food_advise.setText(stringBuilder.toString());
                    //更新营养成分含量数据

//                    tv_energy.setText(Util.float2StringTwoPosition(totalEnergy));
//                    tv_calcium.setText(Util.float2StringTwoPosition(totalCalcium));
//                    tv_salt.setText(Util.float2StringTwoPosition(totalSalt));
//                    tv_phosphorus.setText(Util.float2StringTwoPosition(totalPhosphorus));
//                    tv_soda.setText(Util.float2StringTwoPosition(totalSoda));


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
    public void getFoodAnalysisData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("batchId", mBatchId);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_FODDER_LATEST, new BaseCallBack<FoodRecordInfoResult>() {
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
            protected void onSuccess(Call call, Response response, FoodRecordInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    FoodRecordInfo data = result.getData();
                    if (data != null) {
                        updateUI(data);
                        getNutritionAnalysisData();
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
     * in，更新数据
     *
     * @param data
     */
    private void updateUI(FoodRecordInfo data) {
        if (data == null) {
            return;
        }
        mFoodAnalsisList.clear();
        mFoodAnalsisList.addAll(data.getFodders());
        mFodderRecordId = data.getId();
        List<FodderInfo> fodders = data.getFodders();
        if (CommonUtils.isEmpty(fodders)) {
            return;
        }
        /*合计总重量*/
        float totalWeight = 0;
        /*合计总价*/
        float totalPrice = 0;

        /*总蛋白含量*/
        float totalProtein = 0;
        /*总能量含量*/
        float totalEnergy = 0;

        /*总钙含量*/
        float totalCalcium = 0;

        /*总盐含量*/
        float totalSalt = 0;
        /*总磷含量*/
        float totalPhosphorus = 0;
        /*总小苏打含量*/
        float totalSoda = 0;


        for (int i = 0; i < fodders.size(); i++) {
            FodderInfo fodderInfo = fodders.get(i);
            if (fodderInfo == null) {
                continue;
            }
            float weight = fodderInfo.getWeight();
            totalWeight += weight;
            totalPrice += fodderInfo.getTotalPrice();

            int fodderId = fodderInfo.getFodderId();
            String name = mFoodAnalysisNameMap.get(fodderId);
            fodderInfo.setName(name);

//            /*计算蛋白总量*/
//            totalProtein += weight * (mFoodAnalysisProteinMap.get(fodderId) == null ? 0 : mFoodAnalysisProteinMap.get(fodderId));
//            /*计算能量总量*/
//            totalEnergy += weight * (mFoodAnalysisEnergyMap.get(fodderId) == null ? 0 : mFoodAnalysisEnergyMap.get(fodderId));
//            /*计算钙总量*/
//            totalCalcium += weight * (mFoodAnalysisCalciumMap.get(fodderId) == null ? 0 : mFoodAnalysisCalciumMap.get(fodderId));
//            /*计算盐总量*/
//            totalSalt += weight * (mFoodAnalysisSaltMap.get(fodderId) == null ? 0 : mFoodAnalysisSaltMap.get(fodderId));
//            /*计算磷总量*/
//            totalPhosphorus += weight * (mFoodAnalysisPhosphorusMap.get(fodderId) == null ? 0 : mFoodAnalysisPhosphorusMap.get(fodderId));
//            /*计算小苏打总量*/
//            totalSoda += weight * (mFoodAnalysisSodaMap.get(fodderId) == null ? 0 : mFoodAnalysisSodaMap.get(fodderId));

        }
        mFoodAnalysisAdapter.addAll(fodders);

        //估重   喂养天数  合计  营养成分
        try {
            //喂养天数
            mTvFeedingDays.setText(FormatCurrentData.daysBetween(new Date(mInitTime), new Date(System.currentTimeMillis())) + "");
            mEtInputEstimateWeight.setText(data.getSheepWeight() + "");
            tv_total_weight.setText(totalWeight + "");
            tv_total_price.setText(totalPrice + "");

            //更新营养成分含量数据
//            tv_protein.setText(Util.float2StringTwoPosition(totalProtein));
//            tv_energy.setText(Util.float2StringTwoPosition(totalEnergy));
//            tv_calcium.setText(Util.float2StringTwoPosition(totalCalcium));
//            tv_salt.setText(Util.float2StringTwoPosition(totalSalt));
//            tv_phosphorus.setText(Util.float2StringTwoPosition(totalPhosphorus));
//            tv_soda.setText(Util.float2StringTwoPosition(totalSoda));

        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                    doLossTotalPrice();
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
                    doCutHairTotalPrice();
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
                    doVaccineTotalPrice();
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
                    if (data == null) {
                        return;
                    }
                    mFoodAnalsisList = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        HouseKeeperSourceAnalysisInfo houseKeeperSourceAnalysisInfo = data.get(i);
                        FodderInfo fodderInfo = new FodderInfo();
                        fodderInfo.setFodderId(houseKeeperSourceAnalysisInfo.getId());
                        fodderInfo.setName(houseKeeperSourceAnalysisInfo.getName());
                        mFoodAnalysisNameMap.put(houseKeeperSourceAnalysisInfo.getId(), houseKeeperSourceAnalysisInfo.getName());
                        mFoodAnalysisProteinMap.put(houseKeeperSourceAnalysisInfo.getId(), houseKeeperSourceAnalysisInfo.getProtein());
                        mFoodAnalysisEnergyMap.put(houseKeeperSourceAnalysisInfo.getId(), houseKeeperSourceAnalysisInfo.getEnergy());
                        mFoodAnalysisCalciumMap.put(houseKeeperSourceAnalysisInfo.getId(), houseKeeperSourceAnalysisInfo.getCalcium());
                        mFoodAnalysisSaltMap.put(houseKeeperSourceAnalysisInfo.getId(), houseKeeperSourceAnalysisInfo.getSalt());
                        mFoodAnalysisPhosphorusMap.put(houseKeeperSourceAnalysisInfo.getId(), houseKeeperSourceAnalysisInfo.getPhosphorus());
                        mFoodAnalysisSodaMap.put(houseKeeperSourceAnalysisInfo.getId(), houseKeeperSourceAnalysisInfo.getCarbohydrate());
                        mFoodAnalsisList.add(fodderInfo);
                    }

                    mFoodRecordInfo.setFodders(mFoodAnalsisList);

                    mSourceAnalysisInfos.clear();
                    /*营养记录*/
                    mSourceAnalysisInfos.addAll(data);
                    /*饲料记录*/
                    mFoodAnalysisAdapter.addAll(mFoodAnalsisList);

                    getFoodAnalysisData();
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
        switch (eventType) {
            case MessageEvent.MESSAGE_TYPE_VACCINE_TOTAL_PRICE:
                doVaccineTotalPrice();
                break;
            case MessageEvent.MESSAGE_TYPE_LOSS_TOTAL_PRICE:
                doLossTotalPrice();
                break;
            case MessageEvent.MESSAGE_TYPE_CUT_HAIR_TOTAL_PRICE:
                doCutHairTotalPrice();
                break;
        }
    }

    private void doCutHairTotalPrice() {
        List<BaseInfo> data;
        float totalPrice = 0;
        data = mSheepHairAdapter.getData();
        if (CommonUtils.isEmpty(data)) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            BaseInfo baseInfo = data.get(i);
            if (baseInfo instanceof SheepHairInfo) {
                float price = ((SheepHairInfo) baseInfo).getPrice();
                totalPrice += price;
            }
        }
        tv_hair_total_price.setText(totalPrice + "");
    }

    private void doLossTotalPrice() {
        List<BaseInfo> data;
        float totalPrice = 0;
        data = mSheepLossAdapter.getData();
        if (CommonUtils.isEmpty(data)) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            BaseInfo baseInfo = data.get(i);
            if (baseInfo instanceof SheepLossInfo) {
                float price = ((SheepLossInfo) baseInfo).getPrice();
                totalPrice += price;
            }
        }
        tv_loss_total_price.setText(totalPrice + "");
    }

    private void doVaccineTotalPrice() {
        List<BaseInfo> data;
        float totalPrice = 0;
        data = mSheepVaccineAdapter.getData();
        if (CommonUtils.isEmpty(data)) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            BaseInfo baseInfo = data.get(i);
            if (baseInfo instanceof SheepVaccineInfo) {
                float price = ((SheepVaccineInfo) baseInfo).getPrice();
                totalPrice += price;
            }
        }
        tv_vaccine_total_price.setText(totalPrice + "");
    }
}
