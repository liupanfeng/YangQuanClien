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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFileResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.SelectCaptureTypeView;
import com.meishe.yangquan.pop.SelectCarServiceTypeView;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 服务发布页面
 */
public class PublishServiceActivity extends BaseActivity {

    public static final int MAX_LENGTH = 30;

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;

    private Bitmap showBitmap;
    /*团队名称*/
    private EditText mEtServiceInputTeamName;
    private EditText mEtServiceInputTeamNumber;
    private EditText mEtServiceInputTeamDesc;

    private EditText mEtServiceInputPrice;
    private EditText mEtServiceInputPhoneNumber;

    private Button mBtnPublish;

    private int mServiceType;
    private String mTeamName;
    private String mTeamNumber;
    private String mTeamDesc;
    private String mPrice;
    private String mPhone;
    private String mWeight;
    private File mTempFile;
    private ImageView mIvPublishService;
    private LinearLayout mLlPublishCutSheep;
    private LinearLayout mLlPublishSheepDung;
    private LinearLayout mLlPublishFindCar;


    /*车对名称*/
    private EditText mEtServiceSheepDungCarName;
    /*车辆数量*/
    private EditText mEtServiceSheepDungCarNumber;
    /*车对人数*/
    private EditText mEtServiceSheepDungPersonNumber;
    /*服务价格*/
    private EditText mEtServiceSheepDrungPrice;

    /*车辆类型*/
    private EditText mEtServiceFindCarType;
    /*服务类别*/
    private EditText mEtServiceFindCarSreviceType;
    /*输入长宽高*/
    private EditText mEtServiceFindCarLengthWidthHeight;
    /*限制高度*/
    private EditText mEtServiceFindCarLimitHeight;

    private String mSheepDungPersonNumber;
    private String mSheepDungCuarNmber;
    private String mSheepDungCarName;
    private RadioGroup mRgSelectCarType;
    private TextView mTvServiceFindCarSreviceType;
    private int mCarType = 0;
    private String mCarLengthHighWidth;
    private String mCarLimitHeight;
    private int mCarServiceType;


    @Override
    protected int initRootView() {
        return R.layout.activity_service_publish;
    }

