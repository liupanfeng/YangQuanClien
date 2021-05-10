package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BUBusinessLicenseActivity;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.BUShoppingInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.helper.BackHandlerHelper;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.KeyboardUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.VISIBLE;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/25
 * @Description :
 */
public class ModifyUserInfoFragment extends BaseRecyclerFragment {

    public static final int MAX_LENGTH = 13;

    private TextView mTvTitle;
    private ImageView mIvBack;
    private Button mBtnRight;

    private EditText mEtInput;
    private TextView mTvNumberLimit;

    private OnFragmentListener mOnFragmentListener;

    private int mModifyType;
    private String mContent;

    public static ModifyUserInfoFragment newInstance(int type) {
        ModifyUserInfoFragment modifyUserInfoFragment = new ModifyUserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.USER_MODIFY_TYPE, type);
        modifyUserInfoFragment.setArguments(bundle);
        return modifyUserInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mModifyType = arguments.getInt(Constants.USER_MODIFY_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_modify_user_inof, container, false);
        mTvTitle = view.findViewById(R.id.tv_title);
        mIvBack = view.findViewById(R.id.iv_back);
        mBtnRight = view.findViewById(R.id.btn_right);
        mEtInput = view.findViewById(R.id.et_input);
        mTvNumberLimit = view.findViewById(R.id.tv_number_limit_title);
        mLoading = view.findViewById(R.id.loading);

        mBtnRight.setText("完成");
        mBtnRight.setVisibility(VISIBLE);

