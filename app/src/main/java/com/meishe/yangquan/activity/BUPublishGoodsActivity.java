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
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUPictureInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.bean.MenuItem;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.fragment.BottomMenuFragment;
import com.meishe.yangquan.pop.BUSelectGoodsSpecsView;
import com.meishe.yangquan.pop.BUSelectGoodsTypeView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BitmapUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.PathUtils;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 商版发布商品
 */
public class BUPublishGoodsActivity extends BaseActivity {


    public static final int SHOW_ADD_LOCATION_ACTIVITY_RESULT = 300;
    public static final int SHOW_ADD_LOCATION_ACTIVITY_RESULT_ADDRESS = 301;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 203;   //拍摄存储权限
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 201;
    private File tempFile;

    /*标题字数限制*/
    private int MAX_LENGTH_TITLE = 30;
    /*描述字数限制*/
    private int MAX_LENGTH_DESC = 40;

    private List<BUPictureInfo> mCoverPictureList = new ArrayList<>();
    private List<BUPictureInfo> mDescPictureList = new ArrayList<>();
    private RecyclerView mDescRecyclerView;
    private MultiFunctionAdapter mDescAdapter;
    private int mMaxPictureAmount;
    /*1:上传封面图片 2：上传描述图片*/
    private int mAddPictureType;

    private EditText et_bu_input_desc;
    private EditText et_bu_input_title;
    private TextView tv_number_limit_title;
    private TextView tv_number_limit_desc;
    /*商品类型*/
    private EditText et_bu_select_goods_type;
    /*选择产地*/
    private EditText et_bu_select_goods_place_of_origin;
    private PoiItem mPoiItem;
    private BUSelectGoodsTypeView mBuSelectGoodsTypeView;
    /*品牌*/
    private EditText et_bu_input_goods_brand;

    /*重量*/
    private EditText et_bu_input_goods_weight;
    /*重量规格*/
    private EditText et_bu_select_goods_weight_specs;
    /*价格*/
    private EditText et_bu_input_goods_price;
    /*库存*/
    private EditText et_bu_input_goods_store_amount;
    /*库存规格*/
    private EditText et_bu_select_goods_store_amount_specs;
    /*发货地*/
    private EditText et_bu_select_goods_address;
    /*发布*/
    private View btn_bu_goods_publish;

    /*库存规格*/
    private List<String> mStoreAmountSpecsList=new ArrayList<>();
    /*重量规格*/
    private List<String> mWeightSpecsList=new ArrayList<>();

    private BUSelectGoodsSpecsView mBUSelectGoodsStoreAmountSpecsView;
    private BUSelectGoodsSpecsView mBUSelectGoodsWeightSpecsView;


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_publish_goods;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        /*用于封面图片的上传*/
        mRecyclerView = findViewById(R.id.recycler);
        /*用于描述图片的上传*/
        mDescRecyclerView = findViewById(R.id.recycler_desc);


        et_bu_input_desc = findViewById(R.id.et_bu_input_desc);
        et_bu_input_title = findViewById(R.id.et_bu_input_title);
        tv_number_limit_title = findViewById(R.id.tv_number_limit_title);
        tv_number_limit_desc = findViewById(R.id.tv_number_limit_desc);

        /*商品类型*/
        et_bu_select_goods_type = findViewById(R.id.et_bu_select_goods_type);
        /*选择产地*/
        et_bu_select_goods_place_of_origin = findViewById(R.id.et_bu_select_goods_place_of_origin);
        /*品牌*/
        et_bu_input_goods_brand = findViewById(R.id.et_bu_input_goods_brand);

        /*重量*/
        et_bu_input_goods_weight = findViewById(R.id.et_bu_input_goods_weight);
        /*重量规格*/
        et_bu_select_goods_weight_specs = findViewById(R.id.et_bu_select_goods_weight_specs);
        /*价格*/
        et_bu_input_goods_price = findViewById(R.id.et_bu_input_goods_price);
        /*库存*/
        et_bu_input_goods_store_amount = findViewById(R.id.et_bu_input_goods_store_amount);
        /*数量规格*/
        et_bu_select_goods_store_amount_specs = findViewById(R.id.et_bu_select_goods_store_amount_specs);
        /*发货地*/
        et_bu_select_goods_address = findViewById(R.id.et_bu_select_goods_address);
        btn_bu_goods_publish = findViewById(R.id.btn_bu_goods_publish);

