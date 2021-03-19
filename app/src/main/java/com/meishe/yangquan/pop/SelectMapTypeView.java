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
 * @Description : 底部选择地图View
 */
public class SelectMapTypeView extends BottomPopupView {

    private OnAttachListener mAttachListener;
    private Context mContext;

    public SelectMapTypeView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public static SelectMapTypeView create(Context context, OnAttachListener attachListener) {
        return (SelectMapTypeView) new XPopup.Builder(context)
               .asCustom(new SelectMapTypeView(context).
                        setAttachListener(attachListener));
    }

    @Override
    protected int getMaxWidth() {
        return ScreenUtils.getScreenWidth(mContext);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_select_map_type;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.btn_baidu).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttachListener!=null){
                    mAttachListener.onSelect(Constants.TYPE_MAP_BAIDU);
                }
                dismiss();
            }
        });

        findViewById(R.id.btn_gaode).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttachListener!=null){
                    mAttachListener.onSelect(Constants.TYPE_MAP_GAODE);
                }
                dismiss();
            }
        });

        findViewById(R.id.btn_tengxun).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttachListener!=null){
                    mAttachListener.onSelect(Constants.TYPE_MAP_TENGXUN);
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


    public SelectMapTypeView setAttachListener(SelectMapTypeView.OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }


}
