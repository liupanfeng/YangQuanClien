package com.meishe.yangquan.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.CheckNetwork;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CountDownTimerUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.wiget.MaterialProgress;
import com.meishe.yangquan.wiget.TitleBar;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private String TAG = LoginActivity.class.getSimpleName();

    private MaterialProgress mMaterialProgress;
    private EditText mInputPhoneNum;
    private EditText mInputCheckCode;
    private TextView mGetCheckCode;
    private Button mBtnLogin;
    private Button mBtnRegister;

    private TextView mUserAgreement;     //用户协议
    private TextView mUserPrivacy;      //隐私协议

    private String phoneNumber;
    private String smsCode;

    @Override
    protected int initRootView() {
        return R.layout.login;
    }

    @Override
    public void initView() {
        mMaterialProgress = findViewById(R.id.loading);
        mInputPhoneNum = findViewById(R.id.input_phonenumber);
        mInputCheckCode = findViewById(R.id.input_checkcode);
        mGetCheckCode = findViewById(R.id.getcheckcode);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegister = findViewById(R.id.btn_register);
        mUserAgreement = findViewById(R.id.user_agreement);
        mUserPrivacy = findViewById(R.id.privacy);
        mTitleBar = findViewById(R.id.titleBar);
    }

    @Override
    public void initData() {
        mTitleBar.setTextCenter(R.string.str_login_title);
        mTitleBar.setBackImageVisible(View.VISIBLE);
    }

    @Override
    public void initTitle() {

    }


    @Override
    public void initListener() {
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
        mGetCheckCode.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mUserAgreement.setOnClickListener(this);
        mUserPrivacy.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcheckcode:
//                CountDownTimerUtils countDownTimer = new CountDownTimerUtils(mGetCheckCode, 60000, 1000);
//                countDownTimer.start();
//                mInputCheckCode.setFocusable(true);
//                mInputCheckCode.setFocusableInTouchMode(true);
//                mInputCheckCode.requestFocus();
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
            case R.id.btn_login:
                phoneNumber = mInputPhoneNum.getText().toString();
                smsCode=mInputCheckCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtil.showToast(mContext, "请输入手机号再进行登录");
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    ToastUtil.showToast(mContext, "请输入短信验证码");
                    return;
                }

                if (!Util.isMobileNO(phoneNumber)) {
                    ToastUtil.showToast(mContext, "请输入正确的手机号码");
                    return;
                }
                userLogin();
                break;
            case R.id.btn_register:
                AppManager.getInstance().jumpActivity(LoginActivity.this, RegisterActivity.class);
//                userRegister();
                break;
            case R.id.user_agreement:
                break;
            case R.id.privacy:
                break;
        }
    }

    private void getMessageCode() {
        mMaterialProgress.show();
        final HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("phoneNumber", phoneNumber);
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_SEND_CODE, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                mMaterialProgress.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                mMaterialProgress.hide();
                if (result.getStatus() != 200) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                if (result != null && result.getStatus() == 200) {
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
                mMaterialProgress.hide();
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }

    private void userLogin() {
        mMaterialProgress.show();
        final HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("phoneNum", phoneNumber);
        requestParam.put("smsCode", smsCode);
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_LOGIN, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                mMaterialProgress.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                mMaterialProgress.hide();
                if (result.getStatus() != 200) {
                    ToastUtil.showToast(mContext, "请先注册再登录");
                    return;
                }
                if (result != null && result.getStatus() == 200) {
                    User user = result.getData();
                    if (user != null) {
                        UserManager.getInstance(mContext).setUser(user);
                        String token = user.getTokenId();
                        UserManager.getInstance(mContext).setToken(token);
                        AppManager.getInstance().jumpActivity(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.finish();
                        ToastUtil.showToast(mContext, "登录成功");
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                mMaterialProgress.hide();
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }


    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess(int type, Object object) {

    }

    @Override
    public void onError(Object obj) {

    }

    @Override
    public void onError(int type, Object obj) {

    }
}
