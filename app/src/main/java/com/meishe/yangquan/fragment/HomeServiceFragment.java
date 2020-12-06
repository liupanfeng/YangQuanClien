package com.meishe.yangquan.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.ServicePublishActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.MarketResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.CustomButton;
import com.meishe.yangquan.wiget.CustomTextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 主页-服务页面
 * @date 2020/11/26 10:43
 */
public class HomeServiceFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private static final int TYPE_SERVICE_CUT_WOOL = 9;
    private static final int TYPE_SERVICE_VACCINE = 10;
    private static final int TYPE_SERVICE_SHEEP_DUNG = 11;
    private static final int TYPE_SERVICE_LOOK_CAR = 12;

    /*最新*/
    private static final int TYPE_MARKET_LIST_TYPE_NEWEST = 1;
    /*推荐*/
    private static final int TYPE_MARKET_LIST_TYPE_RECOMMEND = 2;

    /*剪羊毛*/
    private CustomButton mCutWool;
    /*打疫苗*/
    private CustomButton mVaccine;
    /*拉羊粪*/
    private CustomButton mSheepDung;
    /*找车辆*/
    private CustomButton mLookCar;

    private CustomTextView mTvMarketNewest;
    private CustomTextView mTvMarketCommand;

    private int mListType = TYPE_MARKET_LIST_TYPE_NEWEST;
    private int mServiceType = TYPE_SERVICE_CUT_WOOL;

    private MultiFunctionAdapter mServiceAdapter;
    private ImageView mIvPublishService;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_home_service,container,false);
        mCutWool = view.findViewById(R.id.btn_cut_wool);
        mVaccine = view.findViewById(R.id.btn_vaccine);
        mSheepDung = view.findViewById(R.id.btn_sheep_dung);
        mLookCar = view.findViewById(R.id.btn_look_car);

        mRecyclerView = view.findViewById(R.id.recycler);
        mTvMarketNewest = view.findViewById(R.id.tv_service_newest);
        mTvMarketCommand = view.findViewById(R.id.tv_service_command);
        mIvPublishService = view.findViewById(R.id.iv_publish_service);
        return view;
    }

    @Override
    protected void initListener() {
        mCutWool.setOnClickListener(this);
        mVaccine.setOnClickListener(this);
        mSheepDung.setOnClickListener(this);
        mLookCar.setOnClickListener(this);
        mTvMarketNewest.setOnClickListener(this);
        mTvMarketCommand.setOnClickListener(this);
        mIvPublishService.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mTvMarketNewest.setSelected(true);
        initRecyclerView();
        selectCutWool();
        getServiceDataFromServer(TYPE_SERVICE_CUT_WOOL, TYPE_MARKET_LIST_TYPE_NEWEST);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mServiceAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setAdapter(mServiceAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cut_wool:
                mServiceType = TYPE_SERVICE_CUT_WOOL;
                selectCutWool();
                getServiceDataFromServer(mServiceType, mListType);
                break;
            case R.id.btn_vaccine:
                mServiceType = TYPE_SERVICE_VACCINE;
                selectVaccine();
                getServiceDataFromServer(mServiceType, mListType);
                break;
            case R.id.btn_sheep_dung:
                mServiceType = TYPE_SERVICE_SHEEP_DUNG;
                selectSheepDung();
                getServiceDataFromServer(mServiceType, mListType);
                break;
            case R.id.btn_look_car:
                mServiceType = TYPE_SERVICE_LOOK_CAR;
                selectLookCar();
                getServiceDataFromServer(mServiceType, mListType);
                break;
            case R.id.tv_market_newest:
                mListType = TYPE_MARKET_LIST_TYPE_NEWEST;
                mTvMarketNewest.setSelected(true);
                mTvMarketCommand.setSelected(false);
                getServiceDataFromServer(mServiceType, mListType);
                break;
            case R.id.tv_market_command:
                mListType = TYPE_MARKET_LIST_TYPE_RECOMMEND;
                mTvMarketCommand.setSelected(true);
                mTvMarketNewest.setSelected(false);
                getServiceDataFromServer(mServiceType, mListType);
                break;
            case R.id.iv_publish_service:
                //发布服务
                AppManager.getInstance().jumpActivity(getActivity(), ServicePublishActivity.class);
                break;
            default:
                break;
        }
    }


    private void selectCutWool() {
        mCutWool.setSelected(true);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(false);
    }

    private void selectVaccine() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(true);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(false);
    }

    private void selectSheepDung() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(true);
        mLookCar.setSelected(false);
    }

    private void selectLookCar() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(true);
    }


    /**
     * 获取服务数据
     */
    private void getServiceDataFromServer(int typeId, int listType) {
//        mLoading.show();
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("listType", listType);
        param.put("pageNum", 1);
        param.put("pageSize", 30);
        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_QUOTATION, new BaseCallBack<MarketResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, MarketResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<MarketInfo> data = result.getData();
                if (data == null || data.size() == 0) {
                    return;
                }
                mServiceAdapter.addAll(data);
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }

}
