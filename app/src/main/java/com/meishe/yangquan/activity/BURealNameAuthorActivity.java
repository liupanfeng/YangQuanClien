package com.meishe.yangquan.activity;


import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.pop.SelectCaptureTypeView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;

import java.io.File;

/**
 * 实名认证
 */
public class BURealNameAuthorActivity extends BaseActivity {

    public static final int SHOW_ADD_LOCATION_ACTIVITY_RESULT = 300;

    private File tempFile;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;
    private File mTempFile;

    /*上传图片类型*/
    private int mTypeCapture;

    private View rl_bu_capture_card_negative;
    private View rl_bu_capture_card_positive;
    private View btn_bu_next;
    private ImageView iv_bu_capture_card_negative;
    private ImageView iv_bu_capture_card_positive;
    /*真实姓名*/
    private EditText et_bu_input_real_name;
    /*身份证号码*/
    private EditText et_bu_input_card_number;

    /*身份证正面*/
    private String mIDCardFrontImagePath;
    /*身份证反面*/
    private String mIDCardResoveImagePath;


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_real_name_author;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        rl_bu_capture_card_negative = findViewById(R.id.rl_bu_capture_card_negative);
        rl_bu_capture_card_positive = findViewById(R.id.rl_bu_capture_card_positive);
        iv_bu_capture_card_negative = findViewById(R.id.iv_bu_capture_card_negative);
        iv_bu_capture_card_positive = findViewById(R.id.iv_bu_capture_card_positive);
        et_bu_input_real_name = findViewById(R.id.et_bu_input_real_name);
        et_bu_input_card_number = findViewById(R.id.et_bu_input_card_number);
        btn_bu_next = findViewById(R.id.btn_bu_next);
    }

    @Override
    public void initData() {
        BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
        if (buShoppingInfo != null && ShoppingInfoManager.getInstance().isRealNameWriteSuccess()) {
            et_bu_input_real_name.setText(buShoppingInfo.getOwnerName());
            et_bu_input_card_number.setText(buShoppingInfo.getOwnerIdCardNum());
            String ownerIdCardFrontImageUrl = buShoppingInfo.getOwnerIdCardFrontImageUrl();
            mIDCardFrontImagePath = ownerIdCardFrontImageUrl;
            String ownerIdCardReverseImageUrl = buShoppingInfo.getOwnerIdCardReverseImageUrl();
            mIDCardResoveImagePath = ownerIdCardReverseImageUrl;
            Bitmap bitmap = BitmapUtils.
                    compressImage(ownerIdCardFrontImageUrl, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
            iv_bu_capture_card_positive.setImageBitmap(bitmap);

            bitmap = BitmapUtils.
                    compressImage(ownerIdCardReverseImageUrl, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
            iv_bu_capture_card_negative.setImageBitmap(bitmap);
        }
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("实名认证");
    }

    @Override
    public void initListener() {

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_bu_capture_card_negative.setOnClickListener(this);
        rl_bu_capture_card_positive.setOnClickListener(this);

        iv_bu_capture_card_negative.setOnClickListener(this);
        iv_bu_capture_card_positive.setOnClickListener(this);

        btn_bu_next.setOnClickListener(this);

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_bu_capture_card_positive:
                //正面
                changePhoto();
                mTypeCapture = 1;
                break;
            case R.id.rl_bu_capture_card_negative:
                //反面
                changePhoto();
                mTypeCapture = 2;
                break;
            case R.id.btn_bu_next:
                //下一步
                String ownName = et_bu_input_real_name.getText().toString();
                if (TextUtils.isEmpty(ownName)) {
                    ToastUtil.showToast("请填写真实姓名");
                    return;
                }

                String idCardNumber = et_bu_input_card_number.getText().toString();
                if (TextUtils.isEmpty(idCardNumber)) {
                    ToastUtil.showToast("请输入身份证号");
                    return;
                }

                if (TextUtils.isEmpty(mIDCardFrontImagePath)) {
                    ToastUtil.showToast("请上传身份证正面照片");
                    return;
                }

                if (TextUtils.isEmpty(mIDCardResoveImagePath)) {
                    ToastUtil.showToast("请上传身份证反面照片");
                    return;
                }

                BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
                if (buShoppingInfo == null) {
                    buShoppingInfo = new BUShoppingInfo();
                    ShoppingInfoManager.getInstance().setBuShoppingInfo(buShoppingInfo);
                }
                buShoppingInfo.setOwnerName(ownName);
                buShoppingInfo.setOwnerIdCardNum(idCardNumber);
                buShoppingInfo.setOwnerIdCardFrontImageUrl(mIDCardFrontImagePath);
                buShoppingInfo.setOwnerIdCardReverseImageUrl(mIDCardResoveImagePath);

                ShoppingInfoManager.getInstance().setRealNameWriteSuccess(true);
                AppManager.getInstance().jumpActivity(this, BUBusinessLicenseActivity.class);
                finish();
                break;
            case R.id.iv_bu_capture_card_positive:
                //查看大图
                Util.showBigPicture(mContext,mIDCardFrontImagePath);
                break;
            case R.id.iv_bu_capture_card_negative:
                //查看大图
                Util.showBigPicture(mContext,mIDCardFrontImagePath);
                break;

            default:
                break;

        }
    }


    private void changePhoto() {
        SelectCaptureTypeView selectCaptureTypeView = SelectCaptureTypeView.create(mContext, new SelectCaptureTypeView.OnAttachListener() {
            @Override
            public void onSelect(int type) {
                if (type==Constants.TYPE_CAPTURE){
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
                if (tempFile != null && tempFile.exists()) {
                    String compressImagePath = BitmapUtils.
                            compressImageUpload(tempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
                    mTempFile = new File(compressImagePath);
                    if (!mTempFile.exists()) {
                        return;
                    }
                    Bitmap bitmap = BitmapUtils.
                            compressImage(compressImagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
//                    mIvPersonalMinePhoto.setImageBitmap(bitmap);
//                    uploadPicture(UPLOAD_FILE_MODE_1);
                    if (mTypeCapture == 1) {
                        mIDCardFrontImagePath = compressImagePath;
                        iv_bu_capture_card_positive.setImageBitmap(bitmap);
                    } else if (mTypeCapture == 2) {
                        mIDCardResoveImagePath = compressImagePath;
                        iv_bu_capture_card_negative.setImageBitmap(bitmap);
                    }
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
                        String compressImagePath = BitmapUtils.compressImageUpload(imagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
                        mTempFile = new File(compressImagePath);
                        if (!mTempFile.exists()) {
                            return;
                        }
                        Bitmap bitmap = BitmapUtils.
                                compressImage(compressImagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
//                        mIvPersonalMinePhoto.setImageBitmap(bitmap);
//                        uploadPicture(UPLOAD_FILE_MODE_1);
                        if (mTypeCapture == 1) {
                            mIDCardFrontImagePath = compressImagePath;
                            iv_bu_capture_card_positive.setImageBitmap(bitmap);
                        } else if (mTypeCapture == 2) {
                            mIDCardResoveImagePath = compressImagePath;
                            iv_bu_capture_card_negative.setImageBitmap(bitmap);
                        }
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
//                    showLoading();
//                    uploadUserInfo("icon", Util.bitmaptoString(resultBitmap));
                }
        }
    }


}