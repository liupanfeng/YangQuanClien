package com.meishe.yangquan.activity;


import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.amap.api.services.core.PoiItem;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.BUShoppingInfoResult;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFileResult;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.bean.UserParamInfo;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.fragment.ModifyUserInfoFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.pop.SelectCaptureTypeView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_SHOPPING_INFO;
import static com.meishe.yangquan.utils.Constants.UPLOAD_FILE_MODE_1;

/**
 * 商版-我的-店铺信息
 */
public class BUMineShoppingMessageActivity extends BaseActivity {

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

    /*店铺名称*/
    private EditText et_bu_shopping_name;
    private EditText et_bu_shopping_address;
    private EditText et_bu_shopping_sell_type;
    private EditText et_bu_shopping_nickname;

    private boolean isShowModifyView = false;
    private ModifyUserInfoFragment mModifyUserInfoFragment;
    private PoiItem mPoiItem;
    private Button mBtnRight;

    private ImageView iv_personal_mine_photo;
    private UserInfo mUser;
    private File mTempFile;

    @Override
    protected int initRootView() {
        return R.layout.activity_bu_mine_shopping_message;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        mBtnRight = findViewById(R.id.btn_right);
        mBtnRight.setText("完成");
        mBtnRight.setVisibility(View.GONE);

        et_bu_shopping_name = findViewById(R.id.et_bu_shopping_name);
        et_bu_shopping_address = findViewById(R.id.et_bu_shopping_address);
        et_bu_shopping_sell_type = findViewById(R.id.et_bu_shopping_sell_type);
        et_bu_shopping_nickname = findViewById(R.id.et_bu_shopping_nickname);

        iv_personal_mine_photo = findViewById(R.id.iv_personal_mine_photo);
    }

    @Override
    public void initData() {
        mUser = UserManager.getInstance(mContext).getUser();
        if (mUser != null) {
            String photoUrl = mUser.getIconUrl();
            GlideUtil.getInstance().loadPhotoUrl(photoUrl,iv_personal_mine_photo);
        }

        BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
        if (buShoppingInfo== null){
            return;
        }
        int authState = buShoppingInfo.getAuthState();
        if (authState!=1){
            return;
        }
        initShoppingView(buShoppingInfo);
    }

