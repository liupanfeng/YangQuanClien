package com.meishe.yangquan.activity;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.meishe.yangquan.R;
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
import com.meishe.yangquan.utils.UserType;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.wiget.TitleBar;

import java.io.IOException;
import java.util.HashMap;

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

    }

    @Override
    public void initData() {
        mTitleBar.setTextCenter("注册");
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
        if (TextUtils.isEmpty(mInputPhoneNumber)) {
            ToastUtil.showToast(mContext, "请输入手机号再进行登录");
            return;
        }

        if (mInputPhoneNumber.length() != 11) {
            ToastUtil.showToast(mContext, "请输入正确的手机号");
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

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put(Constants.phoneNumber, mInputPhoneNumber);
        requestParam.put(Constants.userType, mUserType);
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_REGISTER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null) {
                    int code=result.getStatus();
                    if (code==200){
                        ToastUtil.showToast(mContext,"注册成功请登录");
                        return;
                    }
                    if (code==401){
                        ToastUtil.showToast(mContext,"改手机号已经注册，请登录");
                        return;
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
        }, requestParam);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcheckcode:
                CountDownTimerUtils countDownTimer = new CountDownTimerUtils(mGetCheckCode, 60000, 1000);
                countDownTimer.start();
                mInputCheckCode.setFocusable(true);
                mInputCheckCode.setFocusableInTouchMode(true);
                mInputCheckCode.requestFocus();

                break;
            case R.id.btn_register:
                userRegister();
                break;
        }
    }

    @Override
    public void release() {

    }


    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(Object obj) {

    }
}