    @Override
    public void initView() {

        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnPublish = findViewById(R.id.btn_publish);
        mLoading = findViewById(R.id.loading);

        mEtServiceInputTeamName = findViewById(R.id.et_service_input_team_name);
        mEtServiceInputTeamNumber = findViewById(R.id.et_service_input_team_number);
        mEtServiceInputPrice = findViewById(R.id.et_service_input_price);
        mEtServiceInputTeamDesc = findViewById(R.id.et_service_input_team_desc);
        mEtServiceInputPhoneNumber = findViewById(R.id.et_sheep_dung_input_phone_number);
        mIvPublishService = findViewById(R.id.iv_sheep_dung_publish_service);

        mLlPublishCutSheep = findViewById(R.id.ll_publish_cut_sheep);
        mLlPublishSheepDung = findViewById(R.id.ll_publish_sheep_dung);
        mLlPublishFindCar = findViewById(R.id.ll_publish_find_car);


        ////////////////////////publish sheep dung////////////////////////////
        mEtServiceSheepDungCarName = findViewById(R.id.et_sheep_dung_input_team_name);
        mEtServiceSheepDungPersonNumber = findViewById(R.id.et_sheep_dung_input_team_number);
        mEtServiceSheepDungCarNumber = findViewById(R.id.et_sheep_dung_input_car_number);
        mEtServiceSheepDrungPrice = findViewById(R.id.et_sheep_dung_input_price);

        ///////////////////////publish find cat //////////////////////////////////
        mRgSelectCarType = findViewById(R.id.rg_select_car_type);
        mTvServiceFindCarSreviceType = findViewById(R.id.tv_find_car_input_service_type);
        mEtServiceFindCarLengthWidthHeight = findViewById(R.id.et_find_car_input_length_width_height);
        mEtServiceFindCarLimitHeight = findViewById(R.id.et_find_car_input_limit_height);


    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mServiceType = extras.getInt("service_type");
            }
        }
    }

    @Override
    public void initTitle() {
        switch (mServiceType) {
            case Constants.TYPE_SERVICE_CUT_WOOL:
                mTvTitle.setText("羊毛服务");
                showCutHair();
                break;
            case Constants.TYPE_SERVICE_VACCINE:
                mTvTitle.setText("疫苗服务");
                showCutHair();
                break;
            case Constants.TYPE_SERVICE_SHEEP_DUNG:
                mTvTitle.setText("羊粪服务");
                showSheepDung();
                break;
            case Constants.TYPE_SERVICE_LOOK_CAR:
                mTvTitle.setText("车辆服务");
                showFindCar();
                break;
            default:
                break;
        }

    }

    /**
     * 展示找车辆
     */
    private void showFindCar() {
        mLlPublishCutSheep.setVisibility(View.GONE);
        mLlPublishSheepDung.setVisibility(View.GONE);
        mLlPublishFindCar.setVisibility(View.VISIBLE);
    }

    private void showSheepDung() {
        mLlPublishCutSheep.setVisibility(View.GONE);
        mLlPublishSheepDung.setVisibility(View.VISIBLE);
        mLlPublishFindCar.setVisibility(View.GONE);
    }

    /**
     * 展示剪羊毛 页面  剪羊毛跟打疫苗设置页面一样
     */
    private void showCutHair() {
        mLlPublishCutSheep.setVisibility(View.VISIBLE);
        mLlPublishSheepDung.setVisibility(View.GONE);
        mLlPublishFindCar.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        mBtnPublish.setOnClickListener(this);
        mIvPublishService.setOnClickListener(this);
        mTvServiceFindCarSreviceType.setOnClickListener(this);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRgSelectCarType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.rb_long_car) {
                    //长途车
                    mCarType = 1;
                } else if (checkedRadioButtonId == R.id.rb_short_car) {
                    //短途车
                    mCarType = 2;
                }
            }
        });

        mEtServiceInputTeamDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                String str = s.toString();
                if (length > MAX_LENGTH) {
                    mEtServiceInputTeamDesc.setText(str.substring(0, MAX_LENGTH));
                    mEtServiceInputTeamDesc.requestFocus();
                    mEtServiceInputTeamDesc.setSelection(mEtServiceInputTeamDesc.getText().length());
                } else {
//                    int i = MAX_LENGTH - length;
//                    mTvNumberLimit.setText(String.valueOf(i));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                switch (mServiceType) {
                    case Constants.TYPE_SERVICE_CUT_WOOL:
                    case Constants.TYPE_SERVICE_VACCINE:
                        publishCutSheepHair();
                        break;
                    case Constants.TYPE_SERVICE_SHEEP_DUNG:
                        publishSheepDung();
                        break;
                    case Constants.TYPE_SERVICE_LOOK_CAR:
                        publishSheepCar();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_sheep_dung_publish_service:
                showPictureSelectItem();
                break;
            case R.id.tv_find_car_input_service_type:
                //展示选择服务
                SelectCarServiceTypeView selectCarServiceTypeView = SelectCarServiceTypeView.create(mContext, mTvServiceFindCarSreviceType, new SelectCarServiceTypeView.OnAttachListener() {
                    @Override
                    public void onSelectType(String content) {
                        if ("拉成品羊".equals(content)) {
                            mCarServiceType = 1;
                        } else if ("拉饲料".equals(content)) {
                            mCarServiceType = 2;
                        } else if ("拉玉米".equals(content)) {
                            mCarServiceType = 3;
                        } else if ("冷藏车".equals(content)) {
                            mCarServiceType = 4;
                        }
                        mTvServiceFindCarSreviceType.setText(content);
                    }
                });
                selectCarServiceTypeView.show();
                break;
            default:
                break;
        }
    }


    /**
     * 发布剪羊毛 和打疫苗
     */
    private void publishCutSheepHair() {
        mTeamName = mEtServiceInputTeamName.getText().toString().trim();
        if (Util.checkNull(mTeamName)) {
            ToastUtil.showToast(mContext, "服务名称必须填写");
            return;
        }

        mTeamNumber = mEtServiceInputTeamNumber.getText().toString().trim();
        if (Util.checkNull(mTeamNumber)) {
            ToastUtil.showToast(mContext, "服务数量必须填写");
            return;
        }

        mPrice = mEtServiceInputPrice.getText().toString().trim();
        if (Util.checkNull(mPrice)) {
            ToastUtil.showToast(mContext, "价格必须填写");
            return;
        }
        mTeamDesc = mEtServiceInputTeamDesc.getText().toString().trim();
        if (Util.checkNull(mTeamDesc)) {
            ToastUtil.showToast(mContext, "团队描述必须填写");
            return;
        }
        mPhone = mEtServiceInputPhoneNumber.getText().toString().trim();
        if (Util.checkNull(mPhone)) {
            ToastUtil.showToast(mContext, "手机号必须填写");
            return;
        }
        if (mTempFile==null||!mTempFile.exists()) {
            ToastUtil.showToast(mContext, "图片必须上传");
            return;
        }
        uploadPicture();
    }


    /**
     * publish sheep dung
     * 这个类别不一样的只有一个是 车队的数量
     */
    private void publishSheepDung() {

        mSheepDungCarName = mEtServiceSheepDungCarName.getText().toString().trim();
        if (Util.checkNull(mSheepDungCarName)) {
            ToastUtil.showToast(mContext, "车队名称必须填写");
            return;
        }

        mSheepDungCuarNmber = mEtServiceSheepDungCarNumber.getText().toString().trim();
        if (Util.checkNull(mSheepDungCuarNmber)) {
            ToastUtil.showToast(mContext, "车辆数必须填写");
            return;
        }

        mPrice = mEtServiceSheepDrungPrice.getText().toString().trim();
        if (Util.checkNull(mPrice)) {
            ToastUtil.showToast(mContext, "服务价格必须填写");
            return;
        }

        mSheepDungPersonNumber = mEtServiceSheepDungPersonNumber.getText().toString().trim();
        if (Util.checkNull(mSheepDungPersonNumber)) {
            ToastUtil.showToast(mContext, "车队人数必须填写");
            return;
        }

        mPhone = mEtServiceInputPhoneNumber.getText().toString().trim();
        if (Util.checkNull(mPhone)) {
            ToastUtil.showToast(mContext, "手机号必须填写");
            return;
        }
        if (mTempFile==null||!mTempFile.exists()) {
            ToastUtil.showToast(mContext, "图片必须上传");
            return;
        }
        uploadPicture();
    }

    /**
     * 发布找车辆
     */
    private void publishSheepCar() {
        if (mCarType == 0) {
            ToastUtil.showToast(mContext, "车类型必须选择");
            return;
        }

        if (mCarServiceType == 0) {
            ToastUtil.showToast(mContext, "服务类别必须选择");
            return;
        }

        mCarLengthHighWidth = mEtServiceFindCarLengthWidthHeight.getText().toString().trim();
        if (Util.checkNull(mCarLengthHighWidth)) {
            ToastUtil.showToast(mContext, "车长高宽必须填写");
            return;
        }

        mCarLimitHeight = mEtServiceFindCarLimitHeight.getText().toString().trim();
        if (Util.checkNull(mCarLimitHeight)) {
            ToastUtil.showToast(mContext, "限高必须填写");
            return;
        }

        mPhone = mEtServiceInputPhoneNumber.getText().toString().trim();
        if (Util.checkNull(mPhone)) {
            ToastUtil.showToast(mContext, "手机号必须填写");
            return;
        }
        if (mTempFile==null||!mTempFile.exists()) {
            ToastUtil.showToast(mContext, "图片必须上传");
            return;
        }
        uploadPicture();
    }


    /**
     * 图片上传
     */
    private void uploadPicture() {
        String token = UserManager.getInstance(mContext).getToken();
        if (Util.checkNull(token)) {
            return;
        }
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put("uploadMode", "5");
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
                    hideLoading();
                    return;
                }
                if (uploadFileResult.getCode() != 1) {
                    ToastUtil.showToast(uploadFileResult.getMsg());
                    hideLoading();
                    return;
                }
                UploadFileInfo data = uploadFileResult.getData();
                if (data == null) {
                    ToastUtil.showToast("UploadFileInfo is null");
                    hideLoading();
                    return;
                }
                switch (mServiceType) {
                    case Constants.TYPE_SERVICE_SHEEP_DUNG:
                        publishSheepDungService(String.valueOf(data.getId()));
                        break;
                    case Constants.TYPE_SERVICE_VACCINE:
                    case Constants.TYPE_SERVICE_CUT_WOOL:
                        publishService(String.valueOf(data.getId()));
                        break;
                    case Constants.TYPE_SERVICE_LOOK_CAR:
                        publishFindCarService(String.valueOf(data.getId()));
                        break;
                    default:
                        break;
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
        }, mTempFile, "file", param, token);
    }

    /**
     * 发布找车辆 服务
     *
     * @param pictureId
     */
    private void publishFindCarService(String pictureId) {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("typeId", mServiceType);
        requestParam.put("carType", mCarType);
        requestParam.put("serviceType", mCarServiceType);
        requestParam.put("carVolume", mCarLengthHighWidth);
        requestParam.put("maxHeight", mCarLimitHeight);
        requestParam.put("phone", mPhone);
        requestParam.put("fileIds", pictureId);
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_ADD_SERVICE, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast(mContext, "发布成功");
                    finish();
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
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
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }



    /**
     * 发布 cut sheep hair
     *
     * @param pictureId
     */
    public void publishService(String pictureId) {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("typeId", mServiceType);
        requestParam.put("teamName", mTeamName);
        requestParam.put("teamDesc", mTeamDesc);
        requestParam.put("teamHumanScale", mTeamNumber);
        requestParam.put("price", mPrice);
        requestParam.put("phone", mPhone);
        requestParam.put("fileIds", pictureId);
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_ADD_SERVICE, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast(mContext, "发布成功");
                    finish();
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
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
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }


    /**
     * 发布  sheep dung
     *
     * @param pictureId
     */
    public void publishSheepDungService(String pictureId) {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("typeId", mServiceType);
        requestParam.put("teamName", mSheepDungCarName);
        requestParam.put("teamCarScale", mSheepDungCuarNmber);
        requestParam.put("teamHumanScale", mSheepDungPersonNumber);
        requestParam.put("price", mPrice);
        requestParam.put("phone", mPhone);
        requestParam.put("fileIds", pictureId);
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_ADD_SERVICE, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast(mContext, "发布成功");
                    finish();
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
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
                        ToastUtil.showToast(mContext, "上传失败");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }


    private void showPictureSelectItem() {
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
        mTempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", mTempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
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
            //调用相机后返回
            case CAMERA_REQUEST_CODE:
                if (mTempFile != null) {
                    showBitmap = BitmapUtils.compressImage(mTempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH,Constants.COMPRESS_HEIGHT);
                    mIvPublishService.setImageBitmap(showBitmap);
                }
                break;
            //调用相册后返回
            case ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String imagePath = null;
                    if (intent == null) {
                        return;
                    }
                    Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    if (DocumentsContract.isDocumentUri(this, uri)) {
                        //如果是document类型的Uri,则通过document id处理
                        String docId = DocumentsContract.getDocumentId(uri);
                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                            //解析出数字格式的id
                            String id = docId.split(":")[1];
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
                        showBitmap = BitmapUtils.compressImage(imagePath, Constants.COMPRESS_WIDTH,Constants.COMPRESS_HEIGHT);
                        mIvPublishService.setImageBitmap(showBitmap);
                        mTempFile=new File(imagePath);
                    }
                }
                break;

            //调用剪裁后返回
            case CROP_SMALL_PICTURE:
                if (resultCode == RESULT_OK) {
                    if (intent == null) {
                        return;
                    }
                    File tempCoverFile = new File(PathUtils.getPersonalCoverImageDirectory(), PathUtils.getTempCoverImageName(mContext));
                    Bitmap bitMap = BitmapFactory.decodeFile(tempCoverFile.getAbsolutePath());
//                    if (bitMap != null) {
//                        mIvPhoto.setImageBitmap(bitMap);
//                    }
//                    showLoading();
//                    uploadUserInfo("icon", Util.bitmaptoString(bitMap));
                }
                break;
            default:
                break;
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