    private void initShoppingView(BUShoppingInfo buShoppingInfo) {
        et_bu_shopping_name.setText(buShoppingInfo.getName());
        et_bu_shopping_address.setText(buShoppingInfo.getAddress());
        et_bu_shopping_sell_type.setText(buShoppingInfo.getMainCategory());
        et_bu_shopping_nickname.setText(buShoppingInfo.getSign());
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("店铺信息");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnRight.setOnClickListener(this);
        iv_personal_mine_photo.setOnClickListener(this);

        et_bu_shopping_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifyView(Constants.USER_MODIFY_TYPE_SHOPPING_NAME);
                return true;
            }
        });

        et_bu_shopping_sell_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifyView(Constants.USER_MODIFY_TYPE_SHOPPING_MAIN);
                return true;
            }
        });

        et_bu_shopping_nickname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifyView(Constants.USER_MODIFY_TYPE_SHOPPING_SIGN);
                return true;
            }
        });


        /*养殖地址*/
        et_bu_shopping_address.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                AppManager.getInstance().jumpActivityForResult(BUMineShoppingMessageActivity.this, LocationActivity.class, null, SHOW_ADD_LOCATION_ACTIVITY_RESULT);
                return true;
            }
        });


    }

    private void showModifyView(int modifyType) {
        isShowModifyView = true;
        mModifyUserInfoFragment = ModifyUserInfoFragment.newInstance(modifyType);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, mModifyUserInfoFragment).commit();
        mModifyUserInfoFragment.setOnFragmentListener(new ModifyUserInfoFragment.OnFragmentListener() {
            @Override
            public void hideFragment() {
                isShowModifyView = false;
                getSupportFragmentManager().beginTransaction().hide(mModifyUserInfoFragment).commit();
            }
        });
    }


    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_right:
                updateShoppingFromServer();
                break;
            case R.id.iv_personal_mine_photo:
                changePhoto();
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



    /**
     * 更新店铺信息
     */
    private void updateShoppingFromServer() {
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
        param.put("id", buShoppingInfo.getId());
        param.put("address", buShoppingInfo.getAddress());

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_APPLY_SHOPPING_SANE, new BaseCallBack<BUShoppingInfoResult>() {
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
            protected void onSuccess(Call call, Response response, BUShoppingInfoResult result) {
                hideLoading();
                if (result.getCode() == 1) {
                    ShoppingInfoManager.getInstance().setBuShoppingInfo(result.getData());
//                    MessageEvent messageEvent = new MessageEvent();
//                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_UPDATE_SHOPPING_INFO);
//                    EventBus.getDefault().post(messageEvent);
//                    if (mOnFragmentListener != null) {
//                        KeyboardUtils.hideSoftInput(mEtInput);
//                        mOnFragmentListener.hideFragment();
//                    }
                }else{
                    ToastUtil.showToast(mContext, result.getMsg());
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
        }, param, token);
    }

    /**
     * 用更改用户信息
     */
    private void hasChangeUserInfo() {
        mBtnRight.setVisibility(View.VISIBLE);
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
                            compressImage(tempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
                    iv_personal_mine_photo.setImageBitmap(bitmap);
                    uploadPicture(UPLOAD_FILE_MODE_1);
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
                                compressImage(imagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
                        iv_personal_mine_photo.setImageBitmap(bitmap);
                        uploadPicture(UPLOAD_FILE_MODE_1);
                    }
                }
                break;

            case SHOW_ADD_LOCATION_ACTIVITY_RESULT:
                if (intent != null) {
                    mPoiItem = intent.getParcelableExtra("PoiItem");
                    String title = "不显示我的位置";
                    if (title.equals(mPoiItem.getTitle())) {
                        et_bu_shopping_address.setText(null);
                    } else {
                        et_bu_shopping_address.setText(mPoiItem.getTitle());
                        ShoppingInfoManager.getInstance().getBuShoppingInfo().setAddress((mPoiItem.getTitle()));
                        hasChangeUserInfo();
                    }
                }
                break;


//            case CROP_SMALL_PICTURE:  //调用剪裁后返回
//                if (resultCode == RESULT_OK) {
//                    if (intent == null)
//                        return;
//                    File tempCoverFile = new File(PathUtils.getPersonalCoverImageDirectory(), PathUtils.getTempCoverImageName(mContext));
//                    Bitmap bitMap = BitmapFactory.decodeFile(tempCoverFile.getAbsolutePath());
////                    if (bitMap != null) {
////                        mIvPhoto.setImageBitmap(bitMap);
////                    }
//                    Matrix matrix = new Matrix();
//                    matrix.setScale(0.3f, 0.3f);
//                    Bitmap resultBitmap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),
//                            bitMap.getHeight(), matrix, true);
////                    showLoading();
////                    uploadUserInfo("icon", Util.bitmaptoString(resultBitmap));
//                }
//        }
        }
    }



    /**
     * 单图片上传
     *
     * @param uploadMode 1 用户头像
     */
    private void uploadPicture(final int uploadMode) {
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

                updateUserInfo(data.getId());

//                hasChangeUserInfo();

//                switch (uploadMode) {
//                    case UPLOAD_FILE_MODE_1:
//                        mUserParamInfo.setIconFileId(data.getId());
////                        RequestOptions options = new RequestOptions();
////                        options.centerCrop();
////                        options.placeholder(R.mipmap.ic_default_photo);
////                        Glide.with(mContext)
////                                .asBitmap()
////                                .load(data.getUrl())
////                                .apply(options)
////                                .into(mIvPersonalMinePhoto);
//                        break;
////                    case TYPE_SERVICE_VACCINE:
////                    case TYPE_SERVICE_CUT_WOOL:
////                        publishService(String.valueOf(data.getId()));
////                        break;
////                    case TYPE_SERVICE_LOOK_CAR:
////                        publishFindCarService(String.valueOf(data.getId()));
////                        break;
//                    default:
//                        break;
//                }

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
     * 更新用户信息
     */
    public void updateUserInfo(int iconFileId) {

        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("iconFileId", iconFileId);

        OkHttpManager.getInstance().postRequest(HttpUrl.URL_UPDATE_USER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "发布失败");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null && result.getCode() == 1) {
                    BitmapUtils.deleteCacheFile();
                    UserManager.getInstance(mContext).setUser(result.getData());
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO);
                    EventBus.getDefault().post(messageEvent);
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



    @Override
    protected void eventBusUpdateUI(MessageEvent event) {
        super.eventBusUpdateUI(event);
        int eventType = event.getEventType();
        switch (eventType) {
            case MESSAGE_TYPE_UPDATE_SHOPPING_INFO:
                updateShoppingUI();
                break;
        }
    }

    private void updateShoppingUI() {
        initData();
    }

}