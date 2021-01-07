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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.MsgResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFileResult;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CropViewUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
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

import static com.meishe.yangquan.fragment.HomeServiceFragment.TYPE_SERVICE_CUT_WOOL;
import static com.meishe.yangquan.fragment.HomeServiceFragment.TYPE_SERVICE_LOOK_CAR;
import static com.meishe.yangquan.fragment.HomeServiceFragment.TYPE_SERVICE_SHEEP_DUNG;
import static com.meishe.yangquan.fragment.HomeServiceFragment.TYPE_SERVICE_VACCINE;

/**
 * 服务发布页面
 */
public class PublishServiceActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;

    private int mSheepType = 0;
    private Bitmap showBitmap;
    private MaterialProgress mp_loading;
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


    @Override
    protected int initRootView() {
        return R.layout.activity_service_publish;
    }

    @Override
    public void initView() {

        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnPublish = findViewById(R.id.btn_publish);

        mEtServiceInputTeamName = findViewById(R.id.et_service_input_team_name);
        mEtServiceInputTeamNumber = findViewById(R.id.et_service_input_team_number);
        mEtServiceInputPrice = findViewById(R.id.et_service_input_price);
        mEtServiceInputTeamDesc = findViewById(R.id.et_service_input_team_desc);
        mEtServiceInputPhoneNumber = findViewById(R.id.et_service_input_phone_number);
        mIvPublishService = findViewById(R.id.iv_publish_service);

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
            case TYPE_SERVICE_CUT_WOOL:
                mTvTitle.setText("羊毛服务");
                break;
            case TYPE_SERVICE_VACCINE:
                mTvTitle.setText("疫苗服务");
                break;
            case TYPE_SERVICE_SHEEP_DUNG:
                mTvTitle.setText("羊粪服务");
                break;
            case TYPE_SERVICE_LOOK_CAR:
                mTvTitle.setText("车辆服务");
                break;
            default:
                break;
        }

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
        mIvPublishService.setOnClickListener(this);

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
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
                if (!mTempFile.exists()) {
                    ToastUtil.showToast(mContext, "图片必须上传");
                }
                uploadPicture();
                break;
            case R.id.iv_publish_service:
                showPictureSelectItem();
                break;
            default:
                break;
        }
    }

    /**
     * 图片上传
     */
    private void uploadPicture() {
        String token = UserManager.getInstance(mContext).getToken();
        if (Util.checkNull(token)) {
            return;
        }
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
                publishService(String.valueOf(data.getId()));
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
        formBody.add("userToken", userToken);
        formBody.add("sheepType", sheepType);
        formBody.add("msgContent", msgContent);
        formBody.add("iconBase64", iconBase64);
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
                if (response != null && response.code() == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(mContext, "发布成功");
                            mp_loading.hide();
                            finish();
                        }
                    });

                } else {
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
                if (result != null && result.getStatus() == 200) {
                    ToastUtil.showToast(mContext, "发布成功");
                    mp_loading.hide();
                    finish();
                } else {
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
//            String content = mEtInput.getText().toString().trim();
//            if (showBitmap == null) {
//                ToastUtil.showToast(mContext, "请选择图片！");
//                return;
//            }
//            if (TextUtils.isEmpty(content)) {
//                ToastUtil.showToast(mContext, "请输入内容！");
//                return;
//            }
            mp_loading.show();
//            publishMessage(mUser.getTokenId(),String.valueOf(mSheepType),content, Util.bitmaptoString(showBitmap));
//            publishMessage(mUser.getTokenId(),String.valueOf(mSheepType),content, Util.bitmaptoString(showBitmap));

        }
    }


    private void showPictureSelectItem() {
        new BottomMenuFragment(PublishServiceActivity.this)
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
                    Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(mTempFile.getAbsolutePath(), 1080);
                    if (tmpBitmap == null) {
                        return;
                    }
                    Matrix matrix = new Matrix();
                    matrix.setScale(0.2f, 0.2f);
                    showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
                            tmpBitmap.getHeight(), matrix, true);
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
                        mTempFile = new File(imagePath);
                        Bitmap tmpBitmap = CropViewUtils.compressBitmapForWidth(mTempFile.getAbsolutePath(), 1080);
                        Matrix matrix = new Matrix();
                        matrix.setScale(0.4f, 0.4f);
                        showBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(),
                                tmpBitmap.getHeight(), matrix, true);
                        mIvPublishService.setImageBitmap(showBitmap);
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
