package com.meishe.yangquan.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFileResult;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.bean.UserParamInfo;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 完善驾驶信息页面
 */
public class MineDriverActivity extends BaseCaptureActivity {

    /**
     * 更新用户信息参数列表
     */
    private UserParamInfo mUserParamInfo = new UserParamInfo();

    private View rl_driver_positive;
    private View rl_driver_negative;

    private ImageView iv_driver_positive;
    private ImageView iv_driver_negative;
    private Button mBtnRight;

    private int mType;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_driver;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        rl_driver_positive = findViewById(R.id.rl_driver_positive);
        rl_driver_negative = findViewById(R.id.rl_driver_negative);

        iv_driver_positive = findViewById(R.id.iv_driver_positive);
        iv_driver_negative = findViewById(R.id.iv_driver_negative);

        mBtnRight = findViewById(R.id.btn_right);
        mBtnRight.setText("完成");

    }

    @Override
    public void initData() {
        UserInfo user = UserManager.getInstance(mContext).getUser();
        if (user != null) {
            String idCardFrontUrl = user.getDriveCardFrontUrl();
            String idCardReverseUrl = user.getDriveCardReverseUrl();

            GlideUtil.getInstance().loadUrl(idCardFrontUrl,iv_driver_positive);


            GlideUtil.getInstance().loadUrl(idCardReverseUrl,iv_driver_negative);

        }
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("驾驶证上传");
    }

    @Override
    public void initListener() {
        rl_driver_positive.setOnClickListener(this);
        rl_driver_negative.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
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
            case R.id.rl_driver_positive:
                mType = 1;
                changePhoto();
                break;
            case R.id.rl_driver_negative:
                mType = 2;
                changePhoto();
                break;
            case R.id.btn_right:
                updateUserInfo();
                break;

        }
    }

    @Override
    protected void captureOrAlbumResult(String filePath) {
        hasChangeUserInfo();
        RequestOptions options = new RequestOptions();
        if (mType == 1) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(filePath)
                    .apply(options)
                    .into(iv_driver_positive);

            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }
            uploadPicture(Constants.UPLOAD_FILE_MODE_8, file);
        } else if (mType == 2) {
            GlideUtil.getInstance().loadUrl(filePath,iv_driver_negative);
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }
            uploadPicture(Constants.UPLOAD_FILE_MODE_9, file);
        }
    }


    /**
     * 用更改用户信息
     */
    private void hasChangeUserInfo() {
        mBtnRight.setVisibility(View.VISIBLE);
    }

    /**
     * 单图片上传
     *
     * @param uploadMode 1 用户头像
     */
    private void uploadPicture(final int uploadMode, File file) {
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
                    case Constants.UPLOAD_FILE_MODE_8:
                        mUserParamInfo.setDriveCardFrontFileId(data.getId());
                        break;
                    case Constants.UPLOAD_FILE_MODE_9:
                        mUserParamInfo.setDriveCardReverseFileId(data.getId());
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
        }, file, "file", param, token);
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        if (mUserParamInfo.getDriveCardFrontFileId() != null &&
                mUserParamInfo.getDriveCardFrontFileId() > 0) {
            requestParam.put("driveCardFrontFileId", mUserParamInfo.getDriveCardFrontFileId());
        }
        if (mUserParamInfo.getDriveCardReverseFileId() != null &&
                mUserParamInfo.getDriveCardReverseFileId() > 0) {
            requestParam.put("driveCardReverseFileId", mUserParamInfo.getDriveCardReverseFileId());
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
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null && result.getCode() == 1) {
                    BitmapUtils.deleteCacheFile();
                    UserManager.getInstance(mContext).setUser(result.getData());
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
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }

}