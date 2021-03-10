package com.meishe.yangquan.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.wiget.pay.PasswordEditText;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 86188
 * 我的-支付密码
 */
public class MinePayPasswordActivity extends BaseActivity implements PasswordEditText.PasswordFullListener {

    private TextView mTvTitle;
    private ImageView mIvBack;

    private LinearLayout mKeyBoardView;
    private PasswordEditText mPasswordEditText;
    private View ll_pay_password_view;
    /*设置密码描述*/
    private TextView tv_pay_feed_gold;

    private String mFirstInputPassWorld;

    private String mSecondInputPassWorld;

    private int mInputIndex;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_pay_password;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        mKeyBoardView = findViewById(R.id.keyboard);
        mPasswordEditText = findViewById(R.id.passwordEdt);
        ll_pay_password_view = findViewById(R.id.ll_pay_password_view);
        tv_pay_feed_gold = findViewById(R.id.tv_pay_feed_gold);

    }

    @Override
    public void initData() {
        mFirstInputPassWorld = "";
        mSecondInputPassWorld = "";
        mInputIndex = 0;
        tv_pay_feed_gold.setText("请设置支付密码，用于饲料金支付");
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("支付密码");
    }

    @Override
    public void initListener() {
        mPasswordEditText.setPasswordFullListener(this);
        setItemClickListener(mKeyBoardView);

        mIvBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeViewVisible(ll_pay_password_view, true);
            }
        });

    }


    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            String number = ((TextView) v).getText().toString().trim();
            mPasswordEditText.addPassword(number);
        }
        if (v instanceof ImageView) {
            mPasswordEditText.deletePassword();
        }
    }

    /**
     * 给每一个自定义数字键盘上的View 设置点击事件
     *
     * @param view
     */
    private void setItemClickListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                //不断的给里面所有的View设置setOnClickListener
                View childView = ((ViewGroup) view).getChildAt(i);
                setItemClickListener(childView);
            }
        } else {
            view.setOnClickListener(this);
        }
    }


    @Override
    public void passwordFull(String password) {
        mInputIndex++;
        if (mInputIndex == 1) {
            mFirstInputPassWorld = password;
            tv_pay_feed_gold.setText("请再次输入以确认");
        } else if (mInputIndex == 2) {
            mSecondInputPassWorld = password;
            if (mSecondInputPassWorld.equals(mFirstInputPassWorld)) {
                setUserPassword(mSecondInputPassWorld);
                changeViewVisible(ll_pay_password_view, false);
            } else {
                mInputIndex = 1;
                tv_pay_feed_gold.setText("输入的密码不一致，请重新输入！");
            }
        }
    }

    /**
     * 设置用户支付密码
     *
     * @param passWorld
     */
    private void setUserPassword(String passWorld) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("paymentCode", passWorld);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_SETTING_PASSWORD, new BaseCallBack<ServerResult>() {
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
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast("支付密码设置成功");
                    finish();
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

    private void changeViewVisible(View view, boolean isShow) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
//        float translationY = view.getTranslationY();
//        int height = view.getHeight();
//        Log.e("lpf", "translationY:" + translationY + "------height:" + height);
//        if (isShow) {
//            height = -height/2;
//        }
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", translationY, height, translationY + height);
//        objectAnimator.setDuration(500);
//        objectAnimator.start();
    }
}