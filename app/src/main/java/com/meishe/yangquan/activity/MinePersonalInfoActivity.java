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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFileResult;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.bean.UserParamInfo;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.fragment.ModifyUserInfoFragment;
import com.meishe.yangquan.fragment.ModifyUserSexFragment;
import com.meishe.yangquan.helper.BackHandlerHelper;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.RoundAngleImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO;
import static com.meishe.yangquan.utils.Constants.UPLOAD_FILE_MODE_1;

/**
 * 我的-个人信息页面
 * @author 86188
 */
public class MinePersonalInfoActivity extends BaseActivity {


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


    private RoundAngleImageView mIvPersonalMinePhoto;
    /**
     * 更新用户信息参数列表
     */
    private UserParamInfo mUserParamInfo = new UserParamInfo();
    private Button mBtnRight;
    private File mTempFile;

    /*昵称*/
    private EditText mEtNickname;
    /*性别*/
    private EditText mEtPersonalSex;
    /*养殖地址*/
    private EditText mEtbreedAddress;
    /*养殖规模*/
    private EditText et_input_breeding_scale;
    /*养殖年限*/
    private EditText et_input_breeding_years;
    /*存栏量*/
    private EditText et_input_breeding_inventory;

    private UserInfo mUser;

    private FrameLayout mContainer;

    private ModifyUserInfoFragment mModifyUserInfoFragment;

    private boolean isShowModifyView = false;

    private PoiItem mPoiItem = null;
    private ModifyUserSexFragment mModifyUserSexInfoFragment;

    @Override
    protected int initRootView() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnRight = findViewById(R.id.btn_right);
        mBtnRight.setText("完成");
        mBtnRight.setVisibility(View.GONE);

        mEtNickname = findViewById(R.id.et_nickname);
        mEtPersonalSex = findViewById(R.id.et_personal_sex);
        mEtbreedAddress = findViewById(R.id.et_input_breeding_address);
        et_input_breeding_scale = findViewById(R.id.et_input_breeding_scale);
        et_input_breeding_years = findViewById(R.id.et_input_breeding_years);
        et_input_breeding_inventory = findViewById(R.id.et_input_breeding_inventory);
        mContainer = findViewById(R.id.container);