        return view;
    }

    @Override
    protected void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnFragmentListener != null) {
                    mOnFragmentListener.hideFragment();
                }
            }
        });

        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mModifyType) {
                    case Constants.USER_MODIFY_TYPE_NICKNAME:
                    case Constants.USER_MODIFY_TYPE_SCALE:
                    case Constants.USER_MODIFY_TYPE_YEAR:
                    case Constants.USER_MODIFY_TYPE_QUANTITY:
                        updateUserInfo();
                        break;
                    case Constants.USER_MODIFY_TYPE_SHOPPING_NAME:
                    case Constants.USER_MODIFY_TYPE_SHOPPING_MAIN:
                    case Constants.USER_MODIFY_TYPE_SHOPPING_SIGN:
                        updateShoppingFromServer();
                        break;
                }

            }
        });


        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                String str = s.toString();
                if (length > MAX_LENGTH) {
                    mEtInput.setText(str.substring(0, MAX_LENGTH));
                    mEtInput.requestFocus();
                    mEtInput.setSelection(mEtInput.getText().length());
                } else {
                    int i = MAX_LENGTH - length;
                    mTvNumberLimit.setText(String.valueOf(i));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void initData() {
        UserInfo user = UserManager.getInstance(getContext()).getUser();

        switch (mModifyType) {
            case Constants.USER_MODIFY_TYPE_NICKNAME:
                if (user == null) {
                    return;
                }
                mTvTitle.setText("设置昵称");
                mContent = user.getNickname();
                break;
            case Constants.USER_MODIFY_TYPE_SCALE:
                if (user == null) {
                    return;
                }
                mTvTitle.setText("设置养殖规模");
                mContent = user.getCulturalScale() < 0 ? "" : user.getCulturalScale() + "";
                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case Constants.USER_MODIFY_TYPE_YEAR:
                if (user == null) {
                    return;
                }
                mTvTitle.setText("设置养殖年限");
                mContent = user.getCulturalAge() < 0 ? "" : user.getCulturalAge() + "";
                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case Constants.USER_MODIFY_TYPE_QUANTITY:
                if (user == null) {
                    return;
                }
                mTvTitle.setText("设置存栏量");
                mContent = user.getCurrentCulturalQuantity() < 0 ? "" : user.getCurrentCulturalQuantity() + "";
                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case Constants.USER_MODIFY_TYPE_SHOPPING_NAME:
                mTvTitle.setText("修改店铺名称");
                BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
                if (buShoppingInfo == null) {
                    return;
                }
                mContent = buShoppingInfo.getName();
//                mContent = user.getCurrentCulturalQuantity() < 0 ? "" : user.getCurrentCulturalQuantity() + "";
//                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case Constants.USER_MODIFY_TYPE_SHOPPING_MAIN:
                mTvTitle.setText("修改店铺主营");
                buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
                if (buShoppingInfo == null) {
                    return;
                }
                mContent = buShoppingInfo.getMainCategory();
//                mContent = user.getCurrentCulturalQuantity() < 0 ? "" : user.getCurrentCulturalQuantity() + "";
//                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            case Constants.USER_MODIFY_TYPE_SHOPPING_SIGN:
                mTvTitle.setText("修改店铺签名");
                buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
                if (buShoppingInfo == null) {
                    return;
                }
                mContent = buShoppingInfo.getSign();
//                mContent = user.getCurrentCulturalQuantity() < 0 ? "" : user.getCurrentCulturalQuantity() + "";
//                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
        if (!TextUtils.isEmpty(mContent)) {
            mEtInput.setText(mContent);
            mEtInput.setSelection(mContent.length());    //将光标移至文字末尾
            int i = MAX_LENGTH - mContent.length();
            mTvNumberLimit.setText(String.valueOf(i));
        }
        KeyboardUtils.showSoftInput(mEtInput);
    }


    /**
     * 更新用户信息
     */
    public void updateUserInfo() {

        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        String content = mEtInput.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast("请输入设置内容");
            return;
        }

        showLoading();

        HashMap<String, Object> requestParam = new HashMap<>();
//        requestParam.put("nickname", mUserParamInfo.getNickname());
//        requestParam.put("gender", mUserParamInfo.getGender());
        switch (mModifyType) {
            case Constants.USER_MODIFY_TYPE_NICKNAME:
                requestParam.put("nickname", content);
                break;
            case Constants.USER_MODIFY_TYPE_SCALE:
                requestParam.put("culturalScale", content);
                break;
            case Constants.USER_MODIFY_TYPE_YEAR:
                requestParam.put("culturalAge", content);
                break;
            case Constants.USER_MODIFY_TYPE_QUANTITY:
                requestParam.put("currentCulturalQuantity", content);
                break;
        }

//        requestParam.put("culturalScale", mUserParamInfo.getCulturalScale());
//        requestParam.put("culturalAddress", mUserParamInfo.getCulturalAddress());
//        requestParam.put("culturalAge", mUserParamInfo.getCulturalAge());
//        requestParam.put("currentCulturalQuantity", mUserParamInfo.getCurrentCulturalQuantity());

        OkHttpManager.getInstance().postRequest(HttpUrl.URL_UPDATE_USER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                hideLoading();
                if (result != null && result.getCode() == 1) {
                    UserManager.getInstance(mContext).setUser(result.getData());
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO);
                    EventBus.getDefault().post(messageEvent);
                    if (mOnFragmentListener != null) {
                        KeyboardUtils.hideSoftInput(mEtInput);
                        mOnFragmentListener.hideFragment();
                    }
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
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


    /**
     * 更新店铺信息
     */
    private void updateShoppingFromServer() {
        String token = getToken();
        BUShoppingInfo buShoppingInfo = ShoppingInfoManager.getInstance().getBuShoppingInfo();
        if (buShoppingInfo == null) {
            return;
        }
        String content = mEtInput.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast("请输入设置内容");
            return;
        }
        showLoading();
        HashMap<String, Object> param = new HashMap<>();
        param.put("id", buShoppingInfo.getId());
        switch (mModifyType){
            case Constants.USER_MODIFY_TYPE_SHOPPING_NAME:
                param.put("name",content);
                break;
            case Constants.USER_MODIFY_TYPE_SHOPPING_MAIN:
                param.put("mainCategory", content);
                break;
            case Constants.USER_MODIFY_TYPE_SHOPPING_SIGN:
                param.put("sign", content);
                break;
        }

//        param.put("address", buShoppingInfo.getAddress());
        param.put("needAuth", false);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_APPLY_SHOPPING_SANE, new BaseCallBack<BUShoppingInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
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
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_UPDATE_SHOPPING_INFO);
                    EventBus.getDefault().post(messageEvent);
                    if (mOnFragmentListener != null) {
                        KeyboardUtils.hideSoftInput(mEtInput);
                        mOnFragmentListener.hideFragment();
                    }
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



    @Override
    public boolean onBackPressed() {
// 当确认没有子Fragmnt时可以直接return false
//        if (backHandled) {
//            Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
//            return true;
//        } else {
//            return BackHandlerHelper.handleBackPress(this);
//        }
        return BackHandlerHelper.handleBackPress(this);
    }


    public void setOnFragmentListener(OnFragmentListener onFragmentListener) {
        this.mOnFragmentListener = onFragmentListener;
    }

    public interface OnFragmentListener {

        void hideFragment();

    }

}
