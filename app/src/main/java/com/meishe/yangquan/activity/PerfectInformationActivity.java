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
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.SelectCaptureTypeView;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.CircleImageView;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.meishe.yangquan.wiget.IosDialog;
import com.meishe.yangquan.wiget.MaterialProgress;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 完善资料
 */
public class PerfectInformationActivity extends BaseActivity {

    private LinearLayout mllPhoto;
    private CircleImageView mIvPhoto;

    private File tempFile;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;
    public static final String IMAGE_PATH_ORIGINAL = "original_image";
    private User mUser;
    private MaterialProgress mMpLoading;
    private TextView mTvChangePhoto;
    private RelativeLayout mRlNickName;
    private TextView mTvNickNameContent;                                        //显示签名
    private IosDialog mDialog;
    private String mNickname;
    private TextView mTvPhoneNumber;
    private RelativeLayout mRlPhoneNumber;
    private RelativeLayout rl_autograph;
    private TextView tv_autograph_content;
    private RelativeLayout rl_sex;
    private TextView tv_sex_content;
    private LinearLayout ll_sex;
    private String[] mUserSexArray;
    private Spinner mSpinnerSimple;
    private String mUserSex;
    private String mAutograph;
    private ImageView iv_business_license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initRootView() {
        return R.layout.activity_perfect_infomation;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mIvPhoto = findViewById(R.id.iv_photo);
        mTvChangePhoto = findViewById(R.id.tv_change_photo);
        mRlNickName = findViewById(R.id.rl_nickname);
        mTvPhoneNumber = findViewById(R.id.tv_phone_number_content);
        mMpLoading = findViewById(R.id.loading);
        mTvNickNameContent = findViewById(R.id.tv_nickname_content);
        mRlPhoneNumber = findViewById(R.id.rl_phone_number);
        rl_autograph = findViewById(R.id.rl_autograph);
        tv_autograph_content = findViewById(R.id.tv_autograph_content);
        rl_sex = findViewById(R.id.rl_sex);
        ll_sex = findViewById(R.id.ll_sex);
        tv_sex_content = findViewById(R.id.tv_sex_content);
        mSpinnerSimple = findViewById(R.id.spinner_simple);
        iv_business_license = findViewById(R.id.iv_business_license);

    }

    @Override
    public void initData() {
        mUser = null;
        mUserSexArray = getResources().getStringArray(R.array.user_sex_spinner_values);
        updateUI();

    }

    private void updateUI() {
        if (mUser != null) {
            mTvPhoneNumber.setText(mUser.getPhoneNumber());
            mTvNickNameContent.setText(mUser.getNickname());
            String autograph = mUser.getAutograph();
            Integer sex = mUser.getSex();
            if(TextUtils.isEmpty(autograph)){
                tv_autograph_content.setText("暂无签名，请添加");
            }else{
                tv_autograph_content.setText(Util.decodeString(autograph));
            }

            if (sex==null){
                tv_sex_content.setText("");
            }else{
                switch (sex){
                    case 1:
                        tv_sex_content.setText("男");
                        break;
                    case 2:
                        tv_sex_content.setText("女");
                        break;
                    case 3:
                        tv_sex_content.setText("人妖");
                        break;
                }
            }

            String photoUrl = mUser.getPhotoUrl();
            if (!TextUtils.isEmpty(photoUrl)) {
                GlideUtil.getInstance().loadPhotoUrl(HttpUrl.URL_IMAGE + photoUrl,mIvPhoto);
            }else{
                GlideUtil.getInstance().loadPhotoUrl("",mIvPhoto);
            }
        }
    }

    @Override
    public void initTitle() {
        mToolbar.setMyTitle("完善资料");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
        mToolbar.setLeftButtonVisible(View.VISIBLE);
    }

    @Override
    public void initListener() {
        mTvChangePhoto.setOnClickListener(this);
        mIvPhoto.setOnClickListener(this);
        mRlNickName.setOnClickListener(this);
        mRlPhoneNumber.setOnClickListener(this);
        rl_autograph.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        iv_business_license.setOnClickListener(this);
        mSpinnerSimple.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUserSex = mUserSexArray[position];
                switch (mUserSex){
                    case "男":
                        uploadUserInfo("sex","1");
                        ll_sex.setVisibility(View.GONE);
                        break;
                    case "女":
                        uploadUserInfo("sex","2");
                        ll_sex.setVisibility(View.GONE);
                        break;
                    case "人妖":
                        uploadUserInfo("sex","3");
                        ll_sex.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change_photo:
            case R.id.iv_photo:
                changePhoto();
                break;
            case R.id.rl_nickname:
                showDialog();
                break;
            case R.id.rl_phone_number:
                ToastUtil.showToast(mContext, "手机号不可更改");
                break;
            case R.id.rl_autograph:
                modifyAutograph();
                break;
            case R.id.rl_sex:
                ll_sex.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_business_license:
                ToastUtil.showToast(mContext, "上传营业执照研发中……");
                break;
        }
    }

