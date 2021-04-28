package com.meishe.yangquan.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.HouseKeeperEntryInformationInfo;
import com.meishe.yangquan.bean.HouseKeeperEntryInformationInfoResult;
import com.meishe.yangquan.bean.HouseKeeperOutInformationInfo;
import com.meishe.yangquan.bean.HouseKeeperOutInformationInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.SelectSheepSellTypeView;
import com.meishe.yangquan.pop.SelectSheepTypeView;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.SharedPreferencesUtil;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手-基本信息
 * @date 2020/12/15 18:36
 */
public class SheepBreedHelperBaseMessage extends BaseRecyclerFragment implements View.OnClickListener {

    private RelativeLayout mRlOpen;
    private RelativeLayout mRlPickUp;
    private LinearLayout mLlBaseMessage;
    private LinearLayout mLlOpenClose;
    private RelativeLayout mRlOutOpen;
    private RelativeLayout mRlOpenClose;
    private EditText mEtSelectSheepType;
    /*羊肉价格*/
    private EditText mEtSheepPrice;
    /*羊重量*/
    private EditText mEtSelectSheepWeight;
    /*进栏数量*/
    private EditText mEtSelectSheepNumber;
    /*入栏时间*/
    private TextView mTvSheepTime;
    /*保存*/
    private Button mBtnBaseMessageSave;
    /*批次id*/
    private int mBatchId;
    /*剩余出栏羊只数*/
    private int mCurrentCulturalQuantity;

    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePicker mDatePicker;
    private EditText mEtSellType;
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
    private SelectSheepTypeView mSelectSheepTypeView;
    private SelectSheepSellTypeView mSelectSheepSellTypeView;
    private RelativeLayout mRlSellSheepWeight;
    private long mInitTime;

    ///////////////////////////////////////////////////养殖档案新增逻辑///////////////////////////////////////////////////
    /*顶部的标题名称*/
    private TextView tv_title_top_name;
    private TextView tv_pick_up_top_title;
    /*底部标题*/
    private TextView tv_title_bottom_name;
    private TextView tv_pick_up_bottom_title;

    /*页面类型 1:养殖助手 2：养殖档案*/
    private int mType;

    private boolean isForbidEdit = false;

    /**
     * @param batchId                 批次id
     * @param currentCulturalQuantity 剩余数量
     * @param initTime
     * @param type                    1：养殖助手 2.养殖档案
     * @return
     */
    public static SheepBreedHelperBaseMessage newInstance(int batchId, int currentCulturalQuantity, long initTime, int type) {
        SheepBreedHelperBaseMessage helperBaseMessage = new SheepBreedHelperBaseMessage();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE_KEY_BATCH_ID, batchId);
        bundle.putInt(Constants.TYPE_KEY_SHEEP_SURPLUS, currentCulturalQuantity);
        bundle.putInt(Constants.TYPE_KEY_SHEEP_TYPE, type);
        bundle.putLong(Constants.TYPE_KEY_SHEEP_INIT_TIME, initTime);
        helperBaseMessage.setArguments(bundle);
        return helperBaseMessage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBatchId = arguments.getInt(Constants.TYPE_KEY_BATCH_ID);
            mCurrentCulturalQuantity = arguments.getInt(Constants.TYPE_KEY_SHEEP_SURPLUS);
            mType = arguments.getInt(Constants.TYPE_KEY_SHEEP_TYPE);
            mInitTime = arguments.getLong(Constants.TYPE_KEY_SHEEP_INIT_TIME);
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

        mEtSelectSheepType = view.findViewById(R.id.et_select_sheep_type);
        mEtSheepPrice = view.findViewById(R.id.et_sheep_price);
        mEtSelectSheepWeight = view.findViewById(R.id.et_sheep_weight);
        mEtSelectSheepNumber = view.findViewById(R.id.et_sheep_number);
        mTvSheepTime = view.findViewById(R.id.tv_sheep_time);
        mBtnBaseMessageSave = view.findViewById(R.id.btn_base_massage_save);

        /*----------出栏数据------------*/
        mEtSellType = view.findViewById(R.id.et_sell_type);
        mEtSellSheepPrice = view.findViewById(R.id.et_sell_sheep_price);
        mEtSellWeight = view.findViewById(R.id.et_sell_weight);
        mEtSellSheepWeight = view.findViewById(R.id.et_sell_sheep_weight);
        mEtSellSheepNumber = view.findViewById(R.id.et_sell_sheep_number);
        mTvSellSheepSurplusNumber = view.findViewById(R.id.tv_surplus_number);
        mTvSellSheepTime = view.findViewById(R.id.tv_sell_sheep_time);
        mBtnSellSheep = view.findViewById(R.id.btn_sell_sheep);
        mRlSellSheepWeight = view.findViewById(R.id.rl_sell_sheep_weight);


        tv_title_top_name = view.findViewById(R.id.tv_title_top_name);
        tv_pick_up_top_title = view.findViewById(R.id.tv_pick_up_top_title);

