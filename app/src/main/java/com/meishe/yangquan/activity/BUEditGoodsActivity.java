package com.meishe.yangquan.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BUGoodsInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 编辑商品
 * 只修改 名称库存  价格  这三个  如果大修改，那就重新发布商品
 */
public class BUEditGoodsActivity extends BaseActivity {

    /*标题字数限制*/
    private int MAX_LENGTH_TITLE = 30;

    private EditText et_bu_input_title;
    private TextView tv_number_limit_title;
    private EditText et_bu_input_goods_store_amount;
    private EditText et_bu_input_goods_price;
    private BUGoodsInfo mBuGoodsInfo;
    private Button btn_right;


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_edit_goods;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        btn_right = findViewById(R.id.btn_right);
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("保存");

        //商品标题
        et_bu_input_title = findViewById(R.id.et_bu_input_title);
        tv_number_limit_title = findViewById(R.id.tv_number_limit_title);

        //库存
        et_bu_input_goods_store_amount = findViewById(R.id.et_bu_input_goods_store_amount);
        //价格
        et_bu_input_goods_price = findViewById(R.id.et_bu_input_goods_price);


    }

    @Override
    public void initData() {
        mBuGoodsInfo = UserManager.getInstance(mContext).getBuGoodsInfo();
        if (mBuGoodsInfo == null) {
            return;
        }

        et_bu_input_title.setText(mBuGoodsInfo.getTitle());
        et_bu_input_goods_store_amount.setText(mBuGoodsInfo.getStoreAmount()+"");
        et_bu_input_goods_price.setText(mBuGoodsInfo.getPrice() + "");

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("编辑商品");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().finishActivity();
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

        btn_right.setOnClickListener(this);

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_right) {
            String title = et_bu_input_title.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                ToastUtil.showToast("商品的标题必须填写");
                return;
            }

            String amount = et_bu_input_goods_store_amount.getText().toString().trim();
            if (TextUtils.isEmpty(amount)) {
                ToastUtil.showToast("商品的数量必须填写");
                return;
            }

            String price = et_bu_input_goods_price.getText().toString().trim();
            if (TextUtils.isEmpty(price)) {
                ToastUtil.showToast("商品的价格必须填写");
                return;
            }
            editGoods(title, amount, price);

        }
    }


    /**
     * 编辑商品
     */
    private void editGoods(String title, String amount, String price) {

        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("id", mBuGoodsInfo.getId());
        requestParam.put("title", title);
        requestParam.put("isPublic", 0);
        requestParam.put("storeAmount", amount);
        requestParam.put("price", price);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_GOODS_ADD, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result != null && result.getCode() == 1) {
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_BU_PUBLISH_GOODS_SUCCESS);
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