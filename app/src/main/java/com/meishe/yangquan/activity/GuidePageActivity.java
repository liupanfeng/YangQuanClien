package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.fragment.SlideFragment;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.SharedPreferencesUtil;

/**
 * 客户端-引导页面
 */
public class GuidePageActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SlideFragment.newInstance(R.layout.layout_01));
        addSlide(SlideFragment.newInstance(R.layout.layout_02));
        addSlide(SlideFragment.newInstance(R.layout.layout_03));
        addSlide(SlideFragment.newInstance(R.layout.layout_04));
        setSeparatorColor(getResources().getColor(R.color.transparent));
        setVibrateIntensity(30);
        setDoneText("完成");
        setSkipText("跳过");
    }



    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(GuidePageActivity.this, LoginActivity.class);
        startActivity(intent);
        SharedPreferencesUtil.getInstance(App.getContext()).putBoolean(Constants.FIRST_OPEN, true);
        finish();
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //当执行完成动作时触发
        Intent intent = new Intent(GuidePageActivity.this, LoginActivity.class);
        startActivity(intent);
        SharedPreferencesUtil.getInstance(App.getContext()).putBoolean(Constants.FIRST_OPEN, true);
        finish();
    }



    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        //当执行变化动作时触发
    }




}