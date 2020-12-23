package com.meishe.yangquan.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.BusinessOpportunityListResult;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.CommentListResult;
import com.meishe.yangquan.bean.CommentResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.input.InputDialog;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class CommentBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private Context mContext = null;
    private BottomSheetBehavior mBehavior;
    private RecyclerView mRecyclerView;
    private MaterialProgress mLoading;
    private MultiFunctionAdapter mAdapter;
    private View mNoDate;

    private TextView mEditText;


    private long mMessageId;
    private String mCurCommentContent;
    private User mUser;
    private View mBtnClose;

    private List<Comment> mList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(mContext, R.layout.dialog_bottom_sheet, null);
        dialog.setContentView(view);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setSkipCollapsed(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        RelativeLayout dialogContent = view.findViewById(R.id.dialog_content);
        ViewGroup.LayoutParams params = dialogContent.getLayoutParams();
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.8);
        params.height = height;
        dialogContent.setLayoutParams(params);

        initView(view);
        initData();
        initListener();
        return dialog;
    }



    public void setmMessageId(long mMessageId) {
        this.mMessageId = mMessageId;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.common_list);
        mLoading = view.findViewById(R.id.loading);
        mNoDate=view.findViewById(R.id.view_no_data);
        mEditText=view.findViewById(R.id.edit_text);
        mBtnClose=view.findViewById(R.id.bottom_sheet_dialog_close_btn);
        mLoading.show();
    }

    private void initData() {
        if (mMessageId>0){
            LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
            getCommentListFromServer(mMessageId);
        }
    }


    private void initListener() {
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isFastDoubleClick()){
                    return;
                }

                if(UserManager.getInstance(mContext).isNeedLogin()){
                    //未登陆提示用户登陆
                    AppManager.getInstance().jumpActivity(getActivity(), LoginActivity.class);
                    return;
                }

                mUser=null;

                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager == null){
                    return;
                }

                if (mUser==null){
                    ToastUtil.showToast(mContext,"comment user is null");
                    return;
                }
                // 弹出评论输入框
                showCommentInputDialog();

            }
        });


        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void showCommentInputDialog() {
        // 弹出评论输入框
        InputDialog inputDialog = new InputDialog();
        Bundle bundle = new Bundle();
        inputDialog.setArguments(bundle);
        inputDialog.show(getFragmentManager(), "InputDialog");
        inputDialog.setCommentListener(new InputDialog.OnCommentListener() {
            @Override
            public void onComment(String commentContent) {
                if (TextUtils.isEmpty(commentContent.trim())) {
                    ToastUtil.showToast(mContext, "请输入评论内容");
                    return;
                }
                addComment(mMessageId,commentContent);
                mLoading.show();
            }

            @Override
            public void onRecordCommentContent(String commentContent) {
                mCurCommentContent = commentContent;
            }
        });
    }

    private void addComment(long messageId, String commentContent) {
        String str_count=  Base64.encodeToString(commentContent.getBytes(), Base64.DEFAULT);
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("messageId", messageId);
        requestParam.put("userId", mUser.getUserId());
        requestParam.put("photoUrl", mUser.getPhotoUrl());
        requestParam.put("nickName", mUser.getNickname());
        requestParam.put("content", str_count);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_ADD_COMMENT, new BaseCallBack<CommentResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.hide();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, CommentResult result) {
                mLoading.hide();
                if (response != null && response.code() == 200) {
                    if (result == null && result.getStatus() != 200) {
                        setNoDataVisible(View.VISIBLE);
                        return;
                    }
                   Comment comment = result.getData();
                    if (comment!=null){
                        mAdapter.addItem(0,comment);
                        setNoDataVisible(View.GONE);
                    }

                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.hide();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }

    private void getCommentListFromServer(long messageId) {
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("messageId", messageId);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_COMMENT_LIST, new BaseCallBack<CommentListResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setNoDataVisible(View.VISIBLE);
                        mLoading.hide();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, CommentListResult result) {
                mLoading.hide();
                if (response != null && response.code() == 200) {
                    if (result == null && result.getStatus() != 200) {
                        setNoDataVisible(View.VISIBLE);
                        return;
                    }
                    mList = result.getData();
                    if (mList != null && mList.size() > 0) {
                        Collections.reverse(mList);
                        mAdapter.addAll(mList);
                        setNoDataVisible(View.GONE);
                    } else {
                        setNoDataVisible(View.VISIBLE);
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.hide();
                        setNoDataVisible(View.VISIBLE);
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }

    public void setNoDataVisible(int visible){
        if (mNoDate!=null){
            mNoDate.setVisibility(visible);
        }
    }

}
