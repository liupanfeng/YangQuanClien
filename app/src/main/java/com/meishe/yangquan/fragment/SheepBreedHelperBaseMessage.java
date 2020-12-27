package com.meishe.yangquan.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.ServiceResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手-基本信息
 * @date 2020/12/15 18:36
 */
public class SheepBreedHelperBaseMessage extends BaseRecyclerFragment implements View.OnClickListener {

    private static final String TYPE_KEY_BATCH_ID = "batch_id";

    private RelativeLayout mRlOpen;
    private RelativeLayout mRlPickUp;
    private LinearLayout mLlBaseMessage;
    private LinearLayout mLlOpenClose;
    private RelativeLayout mRlOutOpen;
    private RelativeLayout mRlOpenClose;
    private TextView mTvSelectSheepType;
    /*羊肉价格*/
    private EditText mEtSheepPrice;
    /*羊重量*/
    private EditText mEtSelectSheepWeight;
    /*进栏数量*/
    private EditText mEtSelectSheepNumbr;
    /*入栏时间*/
    private TextView mTvSheepTime;
    /*保存*/
    private Button mBtnBaseMessageSave;
    private int mBatchId;

    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePicker mDatePicker;
    private TextView mTvSellType;
    private EditText mEtSellSheepPrice;

    /*出栏重量*/
    private EditText mEtSellWeight;
    /*羊腔重量*/
    private EditText mEtSellSheepWeight;
    /*出栏数量*/
    private EditText mEtSellSheepNumber;
    /*剩余数量*/
    private TextView mTvSellSheepSurplusNumber;
    /*出栏时间*/
    private TextView mTvSellSheepTime;
    private Button mBtnSellSheep;

    public static SheepBreedHelperBaseMessage newInstance(int batchId) {
        SheepBreedHelperBaseMessage helperBaseMessage = new SheepBreedHelperBaseMessage();
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
        View view = inflater.inflate(R.layout.sheep_breed_helper_base_message_fragment, container, false);
        mRlOpen = view.findViewById(R.id.rl_open);
        mRlPickUp = view.findViewById(R.id.rl_pick_up);
        mLlBaseMessage = view.findViewById(R.id.ll_base_message);
        mRlOutOpen = view.findViewById(R.id.rl_out_open);
        mRlOpenClose = view.findViewById(R.id.rl_open_close);
        mLlOpenClose = view.findViewById(R.id.ll_open_close);

        mTvSelectSheepType = view.findViewById(R.id.tv_select_sheep_type);
        mEtSheepPrice = view.findViewById(R.id.et_sheep_price);
        mEtSelectSheepWeight = view.findViewById(R.id.et_sheep_weight);
        mEtSelectSheepNumbr = view.findViewById(R.id.et_sheep_number);
        mTvSheepTime = view.findViewById(R.id.tv_sheep_time);
        mBtnBaseMessageSave = view.findViewById(R.id.btn_base_massage_save);

        /*----------出栏数据------------*/
        mTvSellType = view.findViewById(R.id.tv_sell_type);
        mEtSellSheepPrice = view.findViewById(R.id.et_sell_sheep_price);
        mEtSellWeight = view.findViewById(R.id.et_sell_weight);
        mEtSellSheepWeight = view.findViewById(R.id.et_sell_sheep_weight);
        mEtSellSheepNumber = view.findViewById(R.id.et_sell_sheep_number);
        mTvSellSheepSurplusNumber = view.findViewById(R.id.tv_surplus_number);
        mTvSellSheepTime = view.findViewById(R.id.tv_sell_sheep_time);
        mBtnSellSheep = view.findViewById(R.id.btn_sell_sheep);


        mTvSheepTime.setText(FormatDateUtil.longToString(System.currentTimeMillis(),
                FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));

        mTvSellSheepTime.setText(FormatDateUtil.longToString(System.currentTimeMillis(),
                FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));

        return view;
    }

