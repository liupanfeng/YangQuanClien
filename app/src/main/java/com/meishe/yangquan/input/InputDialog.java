package com.meishe.yangquan.input;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.listener.NvSoftKeyBoardListener;
import com.meishe.yangquan.utils.Constants;

public class InputDialog extends DialogFragment {

    private EditText mEditText;
    private Context mContext;
    private OnCommentListener mListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CommentInputDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        Window win = getDialog().getWindow();

        win.setBackgroundDrawable(new ColorDrawable(0x00000000));
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);

        return inflater.inflate(R.layout.input_dialog, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mEditText = view.findViewById(R.id.edit_text);

        Bundle msgBundle = getArguments();
        if (msgBundle != null) {
            String commentMsg = msgBundle.getString("commentMsg","");
            String curCommtentContent = msgBundle.getString(Constants.COMMENT_CONTENT,"");
            if (!TextUtils.isEmpty(commentMsg)){
                mEditText.setHint(commentMsg);
            }
            setEditTextContent(curCommtentContent);
        }
        view.findViewById(R.id.dialog_root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(mContext == null){
                    return;
                }
                InputMethodManager inputMethodManager= (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    // requestFocus() 需要在inputMethodManager.showSoftInput 方法之前执行
                    mEditText.requestFocus();
                    if (inputMethodManager.showSoftInput(mEditText, 0)) {
                        mEditText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });

        NvSoftKeyBoardListener.setListener(this, new NvSoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                dismiss();
                if (mListener != null){
                    mListener.onRecordCommentContent(mEditText.getText().toString());
                }
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_SEND) {
                    hideSoftKeyboard();
                    if(mListener != null){
                        String commentContent = mEditText.getText().toString();
                        mListener.onComment(commentContent);
                        mEditText.setText("");
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    private void hideSoftKeyboard(){
        if(mContext == null){
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) mEditText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setEditTextContent(String commentContent){
        mEditText.setText(commentContent);
        int start = commentContent.length();
        mEditText.setSelection(start,start);
    }
    public void setCommentListener(OnCommentListener listener) {
        mListener = listener;
    }

    public interface OnCommentListener{
        void onComment(String commentContent);
        void onRecordCommentContent(String commentContent);
    }
}
