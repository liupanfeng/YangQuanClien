package com.meishe.yangquan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CaptureAndEditUtil;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.view.CropCircleView;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.io.File;
import java.io.FileOutputStream;

import static com.meishe.yangquan.utils.CaptureAndEditUtil.REQUEST_FAILED;

public class ClipCircleImageActivity extends AppCompatActivity {

    private static final String TAG = ClipCircleImageActivity.class.getSimpleName();
    private CropCircleView mClipImage;
    private TextView mCancel;
    private TextView mComplete;

    public static final String IMAGE_PATH_ORIGINAL = "original_image";

    private static final int COVER_SUCCESS=100;
    private static final int COVER_ERROR=200;

    public  Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mloadingView.hide();
            switch (msg.what){
                case COVER_SUCCESS:
                    if (msg.obj!=null){
                        String path= (String) msg.obj;
                        Intent intent = getIntent();
                        intent.putExtra("cover_path",path);
                        setResult(RESULT_OK, intent);
                    }
                    finish();
                    break;
                case COVER_ERROR:
                    Intent intent = getIntent();
                    setResult(REQUEST_FAILED, intent);
                    finish();
                    break;
            }
        }
    };
    private MaterialProgress mloadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_circle_image);

        initView();
        initData();
        initListener();

    }

    private void initListener() {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });

        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mloadingView.show();
                new Thread(){
                    @Override
                    public void run() {
                        cover();
                    }
                }.start();
            }
        });
    }

    private void cover() {
        String coverImageDir = PathUtils.getPersonalCoverImageDirectory();
        if(coverImageDir == null){
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            File clipFile = new File(coverImageDir, PathUtils.getTempCoverImageName(ClipCircleImageActivity.this));
            fileOutputStream = new FileOutputStream(clipFile.getAbsolutePath());
            Bitmap bitmap = mClipImage.clipBitmap();
            if(bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            fileOutputStream.flush();
            Message msg=mHandler.obtainMessage();
            msg.obj=clipFile.getAbsolutePath();
            msg.what=COVER_SUCCESS;
            mHandler.sendMessage(msg);
        } catch (Exception e) {
            Log.e(TAG,"fail to initListener" ,e);
            mHandler.sendEmptyMessage(COVER_ERROR);
        } finally {
            CaptureAndEditUtil.close(fileOutputStream);
        }
    }

    private void initData() {
        Intent intent = getIntent();
        String originalImgPath = intent.getStringExtra(IMAGE_PATH_ORIGINAL);
        //Uri url = Uri.parse(originalImgPath);
        mClipImage.setBitmapForWidth(originalImgPath,1080);
    }

    private void initView() {
        mClipImage = (CropCircleView) findViewById(R.id.clip_image);
        mloadingView = (MaterialProgress) findViewById(R.id.loading);
        mCancel = (TextView) findViewById(R.id.cancel);
        mComplete = (TextView) findViewById(R.id.complete);
    }


    @Override
    public void onBackPressed() {
        finishActivity();
    }



    private void finishActivity(){
        if (!isFinishing()){
            AppManager.getInstance().finishActivity();
        }
    }
}
