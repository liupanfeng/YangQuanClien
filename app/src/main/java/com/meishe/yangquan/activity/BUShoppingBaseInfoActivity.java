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
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.amap.api.services.core.PoiItem;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUPictureInfo;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFileResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.pop.SelectCaptureTypeView;
import com.meishe.yangquan.pop.ShowBigPictureView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.CustomButtonWhitText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 申请开店基本信息填写页面
 */
public class BUShoppingBaseInfoActivity extends BaseActivity {

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


    /*商店类型*/
    private String mShoppingType;
    /*个人店*/
    private CustomButtonWhitText cv_shopping_type_personal;
    /*个体工商户*/
    private CustomButtonWhitText cv_shopping_type_personal_business;
    /*有限公司*/
    private CustomButtonWhitText cv_shopping_type_company;


    /*主营类目类型*/
    private String mCategoryType;
    /*饲料*/
    private CustomButtonWhitText cv_category_type_feed;
    /*玉米*/
    private CustomButtonWhitText cv_category_type_corn;
    /*五金电料*/
    private CustomButtonWhitText cv_category_type_tool;


    /*货源类型*/
    private String mGoodsType;
    /*厂家直销*/
    private CustomButtonWhitText cv_goods_type_factory;
    /*分销代销*/
    private CustomButtonWhitText cv_goods_type_replace;

    /*上传店外边照片*/
    private View rl_bu_capture;
    /*上传店内照片*/
    private View rl_bu_capture_inner;

    /*显示店外照片*/
    private ImageView iv_bu_shopping_outside;

    private EditText et_bu_input_shopping_name;
    /*签名*/
    private EditText et_bu_input_shopping_nickname;
    /*选择经营地址*/
    private EditText et_bu_select_address;
    private File mTempFile;
    private PoiItem mPoiItem = null;
    /*上传图片类型*/
    private int mTypeCapture;
    /*保存并进行下一步*/
    private View btn_bu_save_next;

    private List<BUPictureInfo> mPictureList = new ArrayList<>();
    /*外边的地址*/
    private String mOutsidePicturePath;


