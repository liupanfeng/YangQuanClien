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
 * 完善身份证信息
 */
public class MineIdCardActivity extends BaseCaptureActivity {


    private View rl_bu_capture_card_positive;
    private View rl_bu_capture_card_negative;

    private ImageView iv_bu_capture_card_positive;
    private ImageView iv_bu_capture_card_negative;
    private Button mBtnRight;

    private int mType;
    /**
     * 更新用户信息参数列表
     */
    private UserParamInfo mUserParamInfo = new UserParamInfo();

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_id_card;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        rl_bu_capture_card_positive = findViewById(R.id.rl_bu_capture_card_positive);
        rl_bu_capture_card_negative = findViewById(R.id.rl_bu_capture_card_negative);

        iv_bu_capture_card_positive = findViewById(R.id.iv_bu_capture_card_positive);
        iv_bu_capture_card_negative = findViewById(R.id.iv_bu_capture_card_negative);
        mBtnRight = findViewById(R.id.btn_right);
        mBtnRight.setText("完成");
    }

    @Override
    public void initData() {
        UserInfo user = UserManager.getInstance(mContext).getUser();
        if (user != null) {
            RequestOptions options = new RequestOptions();
            String idCardFrontUrl = user.getIdCardFrontUrl();
            String idCardReverseUrl = user.getIdCardReverseUrl();

            GlideUtil.getInstance().loadUrl(idCardFrontUrl,iv_bu_capture_card_positive);
            GlideUtil.getInstance().loadUrl(idCardReverseUrl,iv_bu_capture_card_negative);

        }
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("身份证上传");
    }

    @Override
    public void initListener() {
        rl_bu_capture_card_positive.setOnClickListener(this);
        rl_bu_capture_card_negative.setOnClickListener(this);
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
            case R.id.rl_bu_capture_card_positive:
                mType = 1;
                changePhoto();
                break;
            case R.id.rl_bu_capture_card_negative:
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
        if (mType == 1) {
            GlideUtil.getInstance().loadUrl(filePath,iv_bu_capture_card_positive);
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }
            uploadPicture(Constants.UPLOAD_FILE_MODE_2, file);
        } else if (mType == 2) {
            GlideUtil.getInstance().loadUrl(filePath,iv_bu_capture_card_negative);
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }
            uploadPicture(Constants.UPLOAD_FILE_MODE_3, file);
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
                    case Constants.UPLOAD_FILE_MODE_2:
                        mUserParamInfo.setIdCardFrontFileId(data.getId());
                        break;
                    case Constants.UPLOAD_FILE_MODE_3:
                        mUserParamInfo.setIdCardReverseFileId(data.getId());
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
        if (mUserParamInfo.getIdCardFrontFileId() != null &&
                mUserParamInfo.getIdCardFrontFileId() > 0) {
            requestParam.put("idCardFrontFileId", mUserParamInfo.getIdCardFrontFileId());
        }
        if (mUserParamInfo.getIdCardReverseFileId() != null &&
                mUserParamInfo.getIdCardReverseFileId() > 0) {
            requestParam.put("idCardReverseFileId", mUserParamInfo.getIdCardReverseFileId());
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