    @Override
    protected void initListener() {
        mRlOpen.setOnClickListener(this);
        mRlPickUp.setOnClickListener(this);
        mRlOutOpen.setOnClickListener(this);
        mRlOpenClose.setOnClickListener(this);
        mTvSelectSheepType.setOnClickListener(this);
        mTvSheepTime.setOnClickListener(this);
        mBtnBaseMessageSave.setOnClickListener(this);
        mTvSellSheepTime.setOnClickListener(this);

        mBtnSellSheep.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        hideEnterPickUp();
        hideOutPickUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            /*选择肉羊品种*/
            case R.id.tv_select_sheep_type:

                break;
            /*基础信息保存*/
            case R.id.btn_base_massage_save:
                saveBaseMessageToServer();
                break;
            /*入栏时间*/
            case R.id.tv_sheep_time:
                showDataPicker(mTvSheepTime);
                break;
            /*选择出栏时间*/
            case R.id.tv_sell_sheep_time:
                showDataPicker(mTvSellSheepTime);
                break;
            /*出栏*/
            case R.id.btn_sell_sheep:
                sellSheepMessageToServer();
                break;
            default:
                break;

        }
    }

    /**
     * 出栏信息保存到数据库
     */
    private void sellSheepMessageToServer() {

        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        String sellType = mTvSellType.getText().toString().trim();
        if (TextUtils.isEmpty(sellType)) {
            return;
        }
        String sellSheepPrice = mEtSellSheepPrice.getText().toString().trim();
        if (TextUtils.isEmpty(sellSheepPrice)) {
            ToastUtil.showToast(mContext,"出栏价格必须填写");
            return;
        }

        String sellWeight = mEtSellWeight.getText().toString().trim();
        String sellSheepWeight = mEtSellSheepWeight.getText().toString().trim();
        String sellSheepNumber = mEtSellSheepNumber.getText().toString().trim();
        if (TextUtils.isEmpty(sellSheepPrice)) {
            ToastUtil.showToast(mContext,"出栏数量必须填写");
            return;
        }
        String sellSheepsurplusNumber = mTvSellSheepSurplusNumber.getText().toString().trim();
        String sellSheepTime = mTvSellSheepTime.getText().toString().trim();

        HashMap<String, Object> param = new HashMap<>();
        param.put("batchId", mBatchId);
        param.put("outType", 1);
        param.put("price", sellSheepPrice);
        param.put("weight", sellWeight);
        param.put("cavityWeight", sellSheepWeight);
        param.put("amount", sellSheepNumber);
        param.put("outDate", sellSheepTime);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_BASE_MESSAGE_BATCH_OUT, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                if (serverResult == null) {
                    return;
                }
                if (serverResult.getCode() != 1) {
                    ToastUtil.showToast(mContext, "code:" + serverResult.getCode() + "error:" + serverResult.getMsg());
                    return;
                } else {
                    ToastUtil.showToast(mContext, "出栏信息已上传");
                }
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

    /**
     * 展示时间选择器
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDataPicker(final TextView textView) {
        String strTime = mTvSheepTime.getText().toString().trim();
        if (!TextUtils.isEmpty(strTime)) {
            String[] split = strTime.split("-");
            if (split != null && split.length == 3) {
                mYear = Integer.valueOf(split[0]);
                mMonth = Integer.valueOf(split[1]);
                mDay = Integer.valueOf(split[2]);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setNegativeButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth() + 1;
                int dayOfMonth = mDatePicker.getDayOfMonth();
                textView.setText(year + "-" + month + "-" + dayOfMonth);
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        View view = View.inflate(getContext(), R.layout.dialog_date, null);
        mDatePicker = view.findViewById(R.id.datePicker);
        alertDialog.setTitle("设置日期");
        alertDialog.setView(view);
        alertDialog.show();
        mDatePicker.init(mYear, mMonth - 1, mDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        });
    }

    /**
     * 保存基础数据
     */
    private void saveBaseMessageToServer() {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        String sheepType = mTvSelectSheepType.getText().toString().trim();
        if (TextUtils.isEmpty(sheepType)) {
            return;
        }
        String sheepPrice = mEtSheepPrice.getText().toString().trim();
        if (TextUtils.isEmpty(sheepPrice)) {
            ToastUtil.showToast(mContext, "请输入羊价格");
            return;
        }
        String sheepWeight = mEtSelectSheepWeight.getText().toString().trim();
        if (TextUtils.isEmpty(sheepWeight)) {
            ToastUtil.showToast(mContext, "请输入羊重量");
            return;
        }
        String sheepNumber = mEtSelectSheepNumbr.getText().toString().trim();
        if (TextUtils.isEmpty(sheepNumber)) {
            ToastUtil.showToast(mContext, "请输入羊数量");
            return;
        }

        String sheepTime = mTvSheepTime.getText().toString().trim();
        if (TextUtils.isEmpty(sheepTime)) {
            ToastUtil.showToast(mContext, "请输入入栏时间");
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("batchId", mBatchId);
        param.put("variety", sheepType);
        param.put("price", sheepPrice);
        param.put("weight", sheepWeight);
        param.put("amount", sheepNumber);
        param.put("inDate", sheepTime);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_BASE_MESSAGE_BATCH_IN, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                if (serverResult == null) {
                    return;
                }
                if (serverResult.getCode() != 1) {
                    ToastUtil.showToast(mContext, "code:" + serverResult.getCode() + "error:" + serverResult.getMsg());
                    return;
                } else {
                    ToastUtil.showToast(mContext, "建档信息保存成功");
                }

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