        initCoverRecyclerView();
        initDescRecyclerView();
    }

    private void initCoverRecyclerView() {
        GridLayoutManager layoutManager =
                new GridLayoutManager(mContext, 4);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new CustomGridItemDecoration(ScreenUtils.dip2px(mContext, 3)));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDescRecyclerView() {
        GridLayoutManager descLayoutManager =
                new GridLayoutManager(mContext, 4);
        mDescAdapter = new MultiFunctionAdapter(mContext, mDescRecyclerView);
        mDescRecyclerView.setLayoutManager(descLayoutManager);
        mDescRecyclerView.addItemDecoration(new CustomGridItemDecoration(ScreenUtils.dip2px(mContext, 3)));
        mDescRecyclerView.setAdapter(mDescAdapter);
    }

    @Override
    public void initData() {
        mMaxPictureAmount = 3;
        BUPictureInfo buPictureInfo = new BUPictureInfo();
        buPictureInfo.setFilePath(String.valueOf(R.mipmap.ic_sheep_bar_add));
        buPictureInfo.setType(CommonPictureInfo.TYPE_ADD_PIC);
        mCoverPictureList.add(buPictureInfo);
        mAdapter.addAll(mCoverPictureList);

        mDescPictureList.add(buPictureInfo);
        mDescAdapter.addAll(mDescPictureList);

        mStoreAmountSpecsList.add("桶");
        mStoreAmountSpecsList.add("袋");
        mStoreAmountSpecsList.add("箱");
        mStoreAmountSpecsList.add("瓶");

        mWeightSpecsList.add("斤");
        mWeightSpecsList.add("公斤");
        mWeightSpecsList.add("吨");
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("发布商品");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_bu_goods_publish.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof BUPictureInfo && ((BUPictureInfo) baseInfo).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                    if (mCoverPictureList != null && mCoverPictureList.size() > mMaxPictureAmount) {
                        ToastUtil.showToast(mContext, "最多添加" + mMaxPictureAmount + "张图片");
                        return;
                    }
                    mAddPictureType = 1;
                    showPictureSelectItem();
                }
            }
        });

        mDescAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof BUPictureInfo && ((BUPictureInfo) baseInfo).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                    if (mDescPictureList != null && mDescPictureList.size() > mMaxPictureAmount) {
                        ToastUtil.showToast(mContext, "最多添加" + mMaxPictureAmount + "张图片");
                        return;
                    }
                    mAddPictureType = 2;
                    showPictureSelectItem();
                }
            }
        });


        et_bu_input_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                String str = s.toString();
                if (length > MAX_LENGTH_TITLE) {
                    et_bu_input_title.setText(str.substring(0, MAX_LENGTH_TITLE));
                    et_bu_input_title.requestFocus();
                    et_bu_input_title.setSelection(et_bu_input_title.getText().length());
                } else {
                    int i = MAX_LENGTH_TITLE - length;
                    tv_number_limit_title.setText(String.valueOf(i));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        et_bu_input_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                String str = s.toString();
                if (length > MAX_LENGTH_DESC) {
                    et_bu_input_desc.setText(str.substring(0, MAX_LENGTH_DESC));
                    et_bu_input_desc.requestFocus();
                    et_bu_input_desc.setSelection(et_bu_input_desc.getText().length());
                } else {
                    int i = MAX_LENGTH_DESC - length;
                    tv_number_limit_desc.setText(String.valueOf(i));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        et_bu_select_goods_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                if (mBuSelectGoodsTypeView==null){
                    mBuSelectGoodsTypeView = BUSelectGoodsTypeView.create(mContext, new BUSelectGoodsTypeView.OnAttachListener() {
                        @Override
                        public void onSelectGoodsType(String goodsType, String subGoodsType) {
                            et_bu_select_goods_type.setText(goodsType+"-"+subGoodsType);
                        }
                    });
                }
                if (mBuSelectGoodsTypeView.isShow()){
                    return true;
                }
                mBuSelectGoodsTypeView.show();
                return true;
            }
        });




        et_bu_select_goods_store_amount_specs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                if (mBUSelectGoodsStoreAmountSpecsView ==null){
                    mBUSelectGoodsStoreAmountSpecsView = BUSelectGoodsSpecsView.create(mContext, mStoreAmountSpecsList,new BUSelectGoodsSpecsView.OnAttachListener() {
                        @Override
                        public void onSelectGoodsType(String goodsType) {
                            et_bu_select_goods_store_amount_specs.setText(goodsType);
                        }
                    });
                }
                if (mBUSelectGoodsStoreAmountSpecsView.isShow()){
                    return true;
                }
                mBUSelectGoodsStoreAmountSpecsView.show();
                return true;
            }
        });

        et_bu_select_goods_weight_specs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                if (mBUSelectGoodsWeightSpecsView ==null){
                    mBUSelectGoodsWeightSpecsView = BUSelectGoodsSpecsView.create(mContext, mWeightSpecsList,new BUSelectGoodsSpecsView.OnAttachListener() {
                        @Override
                        public void onSelectGoodsType(String goodsType) {
                            et_bu_select_goods_weight_specs.setText(goodsType);
                        }
                    });
                }
                if (mBUSelectGoodsWeightSpecsView.isShow()){
                    return true;
                }
                mBUSelectGoodsWeightSpecsView.show();
                return true;
            }
        });


        et_bu_select_goods_place_of_origin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                AppManager.getInstance().jumpActivityForResult(BUPublishGoodsActivity.this, MineAddLocationActivity.class, null, SHOW_ADD_LOCATION_ACTIVITY_RESULT);
                return true;
            }
        });

        et_bu_select_goods_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Util.isFastDoubleClick()) {
                    return true;
                }
                AppManager.getInstance().jumpActivityForResult(BUPublishGoodsActivity.this, MineAddLocationActivity.class, null, SHOW_ADD_LOCATION_ACTIVITY_RESULT_ADDRESS);
                return true;
            }
        });

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_bu_goods_publish){
            //发布

        }
    }


    private void showPictureSelectItem() {
        new BottomMenuFragment(BUPublishGoodsActivity.this)
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
                    String filePath = BitmapUtils.compressImageUpload(tempFile.getAbsolutePath(), Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);

                    BUPictureInfo buPictureInfo = new BUPictureInfo();
                    buPictureInfo.setFilePath(filePath);
                    buPictureInfo.setType(CommonPictureInfo.TYPE_CAPTURE_PIC);
                    addPicture(buPictureInfo);
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
                        File file = new File(imagePath);
                        if (!file.exists()) {
                            return;
                        }
                        String filePath = BitmapUtils.compressImageUpload(imagePath, Constants.COMPRESS_WIDTH, Constants.COMPRESS_HEIGHT);
                        BUPictureInfo buPictureInfo = new BUPictureInfo();
                        buPictureInfo.setFilePath(filePath);
                        buPictureInfo.setType(CommonPictureInfo.TYPE_CAPTURE_PIC);
                        addPicture(buPictureInfo);
                    }
                }
                break;
            case SHOW_ADD_LOCATION_ACTIVITY_RESULT:
                if (intent != null) {
                    mPoiItem = intent.getParcelableExtra("PoiItem");
                    String title = "不显示我的位置";
                    if (title.equals(mPoiItem.getTitle())) {
                    } else {
                        et_bu_select_goods_place_of_origin.setText(mPoiItem.getTitle());
                    }
                }
                break;
            case SHOW_ADD_LOCATION_ACTIVITY_RESULT_ADDRESS:
                if (intent != null) {
                    mPoiItem = intent.getParcelableExtra("PoiItem");
                    String title = "不显示我的位置";
                    if (title.equals(mPoiItem.getTitle())) {
                    } else {
                        et_bu_select_goods_address.setText(mPoiItem.getTitle());
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

    private void addPicture(BUPictureInfo buPictureInfo) {
        if (mAddPictureType == 1) {
            mCoverPictureList.add(0, buPictureInfo);
            mAdapter.addAll(mCoverPictureList);
        } else if (mAddPictureType == 2) {
            mDescPictureList.add(0, buPictureInfo);
            mDescAdapter.addAll(mDescPictureList);
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