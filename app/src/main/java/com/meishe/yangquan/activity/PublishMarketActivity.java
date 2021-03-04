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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.amap.api.services.core.PoiItem;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFilesResult;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * s首页-市场发布
 */
public class PublishMarketActivity extends BaseActivity {

    public static final int SHOW_ADD_LOCATION_ACTIVITY_RESULT = 300;

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;
    private File tempFile;

    private List<CommonPictureInfo> mData = new ArrayList<>();

    private TextView mTvTitle;
    private ImageView mIvBack;

    /*标题*/
    private EditText mEtMarketInputTitle;
    /*品种*/
    private EditText mEtMarketInputVarity;
    /*重量*/
    private EditText mEtMarketInputWeight;
    /*数量*/
    private EditText mEtMarketInputAmount;
    /*价格*/
    private EditText mEtMarketInputPrice;
    /*电话*/
    private EditText mEtMarketInputPhone;
    /*发布*/
    private Button mBtnPublish;

    private int mMarketType;
    private String mTitle;
    private String mVarity;
    private String mAmount;
    private String mPrice;
    private String mPhone;
    private String mWeight;
    private ImageView mIvSheepMarketPublish;
    private View rl_price;
    private EditText et_market_desc;
    private String mDesc;
    private View rl_desc;
    private View tv_picture;
    private int mMaxPictureAmount;
    private EditText mEtMarketInputAddress;
    private String mAddress;
    private PoiItem mPoiItem;


    @Override
    protected int initRootView() {
        return R.layout.activity_market_publish;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnPublish = findViewById(R.id.btn_publish);

        mEtMarketInputTitle = findViewById(R.id.et_market_input_title);
        mEtMarketInputVarity = findViewById(R.id.et_market_input_variety);
        mEtMarketInputWeight = findViewById(R.id.et_market_input_weight);
        mEtMarketInputAmount = findViewById(R.id.et_market_input_amount);
        mEtMarketInputPrice = findViewById(R.id.et_market_input_price);
        mEtMarketInputPhone = findViewById(R.id.et_market_input_phone);
        mEtMarketInputAddress = findViewById(R.id.et_address);
        mIvSheepMarketPublish = findViewById(R.id.iv_sheep_market_publish);
        rl_price = findViewById(R.id.rl_price);
        rl_desc = findViewById(R.id.rl_desc);
        et_market_desc = findViewById(R.id.et_market_desc);
        tv_picture = findViewById(R.id.tv_picture);

        mRecyclerView = findViewById(R.id.recycler);
        mLoading = findViewById(R.id.loading);

        initGridRecyclerView();
    }

