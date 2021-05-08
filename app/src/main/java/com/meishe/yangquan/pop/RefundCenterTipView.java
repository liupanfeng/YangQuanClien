package com.meishe.yangquan.pop;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.yangquan.R;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description : 退货管理 中间的提示View
 */
public class RefundCenterTipView extends CenterPopupView {

    private String mTitle;
    private Context mContext;
    private OnAttachListener mAttachListener;

    public static RefundCenterTipView create(Context context, String title,  OnAttachListener listener) {
        return (RefundCenterTipView) new XPopup.Builder(context)
                .asCustom(new RefundCenterTipView(context,title).setAttachListener(listener));
    }

    private RefundCenterTipView(@NonNull Context context, String title) {
        super(context);
        this.mTitle=title;
        this.mContext = context;
    }



    @Override
    protected int getImplLayoutId() {
        return R.layout.refund_tips_center_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        /*标题*/
        TextView title = findViewById(R.id.title_textview);
        /*输入的审核内容*/
        final EditText editText = findViewById(R.id.et_input_content);
        /*取消按钮*/
        Button cancel = findViewById(R.id.cancel_button);
        /*确定*/
        Button confirm = findViewById(R.id.asure_button);

        title.setText(mTitle);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAttachListener !=null ){
                    mAttachListener.cancelClick(editText.getText().toString());
                }
                dismiss();
            }
        });


        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAttachListener != null){
                    mAttachListener.confirmClick(editText.getText().toString());
                }
                dismiss();
            }
        });

    }


    public interface OnAttachListener {

        void cancelClick(String content);

        void confirmClick(String content);

    }


    public RefundCenterTipView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }


}
