package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.SendSmsResult;
import com.meishe.yangquan.bean.UpdateDeviceAliasResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.CheckNetwork;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.CountDownTimerUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.UserType;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.wiget.MaterialProgress;
import com.meishe.yangquan.wiget.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class RegisterActivity extends BaseActivity  {
    private String TAG = RegisterActivity.class.getSimpleName();


    private Spinner mSpinnerSimple;
    private Button mBtnRegister;

    private EditText mInputPhoneNum;
    private EditText mInputCheckCode;
    private TextView mGetCheckCode;

    private String[] mUserTypeArray;

    private String mSelectUser;
    private int mUserType = 0;

    private String mInputPhoneNumber;
    private MaterialProgress mLoading;
    private String phoneNumber;
    private String smsCode;


    @Override
    protected int initRootView() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titleBar);
        mSpinnerSimple = findViewById(R.id.spinner_simple);
        mBtnRegister = findViewById(R.id.btn_register);

        mInputPhoneNum = findViewById(R.id.input_phonenumber);
        mInputCheckCode = findViewById(R.id.input_checkcode);
        mGetCheckCode = findViewById(R.id.getcheckcode);
        mLoading = findViewById(R.id.loading);

    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        if (intent!=null){
            Bundle bundle=intent.getExtras();
            if (bundle!=null){
                String phoneNumber=bundle.getString("phoneNumber");
                mInputPhoneNum.setText(phoneNumber);
            }
        }
        mTitleBar.setTextCenter("注册");
        mTitleBar.setBackImageVisible(View.VISIBLE);
        mUserTypeArray = getResources().getStringArray(R.array.user_type_spinner_values);
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {

        mGetCheckCode.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

        mSpinnerSimple.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectUser = mUserTypeArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void OnBackImageClick() {
                finish();
            }

            @Override
            public void OnCenterTextClick() {

            }

            @Override
            public void OnRightTextClick() {

            }
        });

    }

    private void userRegister() {
        if (Util.isFastDoubleClick()) {
            return;//禁止双击
        }

        mInputPhoneNumber = mInputPhoneNum.getText().toString();
        smsCode=mInputCheckCode.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode)) {
            ToastUtil.showToast(mContext, "请输入短信验证码");
            return;
        }

        if (TextUtils.isEmpty(mInputPhoneNumber)) {
            ToastUtil.showToast(mContext, "请输入手机号再进行注册");
            return;
        }


        if (!Util.isMobileNO(mInputPhoneNumber)) {
            ToastUtil.showToast(mContext, "请输入正确的手机号码");
            return;
        }

        if (!TextUtils.isEmpty(mSelectUser) && !"请选择身份".equals(mSelectUser)) {
            mUserType = UserType.userType.get(mSelectUser);
        }

        if (mUserType == 0) {
            ToastUtil.showToast(mContext, "请选择身份");
            return;
        }

        if (!CheckNetwork.checkNetWork(mContext)) {
            //无网络
            ToastUtil.showToast(mContext, "当前网络不可用，请检查网络设置");
            return;
        }


        registerFromServer();
    }


    private void registerFromServer() {
        mLoading.show();
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put(Constants.phoneNumber, mInputPhoneNumber);
        requestParam.put(Constants.userType, mUserType);
        requestParam.put("smsCode", smsCode);
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_REGISTER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.hide();
                        mInputCheckCode.setFocusable(true);
                        mInputCheckCode.setFocusableInTouchMode(true);
                        mInputCheckCode.requestFocus();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                mLoading.hide();
                if (result != null) {
                    int code=result.getCode();
                    if (code!=200){
                        ToastUtil.showToast(mContext,result.getMsg());
                        return;
                    }
                    if (code==200){
                        User user=null;
                        if (user!=null){
                            String token = user.getTokenId();
                            UserManager.getInstance(mContext).setToken(token);
//                            UserManager.getInstance(mContext).setUser(user);
                            AppManager.getInstance().jumpActivity(RegisterActivity.this, MainActivity.class);
                            updateDeviceAlias();
                            finish();
                            ToastUtil.showToast(mContext,"注册成功并自动登录");
                        }

                        return;
                    }

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
                        mLoading.hide();
                        mInputCheckCode.setFocusable(true);
                        mInputCheckCode.setFocusableInTouchMode(true);
                        mInputCheckCode.requestFocus();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }


    public void updateDeviceAlias(){
        User user=null;
        if(user==null){
            return;
        }
        String userId=user.getUserId()+"";
        String registrationID= JPushInterface.getRegistrationID(mContext);
        final HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("registrationId", registrationID);
        requestParam.put("userId", userId);
        OkHttpManager.getInstance().postRequest(HttpUrl.PUSH_UPDATE_DEVICE_ALIAS, new BaseCallBack<UpdateDeviceAliasResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, UpdateDeviceAliasResult result) {
                if (result.getStatus() != 200) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                if (result != null && result.getStatus() == 200) {
                    Log.d("LoginActivity","绑定成功");
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
        }, requestParam);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcheckcode:
                phoneNumber=mInputPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtil.showToast(mContext, "手机号不能为空");
                    return;
                }

                if (!Util.isMobileNO(phoneNumber)) {
                    ToastUtil.showToast(mContext, "请输入正确的手机号码");
                    return;
                }

                if (!CheckNetwork.checkNetWork(mContext)) {
                    //无网络
                    ToastUtil.showToast(mContext, "当前网络不可用，请检查网络设置");
                    return;
                }

//                if (TextUtils.isEmpty(inputCheckimage.getText().toString().trim())) {
//                    ToastUtil.showToast(mContext, "图片验证码不能为空");
//                    return;
//                }

                mInputCheckCode.setFocusable(true);
                mInputCheckCode.setFocusableInTouchMode(true);
                mInputCheckCode.requestFocus();
                mGetCheckCode.setEnabled(false);
                getMessageCode();

                break;
            case R.id.btn_register:
                userRegister();
                break;
        }
    }


    private void getMessageCode() {
        mLoading.show();
        final HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("phoneNumber", phoneNumber);
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_SEND_CODE, new BaseCallBack<SendSmsResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.hide();
                        mInputCheckCode.setFocusable(true);
                        mInputCheckCode.setFocusableInTouchMode(true);
                        mInputCheckCode.requestFocus();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, SendSmsResult result) {
                mLoading.hide();
                if (result.getCode() != 200) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    mInputCheckCode.setFocusable(true);
                    mInputCheckCode.setFocusableInTouchMode(true);
                    mInputCheckCode.requestFocus();
                    return;
                }
                if (result != null && result.getCode() == 200) {
                    ToastUtil.showToast(mContext, "验证码已发送");
                    CountDownTimerUtils countDownTimer = new CountDownTimerUtils(mGetCheckCode, 60000, 1000);
                    countDownTimer.start();
                    mInputCheckCode.setFocusable(true);
                    mInputCheckCode.setFocusableInTouchMode(true);
                    mInputCheckCode.requestFocus();
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
                        mLoading.hide();
                        mInputCheckCode.setFocusable(true);
                        mInputCheckCode.setFocusableInTouchMode(true);
                        mInputCheckCode.requestFocus();
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }



    @Override
    public void release() {

    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
