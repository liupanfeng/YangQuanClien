package com.meishe.yangquan.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.MsgEvent;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.CircleImageView;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.meishe.yangquan.wiget.IosDialog;
import com.meishe.yangquan.wiget.MaterialProgress;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.utils.Constants.MESSAGE_EVENT_UPDATE_USER_UI;

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
        mMpLoading = findViewById(R.id.mp_loading);
        mTvNickNameContent = findViewById(R.id.tv_nickname_content);
        mRlPhoneNumber = findViewById(R.id.rl_phone_number);
    }

    @Override
    public void initData() {
        HttpRequestUtil.getInstance(PerfectInformationActivity.this).setListener(this);
        mUser = UserManager.getInstance(this).getUser();
        if (mUser != null) {

            mTvPhoneNumber.setText(mUser.getPhoneNumber());
            mTvNickNameContent.setText(mUser.getNickname());

            String photoUrl = mUser.getPhotoUrl();
            if (!TextUtils.isEmpty(photoUrl)) {
                RequestOptions options = new RequestOptions();
                options.circleCrop();
                options.placeholder(R.mipmap.ic_little_sheep);
                Glide.with(mContext)
                        .load(HttpUrl.URL_IMAGE + photoUrl)
                        .apply(options)
                        .into(mIvPhoto);
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
        }
    }

    private void changePhoto() {
        new BottomMenuFragment(PerfectInformationActivity.this)
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


    @Override
    public void onSuccess(Object object) {
        hideLoading();
        if (object instanceof User) {
            mUser = (User) object;
        }
    }

    @Override
    public void onSuccess(int type, Object object) {
        hideLoading();
    }

    @Override
    public void onError(Object obj) {
        hideLoading();
    }

    @Override
    public void onError(int type, Object obj) {
        hideLoading();
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
                    showLoading();
                    uploadUserInfo("icon", Util.bitmaptoString(bitMap));
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
        if (mUser != null) {
            mNickname = mUser.getNickname();
        }
        mDialog = new IosDialog.DialogBuilder(this)
                .setTitle("编辑昵称")
                .setInputContent(mNickname)
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
                        mTvNickNameContent.setText(newNickname);
                        uploadUserInfo("nickname", newNickname);
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
                    User user = result.getData();
                    if (user != null) {
                        mUser = user;
                        String photoUrl = mUser.getPhotoUrl();
                        RequestOptions options = new RequestOptions();
                        options.circleCrop();
                        options.placeholder(R.mipmap.ic_little_sheep);
                        Glide.with(mContext)
                                .load(HttpUrl.URL_IMAGE + photoUrl)
                                .apply(options)
                                .into(mIvPhoto);
                        ToastUtil.showToast(mContext, "更新数据成功");
                        UserManager.getInstance(App.getContext()).setUser(user);
                        EventBus.getDefault().post(new MsgEvent("", MESSAGE_EVENT_UPDATE_USER_UI));
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


}