    @Override
    protected int initRootView() {
        return R.layout.activity_b_u_shopping_base_info;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        et_bu_input_shopping_name = findViewById(R.id.et_bu_input_shopping_name);
        et_bu_input_shopping_nickname = findViewById(R.id.et_bu_input_shopping_nickname);
        et_bu_select_address = findViewById(R.id.et_bu_select_address);

        //店铺类型
        cv_shopping_type_personal = findViewById(R.id.cv_shopping_type_personal);
        cv_shopping_type_personal.setCircleSelected(false);
        cv_shopping_type_personal.setContent("个人店");

        cv_shopping_type_personal_business = findViewById(R.id.cv_shopping_type_personal_business);
        cv_shopping_type_personal_business.setCircleSelected(false);
        cv_shopping_type_personal_business.setContent("个体工商户");

        cv_shopping_type_company = findViewById(R.id.cv_shopping_type_company);
        cv_shopping_type_company.setCircleSelected(false);
        cv_shopping_type_company.setContent("有限公司");

        //主营类目
        cv_category_type_feed = findViewById(R.id.cv_category_type_feed);
        cv_category_type_feed.setCircleSelected(false);
        cv_category_type_feed.setContent("饲料");

        cv_category_type_corn = findViewById(R.id.cv_category_type_corn);
        cv_category_type_corn.setCircleSelected(false);
        cv_category_type_corn.setContent("玉米");

        cv_category_type_tool = findViewById(R.id.cv_category_type_tool);
        cv_category_type_tool.setCircleSelected(false);
        cv_category_type_tool.setContent("五金电料");

        //货源类型
        cv_goods_type_factory = findViewById(R.id.cv_goods_type_factory);
        cv_goods_type_factory.setCircleSelected(false);
        cv_goods_type_factory.setContent("厂家直销");

        cv_goods_type_replace = findViewById(R.id.cv_goods_type_replace);
        cv_goods_type_replace.setCircleSelected(false);
        cv_goods_type_replace.setContent("分销/代销");

        rl_bu_capture = findViewById(R.id.rl_bu_capture);
        rl_bu_capture_inner = findViewById(R.id.rl_bu_capture_inner);
        iv_bu_shopping_outside = findViewById(R.id.iv_bu_shopping_outside);
        btn_bu_save_next = findViewById(R.id.btn_bu_save_next);

        mRecyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager =
                new GridLayoutManager(mContext, 2);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initData() {
        BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
        if (buShoppingInfo==null){
            /*默认选中个人店*/
            selectPersonal();
            /*默认选中饲料*/
            selectFeed();
            /*默认选中*/
            selectFactory();
            mPictureList.clear();
        }else{
            et_bu_select_address.setText(buShoppingInfo.getAddress());
            et_bu_input_shopping_nickname.setText(buShoppingInfo.getNickname());
            et_bu_input_shopping_name.setText(buShoppingInfo.getName());

            String shopOutSideImageUrl = buShoppingInfo.getShopOutSideImageUrl();

            Bitmap bitmap = BitmapUtils.
                    compressImage(shopOutSideImageUrl, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
            iv_bu_shopping_outside.setImageBitmap(bitmap);
            mOutsidePicturePath=shopOutSideImageUrl;

            List<String> shopInSideImageUrls = buShoppingInfo.getShopInSideImageUrls();
            for (int i = 0; i < shopInSideImageUrls.size(); i++) {
                String picturePath = shopInSideImageUrls.get(i);
                BUPictureInfo buPictureInfo=new BUPictureInfo();
                buPictureInfo.setFilePath(picturePath);
                buPictureInfo.setType(BUPictureInfo.TYPE_CAPTURE_PIC);
                mPictureList.add(buPictureInfo);
            }
            mAdapter.addAll(mPictureList);


            String goodsSource = buShoppingInfo.getGoodsSource();
            switch (goodsSource){
                case "厂家直销":
                    selectFactory();
                    break;

                case "分销代销":
                    selectReplace();
                    break;
            }

            String shopType = buShoppingInfo.getShopType();
            switch (shopType){
                case "个人店":
                    selectPersonal();
                    break;

                case "个体工商户":
                    selectPersonalBusiness();
                    break;
                case "有限公司":
                    selectCompany();
                    break;
            }
            String mainCategory = buShoppingInfo.getMainCategory();
            switch (mainCategory){
                case "饲料":
                    selectFeed();
                    break;

                case "玉米":
                    selectCorn();
                    break;
                case "五金电料":
                    selectTool();
                    break;
            }

        }
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("店铺信息");
    }

    @Override
    public void initListener() {
        cv_shopping_type_personal.setOnClickListener(this);
        cv_shopping_type_personal_business.setOnClickListener(this);
        cv_shopping_type_company.setOnClickListener(this);

        cv_category_type_feed.setOnClickListener(this);
        cv_category_type_corn.setOnClickListener(this);
        cv_category_type_tool.setOnClickListener(this);

        cv_goods_type_factory.setOnClickListener(this);
        cv_goods_type_replace.setOnClickListener(this);

        rl_bu_capture.setOnClickListener(this);
        rl_bu_capture_inner.setOnClickListener(this);
        iv_bu_shopping_outside.setOnClickListener(this);
        btn_bu_save_next.setOnClickListener(this);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_bu_select_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                AppManager.getInstance().jumpActivityForResult(BUShoppingBaseInfoActivity.this, LocationActivity.class, null, SHOW_ADD_LOCATION_ACTIVITY_RESULT);
                return true;
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_shopping_type_personal:
                selectPersonal();
                break;
            case R.id.cv_shopping_type_personal_business:
                selectPersonalBusiness();
                break;
            case R.id.cv_shopping_type_company:
                selectCompany();
                break;

            case R.id.cv_category_type_feed:
                selectFeed();
                break;
            case R.id.cv_category_type_corn:
                selectCorn();
                break;
            case R.id.cv_category_type_tool:
                selectTool();
                break;

            case R.id.cv_goods_type_factory:
                selectFactory();
                break;
            case R.id.cv_goods_type_replace:
                selectReplace();
                break;

            /*查看大图*/
            case R.id.iv_bu_shopping_outside:

                if (TextUtils.isEmpty(mOutsidePicturePath)) {
                    return;
                }

                ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, mOutsidePicturePath);
                if (showBigPictureView != null) {
                    showBigPictureView.show();
                }

                break;
            /*上传店外照片*/
            case R.id.rl_bu_capture:
                if (Util.isFastDoubleClick()) {
                    return;
                }
                mTypeCapture = 1;
                changePhoto();
                break;

            /*上传店内照片*/
            case R.id.rl_bu_capture_inner:
                if (Util.isFastDoubleClick()) {
                    return;
                }
                mTypeCapture = 2;
                changePhoto();
                break;
            /*保存并进行下一步*/
            case R.id.btn_bu_save_next:
                String shoppingName = et_bu_input_shopping_name.getText().toString();
                if (TextUtils.isEmpty(shoppingName)) {
                    ToastUtil.showToast("请填写店铺名称");
                    return;
                }
                String shoppingNickName = et_bu_input_shopping_nickname.getText().toString();
                if (TextUtils.isEmpty(shoppingNickName)) {
                    ToastUtil.showToast("请填写店铺签名");
                    return;
                }

                String address = et_bu_select_address.getText().toString();
                if (TextUtils.isEmpty(address)) {
                    ToastUtil.showToast("请选择店铺地址");
                    return;
                }

                if (TextUtils.isEmpty(mOutsidePicturePath)) {
                    ToastUtil.showToast("请上传实体门店头照片");
                    return;
                }

                if (CommonUtils.isEmpty(mPictureList)) {
                    ToastUtil.showToast("请上传实体门店内照片");
                    return;
                }

                BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
                if (buShoppingInfo == null) {
                    buShoppingInfo = new BUShoppingInfo();
                    ShoppingInfoManager.getInstance().setBuShoppingInfo(buShoppingInfo);
                }
                buShoppingInfo.setName(shoppingName);
                buShoppingInfo.setAddress(address);
                buShoppingInfo.setNickname(shoppingNickName);
                buShoppingInfo.setShopOutSideImageUrl(mOutsidePicturePath);
                List<String> picturePaths = new ArrayList<>();
                for (int i = 0; i < mPictureList.size(); i++) {
                    BUPictureInfo buPictureInfo = mPictureList.get(i);
                    picturePaths.add(buPictureInfo.getFilePath());
                }
                buShoppingInfo.setShopInSideImageUrls(picturePaths);

                buShoppingInfo.setGoodsSource(mGoodsType);
                buShoppingInfo.setShopType(mShoppingType);
                buShoppingInfo.setMainCategory(mCategoryType);

                ShoppingInfoManager.getInstance().setBaseMessageWriteSuccess(true);
                AppManager.getInstance().jumpActivity(BUShoppingBaseInfoActivity.this, BURealNameAuthorActivity.class);
                finish();
                break;
            default:
                break;

        }
    }


