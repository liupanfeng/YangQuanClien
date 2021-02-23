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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.CustomButtonWhitText;

import java.io.File;

import static com.meishe.yangquan.utils.Constants.UPLOAD_FILE_MODE_1;

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
    private int mShoppingType;
    /*个人店*/
    private CustomButtonWhitText cv_shopping_type_personal;
    /*个体工商户*/
    private CustomButtonWhitText cv_shopping_type_personal_business;
    /*有限公司*/
    private CustomButtonWhitText cv_shopping_type_company;


    /*主营类目类型*/
    private int mCategoryType;
    /*饲料*/
    private CustomButtonWhitText cv_category_type_feed;
    /*玉米*/
    private CustomButtonWhitText cv_category_type_corn;
    /*五金电料*/
    private CustomButtonWhitText cv_category_type_tool;


    /*货源类型*/
    private int mGoodsType;
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
    private TextView tv_bu_select_address;
    private File mTempFile;
    private PoiItem mPoiItem = null;
    /*上传图片类型*/
    private int mTypeCapture;

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
        tv_bu_select_address = findViewById(R.id.tv_bu_select_address);

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

        mRecyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager =
                new GridLayoutManager(mContext, 2);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void initData() {

        /*默认选中个人店*/
        selectPersonal();
        /*默认选中饲料*/
        selectFeed();
        /*默认选中*/
        selectFactory();

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
        tv_bu_select_address.setOnClickListener(this);
        iv_bu_shopping_outside.setOnClickListener(this);

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
                Object tag = iv_bu_shopping_outside.getTag();
                String path;
                if (tag instanceof String){
                    path= (String) tag;
                }else{
                    return;
                }
                Intent intent=new Intent(mContext, ShowPicActivity.class);
                intent.putExtra("imageUrl",path);
                mContext.startActivity(intent);
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
            /*选择经营地址*/
            case R.id.tv_bu_select_address:
                AppManager.getInstance().jumpActivityForResult(BUShoppingBaseInfoActivity.this, MineAddLocationActivity.class, null, SHOW_ADD_LOCATION_ACTIVITY_RESULT);
                break;
            default:
                break;


        }
    }


    private void selectPersonal() {
        mShoppingType = 1;
        cv_shopping_type_personal.setCircleSelected(true);
        cv_shopping_type_personal_business.setCircleSelected(false);
        cv_shopping_type_company.setCircleSelected(false);
    }

    /**
     * 选中个体工商户
     */
    private void selectPersonalBusiness() {
        mShoppingType = 2;
        cv_shopping_type_personal.setCircleSelected(false);
        cv_shopping_type_personal_business.setCircleSelected(true);
        cv_shopping_type_company.setCircleSelected(false);
    }

    /**
     * 选中公司
     */
    private void selectCompany() {
        mShoppingType = 3;
        cv_shopping_type_personal.setCircleSelected(false);
        cv_shopping_type_personal_business.setCircleSelected(false);
        cv_shopping_type_company.setCircleSelected(true);
    }


    /**
     * 选中饲料
     */
    private void selectFeed() {
        mCategoryType = 1;
        cv_category_type_feed.setCircleSelected(true);
        cv_category_type_corn.setCircleSelected(false);
        cv_category_type_tool.setCircleSelected(false);
    }

    /**
     * 选中玉米
     */
    private void selectCorn() {
        mCategoryType = 2;
        cv_category_type_feed.setCircleSelected(false);
        cv_category_type_corn.setCircleSelected(true);
        cv_category_type_tool.setCircleSelected(false);
    }

    /**
     * 选中工具
     */
    private void selectTool() {
        mCategoryType = 3;
        cv_category_type_feed.setCircleSelected(false);
        cv_category_type_corn.setCircleSelected(false);
        cv_category_type_tool.setCircleSelected(true);
    }

    /**
     * 选中厂家直销
     */
    private void selectFactory() {
        mGoodsType = 1;
        cv_goods_type_factory.setCircleSelected(true);
        cv_goods_type_replace.setCircleSelected(false);
    }

    /**
     * 选中分销
     */
    private void selectReplace() {
        mGoodsType = 2;
        cv_goods_type_factory.setCircleSelected(false);
        cv_goods_type_replace.setCircleSelected(true);
    }

    private void changePhoto() {
        new BottomMenuFragment(BUShoppingBaseInfoActivity.this)
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
                    if (mTypeCapture==1){
                        iv_bu_shopping_outside.setImageBitmap(bitmap);
                        iv_bu_shopping_outside.setTag(compressImagePath);
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
                        if (mTypeCapture==1){
                            iv_bu_shopping_outside.setImageBitmap(bitmap);
                            iv_bu_shopping_outside.setTag(compressImagePath);
                        }
                    }
                }
                break;

            case SHOW_ADD_LOCATION_ACTIVITY_RESULT:
                if (intent != null) {
                    mPoiItem = intent.getParcelableExtra("PoiItem");
                    String title = "不显示我的位置";
                    if (title.equals(mPoiItem.getTitle())) {
                        tv_bu_select_address.setText("请选择经营地址");
                    } else {
//                        tv_bu_select_address.setText(mPoiItem.getSnippet());
                        tv_bu_select_address.setText(mPoiItem.getTitle());
//                        mUserParamInfo.setCulturalAddress(mPoiItem.getTitle());
//                        hasChangeUserInfo();
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