        tv_title_bottom_name = view.findViewById(R.id.tv_title_bottom_name);
        tv_pick_up_bottom_title = view.findViewById(R.id.tv_pick_up_bottom_title);
        if (mType == 2) {
            //养殖档案
            tv_title_top_name.setText("建档数据");
            tv_pick_up_top_title.setText("建档数据");

            tv_title_bottom_name.setText("出栏数据");
            tv_pick_up_bottom_title.setText("出栏数据");

            mBtnBaseMessageSave.setVisibility(View.GONE);
            mBtnSellSheep.setVisibility(View.GONE);
        }
        /*出栏剩余只数*/
        mTvSellSheepSurplusNumber.setText(mCurrentCulturalQuantity + "");
        mEtSellSheepNumber.setText(mCurrentCulturalQuantity + "");

        mTvSellSheepTime.setText(FormatDateUtil.longToString(System.currentTimeMillis(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
        mTvSheepTime.setText(FormatDateUtil.longToString(System.currentTimeMillis(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));

        return view;
    }

    @Override
    protected void initListener() {
        mRlOpen.setOnClickListener(this);
        mRlPickUp.setOnClickListener(this);
        mRlOutOpen.setOnClickListener(this);
        mRlOpenClose.setOnClickListener(this);
        mTvSheepTime.setOnClickListener(this);
        mBtnBaseMessageSave.setOnClickListener(this);
        mTvSellSheepTime.setOnClickListener(this);
        mBtnSellSheep.setOnClickListener(this);

        mEtSelectSheepType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                if (isForbidEdit==true){
                    return true;
                }
                if (mSelectSheepTypeView == null) {
                    mSelectSheepTypeView = SelectSheepTypeView.create(mContext, mEtSelectSheepType, new SelectSheepTypeView.OnAttachListener() {
                        @Override
                        public void onSelectType(String content) {
                            mEtSelectSheepType.setText(content);
                        }
                    });
                }
                if (!mSelectSheepTypeView.isShow()) {
                    mSelectSheepTypeView.show();
                }
                return true;
            }
        });

        mEtSheepPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                if (isForbidEdit==true){
                    return true;
                }
                return false;
            }
        });
        mEtSelectSheepWeight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                if (isForbidEdit==true){
                    return true;
                }
                return false;
            }
        });
        mEtSelectSheepNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                if (isForbidEdit==true){
                    return true;
                }
                return false;
            }
        });


        mEtSellType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                if (mSelectSheepSellTypeView == null) {
                    mSelectSheepSellTypeView = SelectSheepSellTypeView.create(mContext, mEtSellType, new SelectSheepSellTypeView.OnAttachListener() {
                        @Override
                        public void onSelectType(String content) {
                            if (content.equals("活羊")) {
                                mRlSellSheepWeight.setVisibility(View.GONE);
                            } else {
                                mRlSellSheepWeight.setVisibility(View.VISIBLE);
                            }
                            mEtSellType.setText(content);
                        }
                    });
                }
                if (!mSelectSheepSellTypeView.isShow()) {
                    mSelectSheepSellTypeView.show();
                }
                return true;
            }
        });

        mEtSellSheepPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                return false;
            }
        });
        mEtSellWeight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                return false;
            }
        });
        mEtSellSheepWeight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                return false;
            }
        });
        mEtSellSheepNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                return false;
            }
        });
        mTvSellSheepSurplusNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mType == 2) {
                    return true;
                }
                return false;
            }
        });


        mEtSellSheepNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @Override
    protected void initData() {

        hideEnterPickUp();
        hideOutPickUp();

        getSheepEntryInformation();
        if (mType == 2) {
            getSheepOutInformation();
        }
    }


    /**
     * 更新入栏UI
     *
     * @param data
     */
    private void updateEntryUi(HouseKeeperEntryInformationInfo data) {
        if (data == null) {
            return;
        }
        mEtSelectSheepType.setText(data.getVariety());
        mEtSheepPrice.setText(data.getPrice() + "");
        mEtSelectSheepWeight.setText(data.getWeight() + "");
        mEtSelectSheepNumber.setText(data.getAmount() + "");
        mTvSheepTime.setText(FormatDateUtil.longToString(data.getInDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));

        forbidEditEntry();

    }

    private void forbidEditEntry() {
        mBtnBaseMessageSave.setVisibility(View.GONE);
        mEtSelectSheepType.setClickable(false);
        mEtSheepPrice.setClickable(false);
        mTvSheepTime.setClickable(false);
        mEtSelectSheepWeight.setClickable(false);
        mEtSelectSheepNumber.setClickable(false);
        isForbidEdit=true;
    }

    /**
     * 更新出栏UI
     *
     * @param data
     */
    private void updateOutUi(HouseKeeperOutInformationInfo data) {
        if (data == null) {
            return;
        }
//        mEtSelectSheepType.setText(data.getVariety());
//        mEtSheepPrice.setText(data.getPrice() + "");
//        mEtSelectSheepWeight.setText(data.getWeight() + "");
//        mEtSelectSheepNumber.setText(data.getAmount() + "");
//        mTvSheepTime.setText(FormatDateUtil.longToString(data.getInDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));


        mEtSellType.setText(data.getOutType());
        mEtSellSheepPrice.setText(data.getPrice() + "");
        mEtSellWeight.setText(data.getWeight() + "");
        mEtSellSheepWeight.setText(data.getCavityWeight() + "");
        mEtSellSheepNumber.setText(data.getAmount() + "");
        mTvSellSheepSurplusNumber.setText(0 + "");
        mTvSellSheepTime.setText(FormatDateUtil.longToString(data.getOutDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));

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
            /*基础信息保存*/
            case R.id.btn_base_massage_save:
                saveBaseMessageToServer();
                break;
            /*入栏时间*/
            case R.id.tv_sheep_time:
                if (mType == 2) {
                    return;
                }
                showDataPicker(mTvSheepTime);
                break;
            /*选择出栏时间*/
            case R.id.tv_sell_sheep_time:
                if (mType == 2) {
                    return;
                }
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
        String sellType = mEtSellType.getText().toString().trim();
        if (TextUtils.isEmpty(sellType)) {
            return;
        }
        String sellSheepPrice = mEtSellSheepPrice.getText().toString().trim();
        if (TextUtils.isEmpty(sellSheepPrice)) {
            ToastUtil.showToast(mContext, "出栏价格必须填写");
            return;
        }

        String sellWeight = mEtSellWeight.getText().toString().trim();
        String sellSheepWeight = mEtSellSheepWeight.getText().toString().trim();
        String sellSheepNumber = mEtSellSheepNumber.getText().toString().trim();
        if (TextUtils.isEmpty(sellSheepPrice)) {
            ToastUtil.showToast(mContext, "出栏数量必须填写");
            return;
        }
        String sellSheepTime = mTvSellSheepTime.getText().toString().trim();

        HashMap<String, Object> param = new HashMap<>();
        param.put("batchId", mBatchId);
        param.put("outType", sellType);
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
                    ToastUtil.showToast(mContext, "出栏成功，养殖信息请在养殖档案查看！");
                    EventBus.getDefault().post(new MessageEvent().setEventType(MessageEvent.MESSAGE_TYPE_UPDATE_BREEDING_ARCHIVING));
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
     * 获取出栏信息
     * 注意这个只在养殖档案才会有
     */
    private void getSheepOutInformation() {

        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("batchId", mBatchId);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_BASE_MESSAGE_BATCH_OUT_INFO,
                new BaseCallBack<HouseKeeperOutInformationInfoResult>() {
                    @Override
                    protected void OnRequestBefore(Request request) {

                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {

                    }

                    @Override
                    protected void onSuccess(Call call, Response response, HouseKeeperOutInformationInfoResult result) {
                        if (result == null) {
                            return;
                        }
                        if (result.getCode() != 1) {
                            ToastUtil.showToast(mContext, "code:" + result.getCode() + "error:" + result.getMsg());
                        } else {
                            List<HouseKeeperOutInformationInfo> data = result.getData();
                            if (!CommonUtils.isEmpty(data)) {
                                updateOutUi(data.get(0));
                            }
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
     * 获取入栏信息
     */
    private void getSheepEntryInformation() {

        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("batchId", mBatchId);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_BASE_MESSAGE_BATCH_IN_INFO,
                new BaseCallBack<HouseKeeperEntryInformationInfoResult>() {
                    @Override
                    protected void OnRequestBefore(Request request) {

                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {

                    }

                    @Override
                    protected void onSuccess(Call call, Response response, HouseKeeperEntryInformationInfoResult result) {
                        if (result == null) {
                            return;
                        }
                        if (result.getCode() != 1) {
                            ToastUtil.showToast(mContext, "code:" + result.getCode() + "error:" + result.getMsg());
                        } else {
                            HouseKeeperEntryInformationInfo data = result.getData();
                            updateEntryUi(data);
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
        String strTime = textView.getText().toString().trim();
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
     * 建档信息保存
     */
    private void saveBaseMessageToServer() {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        String sheepType = mEtSelectSheepType.getText().toString().trim();
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
        String sheepNumber = mEtSelectSheepNumber.getText().toString().trim();
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
                    ToastUtil.showToast(mContext, "建档信息已保存");
                    forbidEditEntry();
                    mTvSellSheepSurplusNumber.setText(mEtSelectSheepNumber.getText().toString());
                    mEtSellSheepNumber.setText(mEtSelectSheepNumber.getText().toString());
                    SharedPreferencesUtil.getInstance(mContext).putString("sheep_amount_" + mBatchId, mEtSelectSheepNumber.getText().toString());
                    SharedPreferencesUtil.getInstance(mContext).putString(mBatchId + "", mEtSheepPrice.getText().toString().trim());
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
