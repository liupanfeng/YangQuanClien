package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.amap.api.services.core.PoiItem;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.BUShoppingInfoResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.fragment.ModifyUserInfoFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_SHOPPING_INFO;

/**
 * 商版-我的-店铺信息
 */
public class BUMineShoppingMessageActivity extends BaseActivity {

    public static final int SHOW_ADD_LOCATION_ACTIVITY_RESULT = 300;

    /*店铺名称*/
    private EditText et_bu_shopping_name;
    private EditText et_bu_shopping_address;
    private EditText et_bu_shopping_sell_type;
    private EditText et_bu_shopping_nickname;

    private boolean isShowModifyView = false;
    private ModifyUserInfoFragment mModifyUserInfoFragment;
    private PoiItem mPoiItem;
    private Button mBtnRight;

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
    }

    @Override
    public void initData() {
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
//            case CAMERA_REQUEST_CODE:   //调用相机后返回
//                if (tempFile != null && tempFile.exists()) {
//                    String compressImagePath = BitmapUtils.
//                            compressImageUpload(tempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
//                    mTempFile = new File(compressImagePath);
//                    if (!mTempFile.exists()) {
//                        return;
//                    }
//                    Bitmap bitmap = BitmapUtils.
//                            compressImage(tempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
//                    mIvPersonalMinePhoto.setImageBitmap(bitmap);
//                    uploadPicture(UPLOAD_FILE_MODE_1);
//                }
//                break;
//
//            case ALBUM_REQUEST_CODE:    //调用相册后返回
//                if (resultCode == RESULT_OK) {
//                    String imagePath = null;
//                    if (intent == null)
//                        return;
//                    Uri uri = intent.getData();
//                    if (uri == null)
//                        return;
//                    if (DocumentsContract.isDocumentUri(this, uri)) {
//                        //如果是document类型的Uri,则通过document id处理
//                        String docId = DocumentsContract.getDocumentId(uri);
//                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                            String id = docId.split(":")[1];//解析出数字格式的id
//                            String selection = MediaStore.Images.Media._ID + "=" + id;
//                            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//                        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
//                            imagePath = getImagePath(contentUri, null);
//                        }
//                    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//                        //如果是content类型的URI，则使用普通方式处理
//                        imagePath = getImagePath(uri, null);
//                    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//                        //如果是file类型的Uri,直接获取图片路径即可
//                        imagePath = uri.getPath();
//                    }
//                    if (imagePath != null) {
//                        String compressImagePath = BitmapUtils.compressImageUpload(imagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
//                        mTempFile = new File(compressImagePath);
//                        if (!mTempFile.exists()) {
//                            return;
//                        }
//                        Bitmap bitmap = BitmapUtils.
//                                compressImage(imagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
//                        mIvPersonalMinePhoto.setImageBitmap(bitmap);
//                        uploadPicture(UPLOAD_FILE_MODE_1);
//                    }
//                }
//                break;

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