    private void selectPersonal() {
        mShoppingType = "个人店";
        cv_shopping_type_personal.setCircleSelected(true);
        cv_shopping_type_personal_business.setCircleSelected(false);
        cv_shopping_type_company.setCircleSelected(false);
    }

    /**
     * 选中个体工商户
     */
    private void selectPersonalBusiness() {
        mShoppingType = "个体工商户";
        cv_shopping_type_personal.setCircleSelected(false);
        cv_shopping_type_personal_business.setCircleSelected(true);
        cv_shopping_type_company.setCircleSelected(false);
    }

    /**
     * 选中公司
     */
    private void selectCompany() {
        mShoppingType = "有限公司";
        cv_shopping_type_personal.setCircleSelected(false);
        cv_shopping_type_personal_business.setCircleSelected(false);
        cv_shopping_type_company.setCircleSelected(true);
    }


    /**
     * 选中饲料
     */
    private void selectFeed() {
        mCategoryType = "饲料";
        cv_category_type_feed.setCircleSelected(true);
        cv_category_type_corn.setCircleSelected(false);
        cv_category_type_tool.setCircleSelected(false);
    }

    /**
     * 选中玉米
     */
    private void selectCorn() {
        mCategoryType = "玉米";
        cv_category_type_feed.setCircleSelected(false);
        cv_category_type_corn.setCircleSelected(true);
        cv_category_type_tool.setCircleSelected(false);
    }

