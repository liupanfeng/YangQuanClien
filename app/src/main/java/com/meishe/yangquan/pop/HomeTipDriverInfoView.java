package com.meishe.yangquan.pop;

import android.content.Context;
import android.text.TextUtils;
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
 * @CreateDate : 2021/3/21
 * @Description : 提示是否去完善车辆信息
 */
public class HomeTipDriverInfoView extends CenterPopupView {

    private TextView titleTextView;
    private TextView contentTextView;
    private Button cancelButton;
    private Button asureButton;
    private EditText mEtInputContent;
    private View view;

    private OnAttachListener mAttachListener;

    private String mTitle;

    private String mContent;


    public static HomeTipDriverInfoView create(Context context, String title,String content,OnAttachListener attachListener) {
        return (HomeTipDriverInfoView) new XPopup.Builder(context)
                .asCustom(new HomeTipDriverInfoView(context,title,content).
                        setAttachListener(attachListener));
    }

    public HomeTipDriverInfoView(@NonNull Context context,String title,String content) {
        super(context);
        this.mTitle=title;
        this.mContent=content;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_driver_info_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        titleTextView = (TextView)  findViewById(R.id.title_textview);
        mEtInputContent = (EditText)  findViewById(R.id.et_input_content);
        contentTextView = (TextView)  findViewById(R.id.content_textview);
        cancelButton = (Button)  findViewById(R.id.cancel_button);
        asureButton = (Button)  findViewById(R.id.asure_button);

        titleTextView.setText(mTitle);
        contentTextView.setText(mContent);


        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mAttachListener!=null){
                    mAttachListener.onClickCancel();
                }
            }
        });

        asureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mAttachListener!=null){
                    mAttachListener.onClickConfirm();
                }
            }
        });

    }

    public interface OnAttachListener {

        void onClickConfirm();

        void onClickCancel();

    }


    public HomeTipDriverInfoView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }

}
