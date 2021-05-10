package com.meishe.yangquan.activity;


import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFileResult;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.pop.SelectCaptureTypeView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 营业执照页面
 */
public class BUBusinessLicenseActivity extends BaseActivity {


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
    public static final String IMAGE_PATH_ORIGINAL = "original_image";
    private File mTempFile;


    private EditText et_bu_input_name;
    private EditText et_bu_input_card_number;
    /*经营范围*/
    private EditText et_bu_input_business_scope;
    /*选择到期时间*/
    private TextView tv_select_time;
    /*上传营业执照*/
    private View rl_bu_capture_business_license;

    private ImageView iv_bu_capture_business_license;

    private String mPicturePath;
    private View btn_bu_commit;

    private int mPictureAcount;

    private int mSuccessUploadAccount;

    private StringBuilder mInnerIds = new StringBuilder();
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePicker mDatePicker;


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_business_license;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        et_bu_input_name = findViewById(R.id.et_bu_input_name);
        et_bu_input_card_number = findViewById(R.id.et_bu_input_card_number);
        et_bu_input_business_scope = findViewById(R.id.et_bu_input_business_scope);
        tv_select_time = findViewById(R.id.tv_select_time);
        rl_bu_capture_business_license = findViewById(R.id.rl_bu_capture_business_license);
        iv_bu_capture_business_license = findViewById(R.id.iv_bu_capture_business_license);
        btn_bu_commit = findViewById(R.id.btn_bu_commit);
        mLoading = findViewById(R.id.loading);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("营业执照");
    }

    @Override
    public void initListener() {
        rl_bu_capture_business_license.setOnClickListener(this);
        btn_bu_commit.setOnClickListener(this);
        tv_select_time.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bu_capture_business_license:
                //上传营业执照
                changePhoto();
                break;
            case R.id.tv_select_time:
                showDataPicker(tv_select_time);
                break;
            case R.id.btn_bu_commit:
                //提交
                String name = et_bu_input_name.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入名称");
                    return;
                }

                String cardNumber = et_bu_input_card_number.getText().toString();

                if (TextUtils.isEmpty(cardNumber)) {
                    ToastUtil.showToast("请输入社会信用代码");
                    return;
                }

                String scope = et_bu_input_business_scope.getText().toString();
                if (TextUtils.isEmpty(scope)) {
                    ToastUtil.showToast("请输入经营范围");
                    return;
                }

                String time = tv_select_time.getText().toString();
                if (time.equals("请选择营业执照到期时间")) {
                    ToastUtil.showToast("请选择营业执照到期时间");
                    return;
                }

                if (TextUtils.isEmpty(mPicturePath)) {
                    ToastUtil.showToast("请上传营业执照");
                    return;
                }

                BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
                if (buShoppingInfo != null) {
                    buShoppingInfo.setBusinessName(name);
                    buShoppingInfo.setBusinessCreditCode(cardNumber);
                    buShoppingInfo.setBusinessScope(scope);
                    buShoppingInfo.setBusinessPeriod(time);
                    buShoppingInfo.setBusinessCardImageUrl(mPicturePath);

                    ShoppingInfoManager.getInstance().setBusinessLicenseWriteSuccess(true);
                    StatisticsPictureNumber(buShoppingInfo);


                    String shopOutSideImageUrl = buShoppingInfo.getShopOutSideImageUrl();
                    File file = new File(shopOutSideImageUrl);
                    if (file.exists()) {
                        uploadNeedPicture(Constants.UPLOAD_FILE_MODE_15, file);
                    }

                    List<String> shopInSideImageUrls = buShoppingInfo.getShopInSideImageUrls();
                    if (!CommonUtils.isEmpty(shopInSideImageUrls)) {
                        for (int i = 0; i < shopInSideImageUrls.size(); i++) {
                            String path = shopInSideImageUrls.get(i);
                            file = new File(path);
                            if (file.exists()) {
                                uploadNeedPicture(Constants.UPLOAD_FILE_MODE_16, file);
                            }
                        }
                    }

                    String ownerIdCardFrontImageUrl = buShoppingInfo.getOwnerIdCardFrontImageUrl();
                    file = new File(ownerIdCardFrontImageUrl);
                    if (file.exists()) {
                        uploadNeedPicture(Constants.UPLOAD_FILE_MODE_2, file);
                    }

                    String ownerIdCardReverseImageUrl = buShoppingInfo.getOwnerIdCardReverseImageUrl();
                    file = new File(ownerIdCardReverseImageUrl);
                    if (file.exists()) {
                        uploadNeedPicture(Constants.UPLOAD_FILE_MODE_3, file);
                    }

                    String businessCardImageUrl = buShoppingInfo.getBusinessCardImageUrl();

                    file = new File(businessCardImageUrl);
                    if (file.exists()) {
                        uploadNeedPicture(Constants.UPLOAD_FILE_MODE_14, file);
                    }
                }

                break;
        }
    }


    /**
     * 展示时间选择器
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDataPicker(final TextView textView) {
        String strTime = textView.getText().toString().trim();
        if (!TextUtils.isEmpty(strTime) && !strTime.equals("请选择营业执照到期时间")) {
            String[] split = strTime.split("-");
            if (split != null && split.length == 3) {
                mYear = Integer.valueOf(split[0]);
                mMonth = Integer.valueOf(split[1]);
                mDay = Integer.valueOf(split[2]);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setNegativeButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth() + 1;
                int dayOfMonth = mDatePicker.getDayOfMonth();
                textView.setText(year + "-" + month + "-" + dayOfMonth);
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        View view = View.inflate(mContext, R.layout.dialog_date, null);
        mDatePicker = view.findViewById(R.id.datePicker);
        alertDialog.setTitle("设置日期");
        alertDialog.setView(view);
        alertDialog.show();
        mDatePicker.init(mYear, mMonth - 1, mDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        });
    }


    /**
     * 统计需要上传的图片数量
     *
     * @param buShoppingInfo
     */
    private void StatisticsPictureNumber(BUShoppingInfo buShoppingInfo) {
        mPictureAcount = 0;
        String shopOutSideImageUrl = buShoppingInfo.getShopOutSideImageUrl();
        File file = new File(shopOutSideImageUrl);
        if (file.exists()) {
            mPictureAcount++;
        }

        List<String> shopInSideImageUrls = buShoppingInfo.getShopInSideImageUrls();
        if (!CommonUtils.isEmpty(shopInSideImageUrls)) {
            for (int i = 0; i < shopInSideImageUrls.size(); i++) {
                String path = shopInSideImageUrls.get(i);
                file = new File(path);
                if (file.exists()) {
                    mPictureAcount++;
                }
            }
        }

        String ownerIdCardFrontImageUrl = buShoppingInfo.getOwnerIdCardFrontImageUrl();
        file = new File(ownerIdCardFrontImageUrl);
        if (file.exists()) {
            mPictureAcount++;
        }

        String ownerIdCardReverseImageUrl = buShoppingInfo.getOwnerIdCardReverseImageUrl();
        file = new File(ownerIdCardReverseImageUrl);
        if (file.exists()) {
            mPictureAcount++;
        }

        String businessCardImageUrl = buShoppingInfo.getBusinessCardImageUrl();

        file = new File(businessCardImageUrl);
        if (file.exists()) {
            mPictureAcount++;
        }
    }

    /**
     * 单图片上传
     *
     * @param uploadMode 1 用户头像
     */
    private void uploadNeedPicture(final int uploadMode, File file) {
        String token = UserManager.getInstance(mContext).getToken();
        if (Util.checkNull(token)) {
            return;
        }
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put("uploadMode", uploadMode + "");
        param.put("order", "1");

        OkHttpManager.getInstance().postUploadSingleImage(HttpUrl.HOME_PAGE_COMMON_FILE_UPLOAD, new BaseCallBack<UploadFileResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, UploadFileResult uploadFileResult) {
                if (uploadFileResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (uploadFileResult.getCode() != 1) {
                    ToastUtil.showToast(uploadFileResult.getMsg());
                    return;
                }
                UploadFileInfo data = uploadFileResult.getData();
                if (data == null) {
                    ToastUtil.showToast("UploadFileInfo is null");
                    return;
                }
                BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
                mSuccessUploadAccount++;
                switch (uploadMode) {
                    case Constants.UPLOAD_FILE_MODE_2:
                        if (buShoppingInfo != null) {
                            buShoppingInfo.setOwnerIdCardFrontImageId(data.getId());
                        }
                        break;
                    case Constants.UPLOAD_FILE_MODE_3:
                        if (buShoppingInfo != null) {
                            buShoppingInfo.setOwnerIdCardReverseImageId(data.getId());
                        }
                        break;
                    case Constants.UPLOAD_FILE_MODE_14:
                        if (buShoppingInfo != null) {
                            buShoppingInfo.setBusinessCardImageId(data.getId());
                        }
                        break;

                    case Constants.UPLOAD_FILE_MODE_15:
                        if (buShoppingInfo != null) {
                            buShoppingInfo.setShopOutSideImageId(data.getId());
                        }
                        break;
                    case Constants.UPLOAD_FILE_MODE_16:
                        int length = mInnerIds.length();
                        if (length == 0) {
                            mInnerIds.append(data.getId());
                        } else {
                            mInnerIds.append("," + data.getId());
                        }
                        break;
                }

                if (mSuccessUploadAccount == mPictureAcount) {
                    if (buShoppingInfo != null) {
                        hideLoading();
                        buShoppingInfo.setShopInsideImageIds(mInnerIds.toString());
                        mSuccessUploadAccount = 0;
                        applyShoppingFromServer();
                    }
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
                        hideLoading();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, file, "file", param, token);
    }


    /**
     * 申请开店
     */
    private void applyShoppingFromServer() {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
        if (buShoppingInfo == null) {
            return;
        }
        showLoading();
        HashMap<String, Object> param = new HashMap<>();
        param.put("name", buShoppingInfo.getName());
        param.put("shopType", buShoppingInfo.getShopType());
        param.put("mainCategory", buShoppingInfo.getMainCategory());
        param.put("address", buShoppingInfo.getAddress());
        param.put("goodsSource", buShoppingInfo.getGoodsSource());
        param.put("sign", buShoppingInfo.getSign());
        param.put("shopOutsideImageId", buShoppingInfo.getShopOutSideImageId());
        param.put("shopInsideImageIds", buShoppingInfo.getShopInsideImageIds());
        param.put("ownerName", buShoppingInfo.getOwnerName());
        param.put("ownerIdCardNum", buShoppingInfo.getOwnerIdCardNum());
        param.put("ownerIdCardFrontImageId", buShoppingInfo.getOwnerIdCardFrontImageId());
        param.put("ownerIdCardReverseImageId", buShoppingInfo.getOwnerIdCardReverseImageId());
        param.put("businessName", buShoppingInfo.getBusinessName());
        param.put("businessCreditCode", buShoppingInfo.getBusinessCreditCode());
        param.put("businessScope", buShoppingInfo.getBusinessScope());
        param.put("businessPeriod", buShoppingInfo.getBusinessPeriod());
        param.put("businessCardImageId", buShoppingInfo.getBusinessCardImageId());
        param.put("needAuth", true);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_APPLY_SHOPPING_SANE, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }


            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                ToastUtil.showToast(mContext, "申请成功，请等待…");
                ShoppingInfoManager.getInstance().setApplyShoppingSuccess(true);
                AppManager.getInstance().finishActivity(BUBusinessLicenseActivity.class);
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
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
                    mPicturePath = compressImagePath;
                    iv_bu_capture_business_license.setImageBitmap(bitmap);

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
                        mPicturePath = compressImagePath;
                        iv_bu_capture_business_license.setImageBitmap(bitmap);
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