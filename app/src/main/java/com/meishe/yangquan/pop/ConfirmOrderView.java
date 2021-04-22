package com.meishe.yangquan.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.BottomPopupView;
import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.wiget.pay.PasswordEditText;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 15:46
 * @Description : 底部确认订单View
 */
public class ConfirmOrderView extends BottomPopupView implements PasswordEditText.PasswordFullListener, View.OnClickListener {

    private OnAttachListener mAttachListener;
    private Context mContext;
    private float mFeedGold;

    private LinearLayout mKeyBoardView;
    private PasswordEditText mPasswordEditText;
    private TextView mTvPrice;
    private View confirmOrderPayView;
    private View orderPayView;


    public ConfirmOrderView(@NonNull Context context,float feedGold) {
        super(context);
        this.mFeedGold=feedGold;
        this.mContext = context;
    }

    public static ConfirmOrderView create(Context context, float feedGold,OnAttachListener attachListener) {
        return (ConfirmOrderView) new XPopup.Builder(context)
               .asCustom(new ConfirmOrderView(context,feedGold).
                        setAttachListener(attachListener));
    }

    @Override
    protected int getMaxWidth() {
        return ScreenUtils.getScreenWidth(mContext);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_confirm_order_view;
    }


    @Override
    protected void onCreate() {
        super.onCreate();

        confirmOrderPayView = findViewById(R.id.rl_confirm_order_pay);
        orderPayView = findViewById(R.id.rl_order_pay_view);
        confirmOrderPayView.setVisibility(VISIBLE);
        orderPayView.setVisibility(GONE);

        mTvPrice= findViewById(R.id.tv_content);
        mTvPrice.setText("饲料金："+mFeedGold);


        mKeyBoardView = findViewById(R.id.keyboard);
        mPasswordEditText = findViewById(R.id.passwordEdt);
        mPasswordEditText.setPasswordFullListener(this);
        setItemClickListener(mKeyBoardView);



        findViewById(R.id.btn_commit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrderPayView.setVisibility(GONE);
                orderPayView.setVisibility(VISIBLE);
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOrderPayView.setVisibility(VISIBLE);
                orderPayView.setVisibility(GONE);
            }
        });

    }


    /**
     * 给每一个自定义数字键盘上的View 设置点击事件
     *
     * @param view
     */
    private void setItemClickListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                //不断的给里面所有的View设置setOnClickListener
                View childView = ((ViewGroup) view).getChildAt(i);
                setItemClickListener(childView);
            }
        } else {
            view.setOnClickListener(this);
        }
    }


    @Override
    public void passwordFull(String password) {
        if (mAttachListener!=null){
            mAttachListener.onSelect(password);
            dismiss();
        }
    }

    public void show(final float price){
        super.show();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTvPrice!=null){
                    confirmOrderPayView.setVisibility(VISIBLE);
                    orderPayView.setVisibility(GONE);
                    mTvPrice.setText("饲料金："+price);
                }
            }
        },100);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            String number = ((TextView) v).getText().toString().trim();
            mPasswordEditText.addPassword(number);
        }
        if (v instanceof ImageView) {
            mPasswordEditText.deletePassword();
        }
    }

    public interface OnAttachListener {

        void onSelect(String password);

    }

    public ConfirmOrderView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }


}
