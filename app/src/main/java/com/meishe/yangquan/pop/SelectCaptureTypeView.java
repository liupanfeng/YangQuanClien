package com.meishe.yangquan.pop;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.BottomPopupView;
import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.ScreenUtils;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 15:46
 * @Description : 底部选择拍摄View
 */
public class SelectCaptureTypeView extends BottomPopupView {

    private OnAttachListener mAttachListener;
    private Context mContext;

    public SelectCaptureTypeView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public static SelectCaptureTypeView create(Context context, OnAttachListener attachListener) {
        return (SelectCaptureTypeView) new XPopup.Builder(context)
               .asCustom(new SelectCaptureTypeView(context).
                        setAttachListener(attachListener));
    }

    @Override
    protected int getMaxWidth() {
        return ScreenUtils.getScreenWidth(mContext);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_select_capture_type;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.btn_capture).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttachListener!=null){
                    mAttachListener.onSelect(Constants.TYPE_CAPTURE);
                }
                dismiss();
            }
        });

        findViewById(R.id.btn_album).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttachListener!=null){
                    mAttachListener.onSelect(Constants.TYPE_ALBUM);
                }
                dismiss();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public interface OnAttachListener {

        void onSelect(int type);

    }


    public SelectCaptureTypeView setAttachListener(SelectCaptureTypeView.OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }


}