        mIvPersonalMinePhoto = findViewById(R.id.iv_personal_mine_photo);
    }


    @Override
    public void initData() {
        mUser = UserManager.getInstance(mContext).getUser();
        if (mUser != null) {

            updateUserUI();

            String photoUrl = mUser.getIconUrl();
            RequestOptions options = new RequestOptions();
            options.circleCrop();
            options.placeholder(R.mipmap.ic_default_photo);
            Glide.with(mContext)
                    .asBitmap()
                    .load(photoUrl)
                    .apply(options)
                    .into(mIvPersonalMinePhoto);

        }
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("个人信息");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mIvPersonalMinePhoto.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);


        mEtNickname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifyView(Constants.USER_MODIFY_TYPE_NICKNAME);
                return true;
            }
        });

        /*养殖规模*/
        et_input_breeding_scale.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifyView(Constants.USER_MODIFY_TYPE_SCALE);
                return true;
            }
        });

        /*养殖年限*/
        et_input_breeding_years.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifyView(Constants.USER_MODIFY_TYPE_YEAR);
                return true;
            }
        });

        /*存栏量*/
        et_input_breeding_inventory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifyView(Constants.USER_MODIFY_TYPE_QUANTITY);
                return true;
            }
        });


        mEtPersonalSex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                showModifySexView();
                return true;
            }
        });

        /*养殖地址*/
        mEtbreedAddress.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                AppManager.getInstance().jumpActivityForResult(MinePersonalInfoActivity.this, MineAddLocationActivity.class, null, SHOW_ADD_LOCATION_ACTIVITY_RESULT);
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

    private void showModifySexView() {
        isShowModifyView = true;
        mModifyUserSexInfoFragment = ModifyUserSexFragment.onCreate();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, mModifyUserSexInfoFragment).commit();
        mModifyUserSexInfoFragment.setOnFragmentListener(new ModifyUserSexFragment.OnFragmentListener() {
            @Override
            public void hideFragment() {
                isShowModifyView = false;
                getSupportFragmentManager().beginTransaction().hide(mModifyUserSexInfoFragment).commit();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_personal_mine_photo:
                if (Util.isFastDoubleClick()) {
                    return;
                }
                changePhoto();
                break;
            case R.id.btn_right:
                updateUserInfo();
                break;
        }
    }


    private void changePhoto() {
        new BottomMenuFragment(MinePersonalInfoActivity.this)
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

                hasChangeUserInfo();

                switch (uploadMode) {
                    case UPLOAD_FILE_MODE_1:
                        mUserParamInfo.setIconFileId(data.getId());
//                        RequestOptions options = new RequestOptions();
//                        options.centerCrop();
//                        options.placeholder(R.mipmap.ic_default_photo);
//                        Glide.with(mContext)
//                                .asBitmap()
//                                .load(data.getUrl())
//                                .apply(options)
//                                .into(mIvPersonalMinePhoto);
                        break;
//                    case TYPE_SERVICE_VACCINE:
//                    case TYPE_SERVICE_CUT_WOOL:
//                        publishService(String.valueOf(data.getId()));
//                        break;
//                    case TYPE_SERVICE_LOOK_CAR:
//                        publishFindCarService(String.valueOf(data.getId()));
//                        break;
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
     * 更新用户信息
     */
    public void updateUserInfo() {
        if (mUserParamInfo == null) {
            return;
        }
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        Integer iconFileId = mUserParamInfo.getIconFileId();
        if (iconFileId != null) {
            requestParam.put("iconFileId", iconFileId);
        }
        String culturalAddress = mUserParamInfo.getCulturalAddress();
        if (!TextUtils.isEmpty(culturalAddress)) {
            requestParam.put("culturalAddress", culturalAddress);
        }

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
                    mUserParamInfo.clear();
                    BitmapUtils.deleteCacheFile();
                    UserManager.getInstance(mContext).setUser(result.getData());
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO);
                    EventBus.getDefault().post(messageEvent);
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
                    mIvPersonalMinePhoto.setImageBitmap(bitmap);
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
                        mIvPersonalMinePhoto.setImageBitmap(bitmap);
                        uploadPicture(UPLOAD_FILE_MODE_1);
                    }
                }
                break;

            case SHOW_ADD_LOCATION_ACTIVITY_RESULT:
                if (intent != null) {
                    mPoiItem = intent.getParcelableExtra("PoiItem");
                    String title = "不显示我的位置";
                    if (title.equals(mPoiItem.getTitle())) {
                        mEtbreedAddress.setText(null);
                    } else {
                        mEtbreedAddress.setText(mPoiItem.getTitle());
                        mUserParamInfo.setCulturalAddress(mPoiItem.getTitle());
                        hasChangeUserInfo();
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

    /**
     * 裁剪图片方法实现
     */
    private void startPhotoZoom() {
        Intent intent = new Intent(MinePersonalInfoActivity.this, ClipCircleImageActivity.class);
        intent.putExtra(IMAGE_PATH_ORIGINAL, tempFile.getAbsolutePath());
        startActivityForResult(intent, CROP_SMALL_PICTURE);

    }


    @Override
    protected void eventBusUpdateUI(MessageEvent event) {
        super.eventBusUpdateUI(event);
        int eventType = event.getEventType();
        switch (eventType) {
            case MESSAGE_TYPE_UPDATE_USER_INFO:
                updateUserUI();
                break;
        }
    }

    private void updateUserUI() {
        mUser = UserManager.getInstance(mContext).getUser();
        if (mUser != null) {
            mEtNickname.setText(mUser.getNickname());
            Integer gender = mUser.getGender();
            if (gender !=null){
                mEtPersonalSex.setText(gender == 0 ? "女" : "男");
            }
            mEtbreedAddress.setText(mUser.getCulturalAddress() == null ? "" : mUser.getCulturalAddress());
            et_input_breeding_scale.setText(mUser.getCulturalScale() < 0 ? "" : mUser.getCulturalScale() + "");
            et_input_breeding_years.setText(mUser.getCulturalAge() < 0 ? "" : mUser.getCulturalAge() + "");
            et_input_breeding_inventory.setText(mUser.getCurrentCulturalQuantity() < 0 ? "" : mUser.getCurrentCulturalQuantity() + "");
        }
    }


    @Override
    public void onBackPressed() {
        if (!isShowModifyView) {
            super.onBackPressed();
            return;
        }
        if (!BackHandlerHelper.handleBackPress(this)) {
            isShowModifyView = false;

            if (mModifyUserInfoFragment != null) {
                getSupportFragmentManager().beginTransaction().hide(mModifyUserInfoFragment).commit();
            }
            if (mModifyUserSexInfoFragment != null) {
                getSupportFragmentManager().beginTransaction().hide(mModifyUserSexInfoFragment).commit();
            }
            return;
        } else {
            super.onBackPressed();
        }
    }


}
