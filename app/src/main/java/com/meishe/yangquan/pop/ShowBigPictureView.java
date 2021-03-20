package com.meishe.yangquan.pop;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description :
 */
public class ShowBigPictureView extends CenterPopupView {


    private RoundAngleImageView mIvShowPicture;
    private String mImagePath;
    private Context mContext;

    public static ShowBigPictureView create(Context context, String imagePath) {
        return (ShowBigPictureView) new XPopup.Builder(context)
                .asCustom(new ShowBigPictureView(context, imagePath));
    }

    public ShowBigPictureView(@NonNull Context context, String imagePath) {
        super(context);
        this.mImagePath = imagePath;
        this.mContext = context;
    }


    @Override
    protected int getMaxWidth() {
        return ScreenUtils.getScreenWidth(mContext);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.show_pic_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mIvShowPicture = findViewById(R.id.iv_show_pic);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvShowPicture.getLayoutParams();
        int screenWidth = ScreenUtils.getScreenWidth(mContext);
        int width = screenWidth;
        params.width = width;
        params.height = width;

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(this)
                .asBitmap()
                .load(mImagePath)
                .apply(options)
                .into(mIvShowPicture);


        mIvShowPicture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
