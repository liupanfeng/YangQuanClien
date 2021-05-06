package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.SharedPreferencesUtil;
import com.meishe.yangquan.utils.UserManager;
import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.layout_splash);

//        boolean isFirstOpen = SharedPreferencesUtil.getInstance(SplashActivity.this).getBoolean(Constants.FIRST_OPEN,false);
//        // 如果是第一次启动，则先进入功能引导页
//        if (!isFirstOpen) {
//            Intent intent = new Intent(this, GuidePageActivity.class);
//            startActivity(intent);
//            overridePendingTransition( R.anim.fade_in, R.anim.fade_out);
//            finish();
//            return;
//        }

//        HttpRequestUtil.getInstance(this).getADFromServer();
        String token=UserManager.getInstance(this).getToken();
        if (!TextUtils.isEmpty(token)){
            HttpRequestUtil.getInstance(this).getUserByToken(token);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.overridePendingTransition( R.anim.right_tran_in, R.anim.right_tran_out);
                finish();
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
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
