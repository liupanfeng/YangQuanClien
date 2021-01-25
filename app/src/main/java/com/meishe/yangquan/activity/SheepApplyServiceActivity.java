package com.meishe.yangquan.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import java.io.IOException;
import java.util.HashMap;

import cn.jiguang.net.HttpRequest;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.utils.Constants.phoneNumber;

/**
 * 羊管家-申请服务
 */
public class SheepApplyServiceActivity extends BaseActivity {


    private Button mBtnCommit;
    /*申请人姓名*/
    private EditText mEtInputApplyName;
    /*借款人的电话*/
    private EditText mEtInputApplyPhoneNumber;
    /*输入身份证号*/
    private EditText mEtInputApplyIdCardNumber;
    /*输入羊场地址*/
    private EditText mEtInputSheepAddress;
    /*输入养殖年限*/
    private EditText mEtInputDoSheepYear;

    /*养殖数量*/
    private EditText mEtInputSheepNumber;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_apply_service;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnCommit = findViewById(R.id.btn_commit);

        mEtInputApplyName = findViewById(R.id.et_input_apply_name);
        mEtInputApplyPhoneNumber = findViewById(R.id.et_input_apply_phone_number);
        mEtInputApplyIdCardNumber = findViewById(R.id.et_input_apply_id_card_number);
        mEtInputSheepAddress = findViewById(R.id.et_input_sheep_address);
        mEtInputDoSheepYear = findViewById(R.id.et_input_do_sheep_year);
        mEtInputSheepNumber = findViewById(R.id.et_input_sheep_number);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("申请服务");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyServiceFromServer();
            }
        });
    }

    /**
     * 申请服务
     */
    private void applyServiceFromServer() {
        String token= UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)){
            return;
        }

        String applyName = mEtInputApplyName.getText().toString().trim();
        if (TextUtils.isEmpty(applyName)){
            ToastUtil.showToast(mContext,"请输入申请人名称");
            return;
        }

        String applyPhoneNumber = mEtInputApplyPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(applyPhoneNumber)){
            ToastUtil.showToast(mContext,"请输入申请人电话");
            return;
        }

        String idCardNumber = mEtInputApplyIdCardNumber.getText().toString().trim();
        if (TextUtils.isEmpty(idCardNumber)){
            ToastUtil.showToast(mContext,"请输入申请人身份证号");
            return;
        }

        String sheepAddress = mEtInputSheepAddress.getText().toString().trim();
        if (TextUtils.isEmpty(sheepAddress)){
            ToastUtil.showToast(mContext,"请输入羊场地址");
            return;
        }

        String doSheepYear = mEtInputDoSheepYear.getText().toString().trim();
        if (TextUtils.isEmpty(doSheepYear)){
            ToastUtil.showToast(mContext,"请输入养殖年限");
            return;
        }
        String sheepNumber = mEtInputSheepNumber.getText().toString().trim();
        if (TextUtils.isEmpty(sheepNumber)){
            ToastUtil.showToast(mContext,"请输入养殖数量");
            return;
        }


        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("name", applyName);
        requestParam.put("phone", applyPhoneNumber);
        requestParam.put("idCardNum", idCardNumber);
        requestParam.put("culturalAddress", sheepAddress);
        requestParam.put("culturalAge", doSheepYear);
        requestParam.put("culturalAge", doSheepYear);
        requestParam.put("currentCulturalQuantity", sheepNumber);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_APPLY_SERVICE, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                ToastUtil.showToast(mContext,"error:"+e.toString());
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                if (serverResult!=null){
                    int code = serverResult.getCode();
                    if (code==1){
                        AppManager.getInstance().jumpActivity(SheepApplyServiceActivity.this,SheepApplyServiceResultActivity.class);
                    }else{
                        ToastUtil.showToast(mContext,serverResult.getMsg());
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
        },requestParam,token);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

    }
}
