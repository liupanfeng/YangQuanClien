package com.meishe.yangquan.pop;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.yangquan.R;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description : 中间的提示View
 */
public class TipsCenterView extends CenterPopupView {

    private String mTitle;
    private String mContent;
    private Context mContext;
    private OnAttachListener mAttachListener;

    public static TipsCenterView create(Context context, String title,String content,OnAttachListener listener) {
        return (TipsCenterView) new XPopup.Builder(context)
                .asCustom(new TipsCenterView(context,title, content).setAttachListener(listener));
    }

    private TipsCenterView(@NonNull Context context, String title,String content) {
        super(context);
        this.mTitle=title;
        this.mContent = content;
        this.mContext = context;
    }



    @Override
    protected int getImplLayoutId() {
        return R.layout.tips_center_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        /*标题*/
        TextView title = findViewById(R.id.title_textview);
        /*内容*/
        TextView content = findViewById(R.id.content_textview);
        /*取消按钮*/
        Button cancel = findViewById(R.id.cancel_button);
        /*确定*/
        Button confirm = findViewById(R.id.asure_button);

        title.setText(mTitle);
        content.setVisibility(VISIBLE);
        content.setText(mContent);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mAttachListener !=null ){
                    mAttachListener.cancelClick();
                }
                dismiss();
            }
        });


        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mAttachListener != null){
                    mAttachListener.confirmClick("");
                }

                dismiss();
            }
        });

    }


    public interface OnAttachListener {

        void cancelClick();

        void confirmClick(String content);

    }


    public TipsCenterView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }


}