    /**
     * 选中工具
     */
    private void selectTool() {
        mCategoryType = "五金电料";
        cv_category_type_feed.setCircleSelected(false);
        cv_category_type_corn.setCircleSelected(false);
        cv_category_type_tool.setCircleSelected(true);
    }

    /**
     * 选中厂家直销
     */
    private void selectFactory() {
        mGoodsType = "厂家直销";
        cv_goods_type_factory.setCircleSelected(true);
        cv_goods_type_replace.setCircleSelected(false);
    }

    /**
     * 选中分销
     */
    private void selectReplace() {
        mGoodsType = "分销代销";
        cv_goods_type_factory.setCircleSelected(false);
        cv_goods_type_replace.setCircleSelected(true);
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
        HashMap<String, String> param = new HashMap<>();
        param.put("uploadMode", uploadMode + "");
        param.put("order", "1");

        OkHttpManager.getInstance().postUploadSingleImage(HttpUrl.HOME_PAGE_COMMON_FILE_UPLOAD, new BaseCallBack<UploadFileResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

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
        }, file, "file", param, token);
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
                        mOutsidePicturePath = compressImagePath;
                        iv_bu_shopping_outside.setImageBitmap(bitmap);
                    } else if (mTypeCapture == 2) {
                        if (mPictureList.size() < 4) {
                            BUPictureInfo buPictureInfo = new BUPictureInfo();
                            buPictureInfo.setType(BUPictureInfo.TYPE_CAPTURE_PIC);
                            buPictureInfo.setFilePath(compressImagePath);
                            mPictureList.add(0, buPictureInfo);
                            mAdapter.addAll(mPictureList);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mPictureList.remove(mPictureList.size() - 1);
                            BUPictureInfo buPictureInfo = new BUPictureInfo();
                            buPictureInfo.setType(BUPictureInfo.TYPE_CAPTURE_PIC);
                            buPictureInfo.setFilePath(compressImagePath);
                            mPictureList.add(0, buPictureInfo);
                            mAdapter.addAll(mPictureList);
                            mAdapter.notifyDataSetChanged();
                        }
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
                            iv_bu_shopping_outside.setImageBitmap(bitmap);
                            mOutsidePicturePath = compressImagePath;
                        } else if (mTypeCapture == 2) {
                            if (mPictureList.size() < 4) {
                                BUPictureInfo buPictureInfo = new BUPictureInfo();
                                buPictureInfo.setType(BUPictureInfo.TYPE_CAPTURE_PIC);
                                buPictureInfo.setFilePath(compressImagePath);
                                mPictureList.add(0, buPictureInfo);
                                mAdapter.addAll(mPictureList);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mPictureList.remove(mPictureList.size() - 1);
                                BUPictureInfo buPictureInfo = new BUPictureInfo();
                                buPictureInfo.setType(BUPictureInfo.TYPE_CAPTURE_PIC);
                                buPictureInfo.setFilePath(compressImagePath);
                                mPictureList.add(0, buPictureInfo);
                                mAdapter.addAll(mPictureList);
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                }
                break;

            case SHOW_ADD_LOCATION_ACTIVITY_RESULT:
                if (intent != null) {
                    mPoiItem = intent.getParcelableExtra("PoiItem");
                    String title = "不显示我的位置";
                    if (title.equals(mPoiItem.getTitle())) {
                    } else {
                        et_bu_select_address.setText(mPoiItem.getTitle());
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