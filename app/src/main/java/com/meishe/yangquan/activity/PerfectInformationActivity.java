package com.meishe.yangquan.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.UploadUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.CircleImageView;
import com.meishe.yangquan.wiget.CustomToolbar;

import java.io.File;
import java.io.IOException;

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
        mllPhoto = findViewById(R.id.ll_photo);
        mIvPhoto = findViewById(R.id.iv_photo);
    }

    @Override
    public void initData() {
        mUser=UserManager.getInstance(this).getUser();
        if (mUser!=null){
            String photoUrl=mUser.getPhotoUrl();
            if (!TextUtils.isEmpty(photoUrl)){
                RequestOptions options = new RequestOptions();
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                options.circleCrop();
                options.placeholder(R.mipmap.ic_little_sheep);
                Glide.with(mContext)
                        .asBitmap()
                        .load(HttpUrl.URL+photoUrl)
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
        mllPhoto.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_photo:
                changePhoto();
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


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (columnIndex >= 0){
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
//                        uploadFile(tempFile);
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
                    if (bitMap!=null){
                        mIvPhoto.setImageBitmap(bitMap);
                    }
                    uploadUserPhoto("icon", Util.bitmaptoString(bitMap));
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


    private void uploadUserPhoto(String type, String bitmapString) {
        if (mUser!=null){
            long userId=mUser.getUserId();
            HttpRequestUtil.getInstance(PerfectInformationActivity.this).uploadUserInfo(userId,type,bitmapString);
        }

    }

}