    private void changePhoto() {
        SelectCaptureTypeView selectCaptureTypeView = SelectCaptureTypeView.create(mContext, new SelectCaptureTypeView.OnAttachListener() {
            @Override
            public void onSelect(int type) {
                if (type== Constants.TYPE_CAPTURE){
                    checkPremission();//拍照
                }else if (type==Constants.TYPE_ALBUM){
                    checkReadPermission();
                }
            }
        });

        if (!selectCaptureTypeView.isShow()){
            selectCaptureTypeView.show();
        }
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


    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {
        @Override
        public void onClick() {
            finish();
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    startPhotoZoom();
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
                        startPhotoZoom();
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
                    Matrix matrix = new Matrix();
                    matrix.setScale(0.3f, 0.3f);
                    Bitmap resultBitmap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),
                            bitMap.getHeight(), matrix, true);
                    showLoading();
                    uploadUserInfo("icon", Util.bitmaptoString(resultBitmap));
                }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    private void startPhotoZoom() {
        Intent intent = new Intent(PerfectInformationActivity.this, ClipCircleImageActivity.class);
        intent.putExtra(IMAGE_PATH_ORIGINAL, tempFile.getAbsolutePath());
        startActivityForResult(intent, CROP_SMALL_PICTURE);

    }

    public void showLoading() {
        if (mMpLoading != null) {
            mMpLoading.show();
        }
    }


    public void hideLoading() {
        if (mMpLoading != null) {
            mMpLoading.hide();
        }
    }


    private void uploadUserInfo(String type, String content) {
        if (mUser != null) {
            long userId = mUser.getUserId();
            uploadUserInfo(userId, type, content);
        }

    }

    private void showDialog() {
        String substring="";
        String startSubstring="";
        if (mUser != null) {
            mNickname = mUser.getNickname();
            if (!TextUtils.isEmpty(mNickname)){
                substring = mNickname.substring(mNickname.indexOf("_")+1,mNickname.length());
                startSubstring = mNickname.substring(0,mNickname.indexOf("_")+1);
            }
        }
        final String finalStartSubstring = startSubstring;
        mDialog = new IosDialog.DialogBuilder(this)
                .setTitle("编辑昵称")
                .setInputContent(substring)
                .setAsureText("修改")
                .setCancelText("取消")
                //.setDialogSize(Util.dip2px(MainActivity.this,189),Util.dip2px(MainActivity.this,117))
                .addListener(new IosDialog.OnButtonClickListener() {
                    @Override
                    public void onAsureClick() {
                        String newNickname = mDialog.getmEtInputContent().getText().toString().trim();
                        if (TextUtils.isEmpty(newNickname)) {
                            ToastUtil.showToast(mContext, "请输入您想要修改的昵称");
                            mDialog.dismiss();
                            return;
                        }
                        if (mNickname.equals(newNickname)) {
                            ToastUtil.showToast(mContext, "请编辑新昵称");
                            return;
                        }
                        uploadUserInfo("nickname", finalStartSubstring +newNickname);
                        showLoading();
                        mDialog.dismiss();

                    }

                    @Override
                    public void onCancelClick() {
                        mDialog.dismiss();
                    }
                }).create();
    }


    private void modifyAutograph() {
        if (mUser != null) {
            mAutograph = tv_autograph_content.getText().toString().trim();
        }
        mDialog = new IosDialog.DialogBuilder(this)
                .setTitle("编辑签名")
                .setInputContent(mAutograph)
                .setAsureText("修改")
                .setCancelText("取消")
                //.setDialogSize(Util.dip2px(MainActivity.this,189),Util.dip2px(MainActivity.this,117))
                .addListener(new IosDialog.OnButtonClickListener() {
                    @Override
                    public void onAsureClick() {
                        String autograph = mDialog.getmEtInputContent().getText().toString().trim();
                        if (TextUtils.isEmpty(autograph)) {
                            ToastUtil.showToast(mContext, "请输入您想要修改的签名");
                            mDialog.dismiss();
                            return;
                        }
                        if (mAutograph.equals(autograph)) {
                            ToastUtil.showToast(mContext, "请编辑新签名");
                            return;
                        }
                        uploadUserInfo("autograph", Util.encodeString(autograph));
                        showLoading();
                        mDialog.dismiss();

                    }

                    @Override
                    public void onCancelClick() {
                        mDialog.dismiss();
                    }
                }).create();
    }



    public void uploadUserInfo(long userId, String type, String content) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userId", userId);
        requestParam.put("type", type);
        requestParam.put("conetnt", content);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_UPDATE_USER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                hideLoading();
            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                hideLoading();
                if (result != null) {
                    User user = null;
                    if (user != null) {
                        mUser = user;
//                        String photoUrl = mUser.getPhotoUrl();
//                        RequestOptions options = new RequestOptions();
//                        options.circleCrop();
//                        options.placeholder(R.mipmap.ic_photo_default);
//                        Glide.with(mContext)
//                                .load(HttpUrl.URL_IMAGE + photoUrl)
//                                .apply(options)
//                                .into(mIvPhoto);
                        updateUI();
                        ToastUtil.showToast(mContext, "更新数据成功");
//                        UserManager.getInstance(App.getContext()).setUser(user);
//                        EventBus.getDefault().post(new MsgEvent("", MESSAGE_EVENT_UPDATE_USER_UI));
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                hideLoading();
                if (e instanceof com.google.gson.JsonParseException) {
                    ToastUtil.showToast(mContext, mContext.getString(R.string.data_analysis_error));
                }
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
