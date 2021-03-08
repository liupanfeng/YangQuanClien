package com.meishe.yangquan.wiget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meishe.yangquan.R;

public class IosDialog extends Dialog {
    private Context context;

    private TextView titleTextView;
    private TextView contentTextView;
    private Button cancelButton;
    private Button asureButton;
    private View view;

    //标题
    private String title;

    //内容
    private String text;


    //内容
    private String inputContent;


    //确定按钮的文字
    private String asureText;

    //取消按钮是的文字
    private String cancelText;

    //对话框位于屏幕位置
    private int gravity;

    //标题大小
    private int titleTextSize;

    //标题颜色
    private int titleTextColor;

    //内容大小
    private int textSize;

    //内容颜色
    private int textColor;

    //确定和取消按钮文字大小
    private int buttonTextSize;

    //确定按钮文字颜色
    private int buttonAsureTextColor;

    //取消按钮文字颜色
    private int buttonCancelTextColor;

    //对话框宽度
    private int width;

    //对话框高度
    private int height;

    //确定和取消按钮点击回调接口
    private OnButtonClickListener listener;

    private EditText mEtInputContent;

    public IosDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
    }

    public IosDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public IosDialog createDialog() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_ios, null);

        titleTextView = (TextView) view.findViewById(R.id.title_textview);
        mEtInputContent = (EditText) view.findViewById(R.id.et_input_content);
        if (!TextUtils.isEmpty(inputContent)){
            mEtInputContent.setVisibility(View.VISIBLE);
            mEtInputContent.setHint(inputContent);
//            mEtInputContent.setSelection(inputContent.length());
        }
        contentTextView = (TextView) view.findViewById(R.id.content_textview);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);
        asureButton = (Button) view.findViewById(R.id.asure_button);
        titleTextView.setVisibility(title != null ? View.VISIBLE : View.GONE);
        titleTextView.setText(title);
        titleTextView.setTextColor(titleTextColor != 0 ? titleTextColor : Color.parseColor("#000000"));
        titleTextView.setTextSize(titleTextSize != 0 ? titleTextSize : 18);
        if (!TextUtils.isEmpty(text)){
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(text);
        }
        contentTextView.setTextColor(textColor != 0 ? textColor : Color.parseColor("#000000"));
        contentTextView.setTextSize(textSize != 0 ? textSize : 17);
        cancelButton.setText(cancelText != null ? cancelText : "否");
//        cancelButton.setTextColor(buttonCancelTextColor != 0 ? buttonCancelTextColor : Color.parseColor("#007AFF"));
        cancelButton.setTextSize(buttonTextSize != 0 ? buttonTextSize : 17);
        asureButton.setText(asureText != null ? asureText : "是");
