package com.meishe.yangquan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.HttpUrl;

public class ShowPicActivity extends AppCompatActivity {

    private ImageView iv_show_pic;
    private RequestOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        initView();
        initData();
    }

    private void initView() {
        iv_show_pic=findViewById(R.id.iv_show_pic);
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) iv_show_pic.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
//        int height = display.getHeight();
        params.width=width;
        params.height=width;
    }

    private void initData() {
        Intent intent=getIntent();
        if (intent!=null){
            options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.mipmap.ic_message_list_photo_default);
            String imageUrl=intent.getStringExtra("imageUrl");
            Glide.with(this)
                    .asBitmap()
                    .load(HttpUrl.URL_IMAGE + imageUrl)
                    .apply(options)
                    .into(iv_show_pic);

        }

    }


}
