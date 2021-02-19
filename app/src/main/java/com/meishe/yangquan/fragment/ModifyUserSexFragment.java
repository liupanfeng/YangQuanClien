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
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.helper.BackHandlerHelper;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.KeyboardUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/25
 * @Description : 更改用户性别
 */
public class ModifyUserSexFragment extends BaseRecyclerFragment {


    private TextView mTvTitle;
    private ImageView mIvBack;
    private Button mBtnRight;


    private OnFragmentListener mOnFragmentListener;


    //0:女 1男
    private Integer mPersonSex ;

    private View rl_man;
    private View iv_man;
    private View rl_woman;
    private View iv_woman;

    public static ModifyUserSexFragment onCreate() {
        ModifyUserSexFragment modifyUserInfoFragment = new ModifyUserSexFragment();
        return modifyUserInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_modify_user_sex_inof, container, false);
        mTvTitle = view.findViewById(R.id.tv_title);
        mIvBack = view.findViewById(R.id.iv_back);
        mBtnRight = view.findViewById(R.id.btn_right);
        mLoading = view.findViewById(R.id.loading);
        rl_man = view.findViewById(R.id.rl_man);
        iv_man = view.findViewById(R.id.iv_man);
        rl_woman = view.findViewById(R.id.rl_woman);
        iv_woman = view.findViewById(R.id.iv_woman);

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

        rl_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersonSex = 1;
                showManView();
            }
        });

        rl_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersonSex = 0;
                showWomanView();
            }
        });


        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInfo();
            }
        });


    }

    @Override
    protected void initData() {
        UserInfo user = UserManager.getInstance(getContext()).getUser();
        if (user == null) {
            return;
        }
        mTvTitle.setText("设置性别");
        mPersonSex = user.getGender();
        if (mPersonSex==null){
            return;
        }
        switch (mPersonSex) {
            case 0:
                showWomanView();
                break;
            case 1:
                showManView();
                break;
            default:
                iv_man.setVisibility(GONE);
                iv_woman.setVisibility(GONE);
                break;
        }
    }

    /**
     * 展示男
     */
    private void showManView() {
        iv_man.setVisibility(VISIBLE);
        iv_woman.setVisibility(GONE);
    }

    /**
     * 展示女
     */
    private void showWomanView() {
        iv_man.setVisibility(GONE);
        iv_woman.setVisibility(VISIBLE);
    }


    /**
     * 更新用户信息
     */
    public void updateUserInfo() {

        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        if (mPersonSex==null){
            return;
        }

        showLoading();

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("gender", mPersonSex);

        OkHttpManager.getInstance().postRequest(HttpUrl.URL_UPDATE_USER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
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
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
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