//        asureButton.setTextColor(buttonAsureTextColor != 0 ? buttonAsureTextColor : Color.parseColor("#007AFF"));
        asureButton.setTextSize(buttonTextSize != 0 ? buttonTextSize : 17);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onCancelClick();
            }
        });
        asureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onAsureClick();
            }
        });

        show();
        setContentView(view);
        getWindow().getAttributes().gravity = gravity != 0 ? gravity : Gravity.CENTER;
        getWindow().setLayout(width != 0 ? width : getWindowWidth((Activity) context)
                - 2 * dp2px(45), height != 0 ? height : WindowManager.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        return this;
    }




    public void setContentViewLineSpace(float lineSpace){
        contentTextView.setLineSpacing(0,lineSpace);
    }

    public void setContentViewLayoutGravity(int gravity){
        contentTextView.setGravity(gravity);
    }


    public void setAsureText(String asureText) {
        this.asureText = asureText;
    }

    public void setButtonAsureTextColor(int buttonAsureTextColor) {
        this.buttonAsureTextColor = buttonAsureTextColor;
    }

    public void setButtonCancelTextColor(int buttonCancelTextColor) {
        this.buttonCancelTextColor = buttonCancelTextColor;
    }

    public void setButtonTextSize(int buttonTextSize) {
        this.buttonTextSize = buttonTextSize;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setView(View view) {
        this.view = view;
    }

    public EditText getmEtInputContent() {
        return mEtInputContent;
    }

    public void setInputContent(String content) {
        this.inputContent=content;
    }

    public void setInputContentVisible(int visible){
        this.mEtInputContent.setVisibility(visible);
    }

    public void setDialogSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public interface OnButtonClickListener {
        void onAsureClick();

        void onCancelClick();
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    private int getWindowWidth(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

    private int dp2px(float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static class DialogBuilder {
        IosDialog dialog;

        public DialogBuilder(Context context) {
            dialog = new IosDialog(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.create();
            }
        }

        /**
         * 设置对话框在屏幕的位置
         * @param gravity 类Gravity下的位置常数
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setGravity(int gravity) {
            dialog.setGravity(gravity);

            return this;
        }

        /**
         * 设置确定按钮文字
         * @param asureText 文字内容
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setAsureText(String asureText) {
            dialog.setAsureText(asureText);
            return this;
        }

        /**
         * 设置确定按钮文字颜色
         * @param buttonAsureTextColor 颜色值
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setButtonAsureTextColor(int buttonAsureTextColor) {
            dialog.setButtonAsureTextColor(buttonAsureTextColor);
            return this;
        }

        /**
         * 设置取消按钮文字颜色
         * @param buttonCancelTextColor 颜色值
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setButtonCancelTextColor(int buttonCancelTextColor) {
            dialog.setButtonCancelTextColor(buttonCancelTextColor);
            return this;
        }

        /**
         * 设置确定和取消按钮的文字大小
         * @param buttonTextSize 文字大小，单位sp
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setButtonTextSize(int buttonTextSize) {
            dialog.setButtonTextSize(buttonTextSize);
            return this;
        }

        /**
         * 设置取消按钮文字
         * @param cancelText 文字内容
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setCancelText(String cancelText) {
            dialog.setCancelText(cancelText);
            return this;
        }

        /**
         * 设置对话框大小
         * @param width   对话框宽度，单位px
         * @param height  对话框高度，单位px
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setDialogSize(int width, int height) {
            dialog.setDialogSize(width, height);
            return this;
        }

        /**
         * 设置标题大小
         * @param titleTextSize 文字大小，单位sp
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setTitleTextSize(int titleTextSize) {
            dialog.setTextSize(titleTextSize);
            return this;
        }

        /**
         * 设置标题颜色
         * @param titleTextColor 颜色值
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setTitleTextColor(int titleTextColor) {
            dialog.setTitleTextColor(titleTextColor);
            return this;
        }

        /**
         * 设置标题
         * @param title 文字内容
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setTitle(String title) {
            dialog.setTitle(title);
            return this;
        }

        /**
         * 设置内容
         * @param text 文字内容
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setText(String text) {
            dialog.setText(text);
            return this;
        }


        /**
         * 设置内容
         * @param text 文字内容
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setInputContent(String text) {
            dialog.setInputContent(text);
            return this;
        }

        /**
         * 设置内容颜色
         * @param textColor 颜色值
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setTextColor(int textColor) {
            dialog.setTextColor(textColor);
            return this;
        }

        /**
         * 设置内容大小
         * @param textSize 文字大小，单位sp
         * @return 当前的DialogBuilder
         */
        public DialogBuilder setTextSize(int textSize) {
            dialog.setTextSize(textSize);
            return this;
        }

        /**
         * 设置确定和取消按钮的点击回调
         * @param listener 回调接口
         * @return 当前的DialogBuilder
         */
        public DialogBuilder addListener(OnButtonClickListener listener) {
            dialog.setOnButtonClickListener(listener);
            return this;
        }

        /**
         * 创建对话框，放在最后执行
         * @return 创建的IosDialog实体
         */
        public IosDialog create() {
            return dialog.createDialog();
        }

    }
}