    private void initGridRecyclerView() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(mContext, 4);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(15);
        mRecyclerView.addItemDecoration(customGridItemDecoration);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mMarketType = extras.getInt(Constants.MARKET_TYPE);
            }
        }

        mData.clear();
        CommonPictureInfo commonPictureInfo = new CommonPictureInfo();
        commonPictureInfo.setFilePath(String.valueOf(R.mipmap.ic_sheep_bar_add));
        commonPictureInfo.setType(CommonPictureInfo.TYPE_ADD_PIC);
        mData.add(commonPictureInfo);
        mAdapter.addAll(mData);
    }

    @Override
    public void initTitle() {
        switch (mMarketType) {
            case Constants.TYPE_MARKET_SELL_LITTLE_SHEEP:
                mTvTitle.setText("出售羊苗");
                rl_price.setVisibility(View.VISIBLE);
                rl_desc.setVisibility(View.GONE);
                mMaxPictureAmount=7;
                break;
            case Constants.TYPE_MARKET_BUY_LITTLE_SHEEP:
                mTvTitle.setText("购买羊苗");
                rl_price.setVisibility(View.GONE);
                rl_desc.setVisibility(View.VISIBLE);
                mMaxPictureAmount=1;
                break;
            case Constants.TYPE_MARKET_SELL_BIG_SHEEP:
                mTvTitle.setText("出售成品羊");
                rl_price.setVisibility(View.GONE);
                rl_desc.setVisibility(View.GONE);
                mMaxPictureAmount=7;
                break;
            case Constants.TYPE_MARKET_BUY_BIG_SHEEP:
                mTvTitle.setText("收购成品羊");
                rl_price.setVisibility(View.GONE);
                rl_desc.setVisibility(View.VISIBLE);
                mMaxPictureAmount=1;
                break;
        }

    }

    public void hidePictureUpload(){
        tv_picture.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }


    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnPublish.setOnClickListener(this);
        mIvSheepMarketPublish.setOnClickListener(this);

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof CommonPictureInfo) {
                    if (((CommonPictureInfo) baseInfo).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                        if (mData != null && mData.size() >  mMaxPictureAmount) {
                            ToastUtil.showToast(mContext, "最多添加"+mMaxPictureAmount+"张图片");
                            return;
                        }
                        showPictureSelectItem();
                    }
                }
            }
        });

        mEtMarketInputAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Util.isFastDoubleClick()){
                    return true;
                }
                AppManager.getInstance().jumpActivityForResult(PublishMarketActivity.this, MineAddLocationActivity.class, null, SHOW_ADD_LOCATION_ACTIVITY_RESULT);
                return true;
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_publish:
                mTitle = mEtMarketInputTitle.getText().toString().trim();
                if (Util.checkNull(mTitle)) {
                    ToastUtil.showToast(mContext, "标题必须填写");
                    return;
                }

                mVarity = mEtMarketInputVarity.getText().toString().trim();
                if (Util.checkNull(mVarity)) {
                    ToastUtil.showToast(mContext, "品种必须填写");
                    return;
                }

                mWeight = mEtMarketInputWeight.getText().toString().trim();
                if (Util.checkNull(mWeight)) {
                    ToastUtil.showToast(mContext, "重量必须填写");
                    return;
                }
                mAmount = mEtMarketInputAmount.getText().toString().trim();
                if (Util.checkNull(mAmount)) {
                    ToastUtil.showToast(mContext, "数量必须填写");
                    return;
                }
                mPrice = mEtMarketInputPrice.getText().toString().trim();
                mPhone = mEtMarketInputPhone.getText().toString().trim();
                if (Util.checkNull(mPhone)) {
                    ToastUtil.showToast(mContext, "电话必须填写");
                    return;
                }
                mAddress = mEtMarketInputAddress.getText().toString().trim();
                if (Util.checkNull(mAddress)) {
                    ToastUtil.showToast(mContext, "地址必须填写");
                    return;
                }

                mDesc = et_market_desc.getText().toString().trim();
                uploadPictures();
                break;
            case R.id.iv_sheep_market_publish:
                showPictureSelectItem();
                break;
        }
    }


    /**
     * 上传多张图片
     */
    private void uploadPictures() {
        if (CommonUtils.isEmpty(mData)) {
            return;
        }
        int size = mData.size();
        if (size <= 1) {
            ToastUtil.showToast(mContext, "请选择图片！");
            return;
        }
        String[] fileKeys = new String[size - 1];
        File[] files = new File[size - 1];
        for (int i = 0; i < mData.size(); i++) {
            CommonPictureInfo commonPictureInfo = mData.get(i);
            if (commonPictureInfo == null) {
                continue;
            }

            File file = new File(commonPictureInfo.getFilePath());
            if (!file.exists()) {
                continue;
            }
            files[i] = file;
            fileKeys[i] = "files";
        }
        String token = UserManager.getInstance(mContext).getToken();
        if (Util.checkNull(token)) {
            return;
        }
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put("uploadMode", Constants.UPLOAD_FILE_MODE_4 + "");
        param.put("order", "1");

        OkHttpManager.getInstance().postUploadMoreImages(HttpUrl.HOME_PAGE_COMMON_FILES_UPLOAD, new BaseCallBack<UploadFilesResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, UploadFilesResult uploadFileResult) {
                if (uploadFileResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (uploadFileResult.getCode() != 1) {
                    ToastUtil.showToast(uploadFileResult.getMsg());
                    return;
                }
                List<UploadFileInfo> datas = uploadFileResult.getData();
                if (datas == null) {
                    ToastUtil.showToast("UploadFileInfo is null");
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < datas.size(); i++) {
                    UploadFileInfo uploadFileInfo = datas.get(i);
                    if (uploadFileInfo == null) {
                        continue;
                    }
                    stringBuilder.append(uploadFileInfo.getId());
                    if (i < datas.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                publishMarket(stringBuilder.toString());
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
        }, files, fileKeys, param, token);
    }


    /**
     * 发布市场
     *  购买羊苗 收购成品羊 有描述  没有价格
     *
     *  价格只有出售羊苗有
     */
    private void publishMarket(String pictures) {
        String token = UserManager.getInstance(mContext).getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", mMarketType);
        param.put("title", mTitle);
        param.put("variety", mVarity);
        param.put("weight", mWeight);
        param.put("amount", mAmount);
        param.put("phone", mPhone);
        param.put("address", mAddress);
        if (mMarketType==Constants.TYPE_MARKET_SELL_LITTLE_SHEEP){
            param.put("price", mPrice);
        }
        if ((mMarketType==Constants.TYPE_MARKET_BUY_LITTLE_SHEEP) ||(mMarketType==Constants.TYPE_MARKET_BUY_BIG_SHEEP)) {
            //购买羊苗  收购成品羊
            param.put("description", mDesc);
        }
        param.put("fileIds", pictures);

        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_ADD_MARKET, new BaseCallBack<ServerResult>() {
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
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                hideLoading();
                if (serverResult != null) {
                    if (serverResult.getCode() != 1) {
                        ToastUtil.showToast(mContext, serverResult.getMsg());
                    } else {
                        ToastUtil.showToast(mContext, "发布成功");
                        finish();
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
        }, param, token);
    }


    private void showPictureSelectItem() {
        new BottomMenuFragment(PublishMarketActivity.this)
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
                if (tempFile != null && tempFile.exists()) {
                    String filePath = BitmapUtils.compressImageUpload(tempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH,Constants.COMPRESS_HEIGHT);
                    CommonPictureInfo sheepBarMessageInfo = new CommonPictureInfo();
                    sheepBarMessageInfo.setFilePath(filePath);
                    sheepBarMessageInfo.setType(CommonPictureInfo.TYPE_CAPTURE_PIC);
                    mData.add(0, sheepBarMessageInfo);
                    mAdapter.addAll(mData);
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
                        String filePath = BitmapUtils.compressImageUpload(imagePath, Constants.COMPRESS_WIDTH,Constants.COMPRESS_HEIGHT);
                        CommonPictureInfo sheepBarMessageInfo = new CommonPictureInfo();
                        sheepBarMessageInfo.setFilePath(filePath);
                        sheepBarMessageInfo.setType(CommonPictureInfo.TYPE_CAPTURE_PIC);
                        mData.add(0, sheepBarMessageInfo);
                        mAdapter.addAll(mData);
                    }
                }
                break;
            case SHOW_ADD_LOCATION_ACTIVITY_RESULT:
                if (intent != null) {
                    mPoiItem = intent.getParcelableExtra("PoiItem");
                    String title = "不显示我的位置";
                    if (title.equals(mPoiItem.getTitle())) {
                    } else {
                        mEtMarketInputAddress.setText(mPoiItem.getTitle());
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


}
