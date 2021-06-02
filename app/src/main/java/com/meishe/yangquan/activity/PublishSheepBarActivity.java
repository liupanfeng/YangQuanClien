package com.meishe.yangquan.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.bean.UploadFileInfo;
import com.meishe.yangquan.bean.UploadFilesResult;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.SelectCaptureTypeView;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 发布羊吧内容界面
 */
public class PublishSheepBarActivity extends BaseActivity {

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;
    private File tempFile;


    private static final int MAX_LENGTH = 150;

    private List<CommonPictureInfo> mData = new ArrayList<>();
    private Button mBtnPublish;
    private EditText mEtInputSheepBarMessage;
    private TextView tv_number_limit_title;

    @Override
    protected int initRootView() {
        return R.layout.activity_bar_sheep;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler);
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnPublish = findViewById(R.id.btn_publish);
        mEtInputSheepBarMessage = findViewById(R.id.et_input_sheep_bar_message);
        tv_number_limit_title = findViewById(R.id.tv_number_limit_title);
        mLoading = findViewById(R.id.loading);


        initGridRecyclerView();
    }

    private void initGridRecyclerView() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(mContext, 3);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(15);
        mRecyclerView.addItemDecoration(customGridItemDecoration);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mData.clear();
        CommonPictureInfo sheepBarMessageInfo = new CommonPictureInfo();
        sheepBarMessageInfo.setFilePath(String.valueOf(R.mipmap.ic_sheep_bar_add));
        sheepBarMessageInfo.setType(CommonPictureInfo.TYPE_ADD_PIC);
        mData.add(sheepBarMessageInfo);
        mAdapter.addAll(mData);
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("羊吧信息");

    }

    @Override
    public void initListener() {
        mBtnPublish.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof CommonPictureInfo) {
                    if (((CommonPictureInfo) baseInfo).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                        if (mData != null && mData.size() > 9) {
                            ToastUtil.showToast(mContext, "最多添加9张图片");
                            return;
                        }
                        showPictureSelectItem();
                    }
                }
            }
        });


        tv_number_limit_title.setText(MAX_LENGTH + "");

        mEtInputSheepBarMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                String str = s.toString();
                if (length > MAX_LENGTH) {
                    mEtInputSheepBarMessage.setText(str.substring(0, MAX_LENGTH));
                    mEtInputSheepBarMessage.requestFocus();
                    mEtInputSheepBarMessage.setSelection(mEtInputSheepBarMessage.getText().length());
                } else {
                    int i = MAX_LENGTH - length;
                    tv_number_limit_title.setText(String.valueOf(i));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                //发布
                uploadPictures();
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
            publishSheepBar("");
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
        param.put("uploadMode", "13");
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
                publishSheepBar(stringBuilder.toString());
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
     * 发布  羊吧信息
     *
     * @param pictureIds
     */
    public void publishSheepBar(String pictureIds) {
        String token = UserManager.getInstance(mContext).getToken();
        String content = mEtInputSheepBarMessage.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(mContext, "请输入内容！");
            hideLoading();
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("content", content);
        if (!TextUtils.isEmpty(pictureIds)) {
            requestParam.put("fileIds", pictureIds);
        }
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_INFO_SAVE, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "上传失败");
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast(mContext, "发布成功");
                    BitmapUtils.deleteCacheFile();
                    MessageEvent messageEvent=new MessageEvent();
                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_UPDATE_SHEEP_BAR);
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
                        hideLoading();
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
                    String filePath = BitmapUtils.compressImageUpload(tempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
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
                        String filePath = BitmapUtils.compressImageUpload(imagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
                        CommonPictureInfo sheepBarMessageInfo = new CommonPictureInfo();
                        sheepBarMessageInfo.setFilePath(filePath);
                        sheepBarMessageInfo.setType(CommonPictureInfo.TYPE_CAPTURE_PIC);
                        mData.add(0, sheepBarMessageInfo);
                        mAdapter.addAll(mData);
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
