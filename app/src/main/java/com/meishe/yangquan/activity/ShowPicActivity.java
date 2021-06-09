package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.view.RoundAngleImageView;
import com.meishe.yangquan.wiget.CustomToolbar;

public class ShowPicActivity extends BaseActivity {

    private RoundAngleImageView iv_show_pic;
    protected CustomToolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        initView();
        initData();
        initTitle();
    }

    @Override
    protected int initRootView() {
        return R.layout.activity_show_pic;
    }

    public void initTitle() {
//        mToolbar.setMyTitle("查看大图");
//        mToolbar.setMyTitleVisible(View.VISIBLE);
//        mToolbar.setLeftButtonVisible(View.VISIBLE);
//        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
        mTvTitle.setText("查看大图");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_show_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        iv_show_pic=findViewById(R.id.iv_show_pic);
        mIvBack = findViewById(R.id.iv_back);

//        mToolbar = findViewById(R.id.toolbar);
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) iv_show_pic.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
//        int height = display.getHeight();
        params.width=width;
        params.height=width;
    }

    public void initData() {
        Intent intent=getIntent();
        if (intent!=null){
            String imageUrl=intent.getStringExtra("imageUrl");
            GlideUtil.getInstance().loadUrl(imageUrl,iv_show_pic);

        }

    }

    @Override
    public void onClick(View view) {

    }



}
