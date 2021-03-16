package com.meishe.yangquan.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedReceiverAddressInfo;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description : 编辑收货地址
 */
public class EditReceiveAddressView extends CenterPopupView {

    private RoundAngleImageView mIvShowPicture;
    private Context mContext;
    private FeedReceiverAddressInfo mBaseInfo;
    private TextView mTvTitle;
    private View mIvBack;

    private EditText et_feed_receive_name;
    /*收货人电话*/
    private EditText et_feed_input_receive_phone_number;
    /*收货人所在区域*/
    private EditText et_feed_input_receive_area;
    /*详细地址*/
    private EditText et_feed_input_detail_address;
    /*操作类型 1=添加  2修改*/
    private View btn_save_receive_address;

    private int mType;


    public static EditReceiveAddressView create(Context context, FeedReceiverAddressInfo baseInfo,OnReceiveAddressListener listener) {
        return (EditReceiveAddressView) new XPopup.Builder(context)
                .asCustom(new EditReceiveAddressView(context, baseInfo).setOnReceiveAddressListener(listener));
    }

    public EditReceiveAddressView(@NonNull Context context, FeedReceiverAddressInfo baseInfo) {
        super(context);
        this.mBaseInfo = baseInfo;
        this.mContext = context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_edit_recevie_address;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(GONE);

        et_feed_receive_name = findViewById(R.id.et_feed_receive_name);
        et_feed_input_receive_phone_number = findViewById(R.id.et_feed_input_receive_phone_number);
        et_feed_input_receive_area = findViewById(R.id.et_feed_input_receive_area);
        et_feed_input_detail_address = findViewById(R.id.et_feed_input_detail_address);
        btn_save_receive_address = findViewById(R.id.btn_save_receive_address);

        if (mBaseInfo == null) {
            mType=1;
            mTvTitle.setText("新建收货地址");
        }else{
            mTvTitle.setText("编辑收货地址");
            mType=2;
            if (mBaseInfo instanceof FeedReceiverAddressInfo ){
                String receiverName = ((FeedReceiverAddressInfo) mBaseInfo).getReceiverName();
                et_feed_receive_name.setText(receiverName);
                et_feed_input_receive_phone_number.setText(((FeedReceiverAddressInfo) mBaseInfo).getReceiverPhone());
                et_feed_input_receive_area.setText(((FeedReceiverAddressInfo) mBaseInfo).getArea());
                et_feed_input_detail_address.setText(((FeedReceiverAddressInfo) mBaseInfo).getDetailAddress());
            }
        }

        btn_save_receive_address.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiveName = et_feed_receive_name.getText().toString();
                if (TextUtils.isEmpty(receiveName)){
                    ToastUtil.showToast("收货人姓名还未填写");
                    return;
                }

                String phoneNumber = et_feed_input_receive_phone_number.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)){
                    ToastUtil.showToast("收货人电话还未填写");
                    return;
                }

                String area = et_feed_input_receive_area.getText().toString();
                if (TextUtils.isEmpty(area)){
                    ToastUtil.showToast("收货人所在区域还未填写");
                    return;
                }

                String detailAddress = et_feed_input_detail_address.getText().toString();
                if (TextUtils.isEmpty(detailAddress)){
                    ToastUtil.showToast("收货人详细地址还未填写");
                    return;
                }

                if (mBaseInfo==null){
                    mBaseInfo=new FeedReceiverAddressInfo();
                }
                mBaseInfo.setReceiverName(receiveName);
                mBaseInfo.setReceiverPhone(phoneNumber);
                mBaseInfo.setArea(area);
                mBaseInfo.setDetailAddress(detailAddress);

                if (mOnReceiveAddressListener!=null){
                    dismiss();
                    mOnReceiveAddressListener.onReceiveAddress(mType,mBaseInfo);
                }
            }
        });

    }

    private OnReceiveAddressListener mOnReceiveAddressListener;

    public EditReceiveAddressView setOnReceiveAddressListener(OnReceiveAddressListener onReceiveAddressListener){
        mOnReceiveAddressListener=onReceiveAddressListener;
        return this;
    }

    public interface OnReceiveAddressListener{
        void onReceiveAddress(int type,BaseInfo baseInfo);
    }


}
