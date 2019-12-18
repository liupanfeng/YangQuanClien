package com.meishe.yangquan.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ScreenUtils;



public class ShareBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    private static final String TAG = "ShareBottomSheetDialogF";

    public final static int SHARE_TO_FRIENDS = 1;
    public final static int SHARE_TO_SINA = 2;
    public final static int SHARE_TO_WECHAT = 3;

    private BottomSheetBehavior mBehavior;
    private LinearLayout mWeChat;
    private LinearLayout mFriends;
    private LinearLayout mSina;


    private TextView mCancelBtn;
    private Context mContext;
    private DownloadAndJumpListeners mListener;

    private int mShareType = 0;//默认微信分享

    //以下两个字段仅供复制链接使用
    private String mShareId = "";
    private String mShareName = "";

    public ShareBottomSheetDialogFragment() {
        super();
    }


    public void setShareType(int shareType) {
        mShareType = shareType;
    }


    public void setShareId(String shareId) {
        mShareId = shareId;
    }

    public void setShareName(String shareName) {
        mShareName = shareName;
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
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View rootView = View.inflate(getActivity(), R.layout.share_bottom_dialog_layout, null);
        dialog.setContentView(rootView);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBehavior = BottomSheetBehavior.from((View) rootView.getParent());
        initViews(rootView);
        initData(rootView);
        initViewsListener();
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wechat:
                if(mListener != null){
                    mListener.onJumpTo(SHARE_TO_WECHAT);
                }
                break;

            case R.id.friends:
                if(mListener != null){
                    mListener.onJumpTo(SHARE_TO_FRIENDS);
                }
                break;
            case R.id.sina:
                if(mListener != null){
                    mListener.onJumpTo(SHARE_TO_SINA);
                }
                break;
            default:
                break;
        }
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void setDownloadAndJumpListener(DownloadAndJumpListeners listener){
        mListener = listener;
    }

    public interface DownloadAndJumpListeners{
        void onJumpTo(int type);
        void onDeleteVideo();
    }

    private void initViews(View rootView){
        mWeChat = rootView.findViewById(R.id.wechat);
        mFriends = rootView.findViewById(R.id.friends);
        mSina = rootView.findViewById(R.id.sina);

        mCancelBtn = rootView.findViewById(R.id.tv_cancel);
    }

    private void initData(View rootView) {
//        CommonData commonData = CommonData.getInstance();
//        UserInfo userInfo = commonData.getUserInfo();
//        boolean isSameUser = false;
//        if(userInfo != null ){
//            String userId = userInfo.getUserId();
//            if (!TextUtils.isEmpty(mUserId)
//                    && !TextUtils.isEmpty(userId)
//                    && mUserId.equals(userId)){
//                isSameUser = true;
//            }
//        }

//        if (mShareType == SHARE_VIDEO_TYPR){
//            //TODO
//            mDeleteVideo.setVisibility(isSameUser ? View.VISIBLE : View.GONE);
//            if (isSameUser){
//                mReportBtn.setVisibility(View.GONE);
//            }
//        }else {
//            mLLPrivateChat.setVisibility(View.GONE);
//            mDualVideoBtn.setVisibility(View.GONE);
//            if (mShareType == SHARE_TOPIC_TYPR
//                    || mShareType == SHARE_TOPIC_RECOMMEND_TYPR
//                    || mShareType == SHARE_HIT_LIST_TYPR
//                    || mShareType == SHARE_Welfare_Officer_TYPR){
//                mReportBtn.setVisibility(View.GONE);
//                mBlacklistBtn.setVisibility(View.GONE);
//                mDeleteVideo.setVisibility(View.INVISIBLE);
//            }else if (mShareType == SHARE_USER_TYPR){
//                mReportBtn.setVisibility(isSameUser ? View.GONE : View.VISIBLE);
//                mBlacklistBtn.setVisibility(isSameUser ? View.GONE : View.VISIBLE);
//                mDeleteVideo.setVisibility(isSameUser ? View.INVISIBLE : View.GONE);
//                if (!isSameUser){
//                    mFillLayout.setVisibility(View.GONE);
//                }
//            }
//        }
        int dpHeight = 300;
        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
        int height = ScreenUtils.dip2px(getActivity(),dpHeight);//mBehavior的行为高度与对话框的高度基本一致
        layoutParams.height = height;
        rootView.setLayoutParams(layoutParams);
        mBehavior.setPeekHeight(height);
    }
    private void initViewsListener() {
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        mWeChat.setOnClickListener(this);
        mFriends.setOnClickListener(this);
        mSina.setOnClickListener(this);
        //

    }



}
