package com.meishe.yangquan.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.ImageCodeInfo;
import com.meishe.yangquan.bean.ImageCodeResult;
import com.meishe.yangquan.bean.LoginInfo;
import com.meishe.yangquan.bean.LoginResult;
import com.meishe.yangquan.bean.SendSmsResult;
import com.meishe.yangquan.bean.UpdateDeviceAliasResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.CheckNetwork;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CountDownTimerUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.Logger;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.ChangeIdentityView;
import com.meishe.yangquan.wiget.MaterialProgress;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
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
    private ChangeIdentityView mChangeIdentity;
    private ImageView mIvVerification;
    private String mUuid;
    private EditText mEtImageCode;
    private String mImageCode;

    @Override
    protected int initRootView() {
        return R.layout.login;
    }

    @Override
    public void initView() {
        mMaterialProgress = findViewById(R.id.loading);
        mInputPhoneNum = findViewById(R.id.input_phonenumber);
        mInputCheckCode = findViewById(R.id.input_checkcode);
        mEtImageCode = findViewById(R.id.et_image_code);
        mGetCheckCode = findViewById(R.id.getcheckcode);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegister = findViewById(R.id.btn_register);
        mUserAgreement = findViewById(R.id.user_agreement);
//        mUserPrivacy = findViewById(R.id.privacy);
        mChangeIdentity = findViewById(R.id.civ_change_identity);
        mIvVerification = findViewById(R.id.iv_verification);
//        mTitleBar = findViewById(R.id.titleBar);
    }

    @Override
    public void initData() {
        String token = UserManager.getInstance(mContext).getToken();
        if (!TextUtils.isEmpty(token)){
            AppManager.getInstance().jumpActivity(LoginActivity.this, MainActivity.class);
            finish();
        }else{
            getImageCode();
        }

    }

    @Override
    public void initTitle() {

    }


    @Override
    public void initListener() {
        /**
         * 切换身份
         */
        mChangeIdentity.setOnChangeIdentityListener(new ChangeIdentityView.OnChangeIdentityListener() {
            @Override
            public void onChangeIdentity(int type) {

            }
        });

//        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
//            @Override
//            public void OnBackImageClick() {
//                finish();
//            }
//
//            @Override
//            public void OnCenterTextClick() {
//
//            }
//
//            @Override
//            public void OnRightTextClick() {
//
//            }
//        });

        mGetCheckCode.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mUserAgreement.setOnClickListener(this);
        mIvVerification.setOnClickListener(this);
        mGetCheckCode.setOnClickListener(this);
//        mUserPrivacy.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcheckcode:
                phoneNumber = mInputPhoneNum.getText().toString().trim();
                mImageCode = mEtImageCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtil.showToast(mContext, "手机号不能为空");
                    return;
                }

                if (!Util.isMobileNO(phoneNumber)) {
                    ToastUtil.showToast(mContext, "请输入正确的手机号码");
                    return;
                }

                if (TextUtils.isEmpty(mImageCode)) {
                    ToastUtil.showToast(mContext, "请输入图形码");
                    return;
                }

                if (!CheckNetwork.checkNetWork(mContext)) {
                    //无网络
                    ToastUtil.showToast(mContext, "当前网络不可用，请检查网络设置");
                    return;
                }

                mInputCheckCode.setFocusable(true);
                mInputCheckCode.setFocusableInTouchMode(true);
                mInputCheckCode.requestFocus();
                mGetCheckCode.setEnabled(false);
                getMessageCode();

                break;
            case R.id.btn_login:
                phoneNumber = mInputPhoneNum.getText().toString();
                smsCode = mInputCheckCode.getText().toString().trim();
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
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber", mInputPhoneNum.getText().toString().trim());
                AppManager.getInstance().jumpActivity(LoginActivity.this, RegisterActivity.class, bundle);
                finish();
                break;
            case R.id.iv_verification:
                //获取图片验证码
                getImageCode();
                break;
//            case R.id.privacy:
//                break;
        }
    }

    /**
     * 获取图片验证码
     */
    private void getImageCode() {
        final HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_GET_IMAGE, new BaseCallBack<ImageCodeResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ImageCodeResult imageCodeResult) {
                if (imageCodeResult == null) {
                    Logger.e(TAG, "getImageCode imageCodeResult is null");
                    return;
                }

                if (imageCodeResult.getCode() != 1) {
                    ToastUtil.showToast(mContext, imageCodeResult.getMsg());
                    return;
                }

                ImageCodeInfo imageCodeInfo = imageCodeResult.getData();
                if (imageCodeInfo == null) {
                    return;
                }

                String base64 = imageCodeInfo.getBase64();
                if (TextUtils.isEmpty(base64)) {
                    return;
                }

                Bitmap bitmap = Util.stringToBitmap(base64);
                if (bitmap == null) {
                    return;
                }

                mIvVerification.setImageBitmap(bitmap);
                mUuid = imageCodeInfo.getUuid();
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


    public void updateDeviceAlias() {
        User user = null;
        if (user == null) {
            return;
        }
//        String userId = user.getUserId() + "";
        String registrationID = JPushInterface.getRegistrationID(mContext);
        final HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("registrationId", registrationID);
//        requestParam.put("userId", userId);
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
                    Log.d("LoginActivity", "绑定成功");
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


    private void getMessageCode() {
        mMaterialProgress.show();
        final HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("uuid", mUuid);
        requestParam.put("verifyCode", mImageCode);
        requestParam.put("phone", phoneNumber);
        requestParam.put("mode", "register");
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_SEND_CODE, new BaseCallBack<SendSmsResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMaterialProgress.hide();
                        mInputCheckCode.setFocusable(true);
                        mInputCheckCode.setFocusableInTouchMode(true);
                        mInputCheckCode.requestFocus();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, SendSmsResult result) {
                mMaterialProgress.hide();
                if (result.getStatus() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    mInputCheckCode.setFocusable(true);
                    mInputCheckCode.setFocusableInTouchMode(true);
                    mInputCheckCode.requestFocus();
                    return;
                }
                if (result != null && result.getStatus() == 1) {
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
                        mMaterialProgress.hide();
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

    private void userLogin() {
        mMaterialProgress.show();
        final HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("phone", phoneNumber);
        requestParam.put("verifyCode", smsCode);
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_LOGIN, new BaseCallBack<LoginResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                mMaterialProgress.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, LoginResult result) {
                mMaterialProgress.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                if (result != null && result.getCode() == 1) {
                    LoginInfo loginInfo = result.getData();
                    if (loginInfo != null) {
//                        UserManager.getInstance(mContext).setUser(user);
//                        String token = user.getTokenId();
                        String token = loginInfo.getToken();
                        UserManager.getInstance(mContext).setToken(token);
                        AppManager.getInstance().jumpActivity(LoginActivity.this, MainActivity.class);
//                        updateDeviceAlias();
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
