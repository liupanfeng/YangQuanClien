package com.meishe.yangquan.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.MessageResult;
import com.meishe.yangquan.bean.MsgResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.CropCircleView;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.meishe.yangquan.wiget.MaterialProgress;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 信息发布
 */
public class MessagePublishActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    private RadioGroup mRadioGroup;
    private int mCurrentPosition;
    private TextView mTvType;

    private User mUser;
    private EditText mEtInput;
    private ImageView mIvSelectIcon;

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;
    private File tempFile;
    private ImageView mIvShowIcon;

    private int mSheepType = 0;
    private Bitmap showBitmap;
    private MaterialProgress mp_loading;


    @Override
    protected int initRootView() {
        return R.layout.activity_message_publish;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRadioGroup = findViewById(R.id.rg_sheep_type);
        mTvType = findViewById(R.id.tv_type);
        mEtInput = findViewById(R.id.et_input_publish_content);
        mIvSelectIcon = findViewById(R.id.iv_select_icon);
        mIvShowIcon = findViewById(R.id.iv_show_icon);
        mp_loading = findViewById(R.id.mp_loading);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            mCurrentPosition = bundle.getInt("currentPosition");
        }

        mUser = UserManager.getInstance(this).getUser();
        if (mUser != null) {
            int userType = mUser.getUserType();
            if (userType == 1 || userType == 2 || userType == 10) {
                mTvType.setVisibility(View.VISIBLE);
                mRadioGroup.setVisibility(View.VISIBLE);
            } else {
                mTvType.setVisibility(View.GONE);
                mRadioGroup.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initTitle() {
        mToolbar.setMyTitle("信息发布");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setLeftButtonVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());

        mToolbar.setRightButtonVisible(View.VISIBLE);
        mToolbar.setOnRightButtonClickListener(new OnRightButtonListener());
        mToolbar.setRightButtonText("发布");
        mToolbar.setRightButtonBackground(getResources().getColor(R.color.mainColor));
    }

    @Override
    public void initListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
        mIvSelectIcon.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_select_icon:
                showPictureSelectItem();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = findViewById(checkedId);
        CharSequence text = rb.getText();
        if ("成品羊".equals(text)) {
            mSheepType = 1;
        } else if ("羊崽儿".equals(text)) {
            mSheepType = 2;
        }
    }


    private void addMessage(String userToken, String sheepType, String msgContent, String iconBase64) {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("userToken",userToken);
        formBody.add("sheepType",sheepType);
        formBody.add("msgContent",msgContent);
        formBody.add("iconBase64",iconBase64);
        Request request = new Request.Builder()//创建Request 对象。
                .url(HttpUrl.MESSAGE_ADD)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, e.getMessage());
                        mp_loading.hide();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response != null&&response.code()==200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(mContext, "发布成功");
                            mp_loading.hide();
                            finish();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(mContext, response.message());
                            mp_loading.hide();
                        }
                    });
                }
            }
        });
    }


    /**
     * 发临时测试
     */
    public void testMessage(String yqtoken, String sheepType, String msgContent, String iconBase64) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("yqtoken", yqtoken);
        requestParam.put("sheepType", sheepType);
        requestParam.put("msgContent", msgContent);
        requestParam.put("iconBase64", iconBase64);
        OkHttpManager.getInstance().postRequest(HttpUrl.MESSAGE_TEST, new BaseCallBack<MsgResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mp_loading.hide();
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, MsgResult result) {
                if (result != null&&result.getStatus()==200) {
                    ToastUtil.showToast(mContext, "发布成功");
                    mp_loading.hide();
                    finish();
                }else{
                    ToastUtil.showToast(mContext, result.getMsg());
                    mp_loading.hide();
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
                        mp_loading.hide();
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }


    /**
     * 发布消息
     */
    public void publishMessage(String yqtoken, String sheepType, String msgContent, String iconBase64) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("yqtoken", yqtoken);
        requestParam.put("sheepType", sheepType);
        requestParam.put("msgContent", msgContent);
        requestParam.put("iconBase64", iconBase64);
        OkHttpManager.getInstance().postRequest(HttpUrl.MESSAGE_ADD, new BaseCallBack<MsgResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mp_loading.hide();
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, MsgResult result) {
                if (result != null&&result.getStatus()==200) {
                    ToastUtil.showToast(mContext, "发布成功");
                    mp_loading.hide();
                    finish();
                }else{
                    ToastUtil.showToast(mContext, result.getMsg());
                    mp_loading.hide();
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
                        mp_loading.hide();
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });

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

    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {
        @Override
        public void onClick() {
            finish();
        }
    }

    private class OnRightButtonListener implements CustomToolbar.OnRightButtonClickListener {
        @Override
        public void onClick() {
            String content = mEtInput.getText().toString().trim();
            if (showBitmap == null) {
                ToastUtil.showToast(mContext, "请选择图片！");
                return;
            }
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showToast(mContext, "请输入内容！");
                return;
            }
            mp_loading.show();
//            publishMessage(mUser.getTokenId(),String.valueOf(mSheepType),content, Util.bitmaptoString(showBitmap));
            publishMessage(mUser.getTokenId(),String.valueOf(mSheepType),content, Util.bitmaptoString(showBitmap));

        }
    }


    private void showPictureSelectItem() {
        new BottomMenuFragment(MessagePublishActivity.this)
                .addMenuItems(new MenuItem("拍照"))
                .addMenuItems(new MenuItem("相册"))
                .setOnItemClickListener(new BottomMenuFragment.OnItemClickListener() {
                    @Override
                    public void onItemClick(TextView menu_item, int position) {
                        if (menu_item.getText().equals("拍照") && position == 0) {
                            checkPremission();//拍照
                        } else {
                            checkReadPermission();
                        }
                    }
                })
                .show();
    }

    private void checkPremission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)) {
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    getPicFromCamera();
                } else {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
                }
            } else {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            }
        } else {
            getPicFromCamera();
        }
    }

    private void checkReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                setLocalPhoto();
            } else {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, ALBUM_REQUEST_CODE);
            }
        } else {
            setLocalPhoto();
        }
    }


    /**
     * 从相机获取图片
     */
    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 相册
     */
    private void setLocalPhoto() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (tempFile != null) {
                    Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(tempFile.getAbsolutePath(), 1080);
                    mIvSelectIcon.setVisibility(View.GONE);
                    Matrix matrix = new Matrix();
                    matrix.setScale(0.2f, 0.2f);
                    showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
                            tmpBitmap.getHeight(), matrix, true);


                    mIvShowIcon.setImageBitmap(showBitmap);
                }
                break;

            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    String imagePath = null;
                    if (intent == null)
                        return;
                    Uri uri = intent.getData();
                    if (uri == null)
                        return;
                    if (DocumentsContract.isDocumentUri(this, uri)) {
                        //如果是document类型的Uri,则通过document id处理
                        String docId = DocumentsContract.getDocumentId(uri);
                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                            String id = docId.split(":")[1];//解析出数字格式的id
                            String selection = MediaStore.Images.Media._ID + "=" + id;
                            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                            imagePath = getImagePath(contentUri, null);
                        }
                    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                        //如果是content类型的URI，则使用普通方式处理
                        imagePath = getImagePath(uri, null);
                    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                        //如果是file类型的Uri,直接获取图片路径即可
                        imagePath = uri.getPath();
                    }
                    if (imagePath != null) {
                        tempFile = new File(imagePath);
                        Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(tempFile.getAbsolutePath(), 1080);
                        Matrix matrix = new Matrix();
                        matrix.setScale(0.2f, 0.2f);
                        showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
                                tmpBitmap.getHeight(), matrix, true);
                        mIvSelectIcon.setVisibility(View.GONE);
                        mIvShowIcon.setImageBitmap(showBitmap);
                    }
                }
                break;


            case CROP_SMALL_PICTURE:  //调用剪裁后返回
                if (resultCode == RESULT_OK) {
                    if (intent == null)
                        return;
                    File tempCoverFile = new File(PathUtils.getPersonalCoverImageDirectory(), PathUtils.getTempCoverImageName(mContext));
                    Bitmap bitMap = BitmapFactory.decodeFile(tempCoverFile.getAbsolutePath());
//                    if (bitMap != null) {
//                        mIvPhoto.setImageBitmap(bitMap);
//                    }
//                    showLoading();
//                    uploadUserInfo("icon", Util.bitmaptoString(bitMap));
                }
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (columnIndex >= 0) {
                    path = cursor.getString(columnIndex);
                }
            }
            cursor.close();
        }
        return path;
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
