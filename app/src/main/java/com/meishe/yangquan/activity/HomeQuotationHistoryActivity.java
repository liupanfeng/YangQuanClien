package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.HistoryInfo;
import com.meishe.yangquan.bean.QuotationHistoryInfo;
import com.meishe.yangquan.bean.QuotationHistoryInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.StatisticsInfo;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.LineChartManager;
import com.meishe.yangquan.utils.Constants;
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
 * 主页--行情历史详情页面
 */
public class HomeQuotationHistoryActivity extends BaseActivity {

    /*今日均价*/
    private TextView tv_today_average_price;
    /*相较于昨日*/
    private TextView tv_compared_with_yesterday;
    /*近7日最高价*/
    private TextView tv_highest_price;
    /*近7日最低价*/
    private TextView tv_lowest_price;
    /*近7日平均价*/
    private TextView tv_average_price;
    /*更新于*/
    private TextView tv_update_time;

    private LineChart mLineChart;
    private List<String> xAxisValues;

    private List<Float> yAxisValues;
    private LineChartManager mLineChartManager;
    private Button btn_seven;
    private Button btn_thirty;
    private String mQuotationId;


    @Override
    protected int initRootView() {
        return R.layout.activity_home_quotation_history;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        tv_today_average_price = findViewById(R.id.tv_today_average_price);
        tv_compared_with_yesterday = findViewById(R.id.tv_compared_with_yesterday);
        tv_highest_price = findViewById(R.id.tv_highest_price);
        tv_lowest_price = findViewById(R.id.tv_lowest_price);
        tv_average_price = findViewById(R.id.tv_average_price);
        tv_update_time = findViewById(R.id.tv_update_time);
        mLineChart = findViewById(R.id.lineChart);
        btn_seven = findViewById(R.id.btn_seven);
        btn_thirty = findViewById(R.id.btn_thirty);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mQuotationId = extras.getString(Constants.QUOTATION_ID);
                getQuotationHistoryData(mQuotationId, Constants.QUOTATION_TIME_TYPE_SEVEN);
            }
        }

        initChartData();
        mLineChartManager = new LineChartManager(this, mLineChart);
        mLineChartManager.showLineChart(xAxisValues, yAxisValues);

    }

    private void initChartData() {
        xAxisValues = new ArrayList<>();
        xAxisValues.add("1.15");
        xAxisValues.add("1.16");
        xAxisValues.add("1.17");
        xAxisValues.add("1.18");
        xAxisValues.add("1.19");
        xAxisValues.add("1.20");
        xAxisValues.add("1.21");

        yAxisValues = new ArrayList<>();
        yAxisValues.add(35f);
        yAxisValues.add(36f);
        yAxisValues.add(35.5f);
        yAxisValues.add(36.3f);
        yAxisValues.add(35.8f);
        yAxisValues.add(36.2f);
        yAxisValues.add(35.8f);
    }


    @Override
    public void initListener() {
        btn_seven.setOnClickListener(this);
        btn_thirty.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_thirty:
                getQuotationHistoryData(mQuotationId, Constants.QUOTATION_TIME_TYPE_THIRTY);
                break;
            case R.id.btn_seven:
                getQuotationHistoryData(mQuotationId, Constants.QUOTATION_TIME_TYPE_SEVEN);
                break;
        }
    }


    /**
     * 根据请求的数据更新view
     *
     * @param data
     */
    private void updateUi(QuotationHistoryInfo data) {
        if (data == null) {
            return;
        }
        StatisticsInfo statistics = data.getStatistics();   //获取行情信息
        HistoryInfo history = data.getHistory();   //折线图数据
        /*更新行情数据*/
        if (statistics != null) {
            tv_today_average_price.setText(statistics.getToday() + "");
            tv_compared_with_yesterday.setText(statistics.getYesterday() + "");
            tv_highest_price.setText(statistics.getMax() + "");
            tv_lowest_price.setText(statistics.getMin() + "");
            tv_average_price.setText(statistics.getAverage() + "");
            tv_update_time.setText("更新于：" + statistics.getUpdateDate());
        }

        /*更新折线图数据*/
        if (history != null) {
            List<String> dates = history.getDates();
            List<Float> prices = history.getPrices();
            mLineChartManager = new LineChartManager(this, mLineChart);
            mLineChartManager.showLineChart(dates, prices);
        }
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("历史记录");
    }


    /**
     * 获取历史行情数据
     *
     * @param quotationId 行情id
     * @param type        seven 7天的数据   thirty：30天的数据
     */
    private void getQuotationHistoryData(String quotationId, String type) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        if (TextUtils.isEmpty(quotationId)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("id", quotationId);
        requestParam.put("timeThresholdType", type);
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_QUOTATION_HISTORY, new BaseCallBack<QuotationHistoryInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, QuotationHistoryInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    QuotationHistoryInfo data = result.getData();
                    if (data == null) {
                        return;
                    }
                    updateUi(data);
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }

}