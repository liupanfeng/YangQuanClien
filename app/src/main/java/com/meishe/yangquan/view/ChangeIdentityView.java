package com.meishe.yangquan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 改变登录身份
 * @date 2020/11/18 13:49
 */
public class ChangeIdentityView extends BaseCustomLinearLayout {

    /*普通用户*/
    public static final int TYPE_IDENTITY_PERSONAL = 1;
    /*商户*/
    public static final int TYPE_IDENTITY_BUSINESS = 2;

    private Button mBtnPersonal;
    private Button mBtnBusiness;
    private OnChangeIdentityListener mOnChangeIdentityListener;
    private View mViewRight;
    private View mViewLeft;

    public ChangeIdentityView(Context context) {
        super(context);
    }

    public ChangeIdentityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangeIdentityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        View view = mLayoutInflater.inflate(R.layout.layout_change_identity_view, this, true);
        mBtnPersonal = view.findViewById(R.id.btn_personal);
        mBtnBusiness = view.findViewById(R.id.btn_business);
        mViewLeft = view.findViewById(R.id.view_left);
        mViewRight = view.findViewById(R.id.view_right);
    }

    @Override
    protected void initListener() {
        mViewLeft.setOnClickListener(this);
        mViewRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_left:
                clickPersonalView(TYPE_IDENTITY_PERSONAL);
                break;
            case R.id.view_right:
                clickBusinessView(TYPE_IDENTITY_BUSINESS);
                break;
            default:
                break;
        }
    }

    private void clickPersonalView(int type) {
        mBtnPersonal.setVisibility(VISIBLE);
        mBtnBusiness.setVisibility(GONE);
        if (mOnChangeIdentityListener != null) {
            mOnChangeIdentityListener.onChangeIdentity(type);
        }
    }

    private void clickBusinessView(int type) {
        mBtnPersonal.setVisibility(GONE);
        mBtnBusiness.setVisibility(VISIBLE);
        if (mOnChangeIdentityListener != null) {
            mOnChangeIdentityListener.onChangeIdentity(type);
        }
    }

    public void setOnChangeIdentityListener(OnChangeIdentityListener onChangeIdentityListener) {
        this.mOnChangeIdentityListener = onChangeIdentityListener;
    }

    public interface OnChangeIdentityListener {

        /**
         * 身份切换回调
         *
         * @param type
         */
        void onChangeIdentity(int type);

    }

}
