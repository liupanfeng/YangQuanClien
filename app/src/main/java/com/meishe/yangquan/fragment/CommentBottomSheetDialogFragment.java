package com.meishe.yangquan.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.CommentListResult;
import com.meishe.yangquan.bean.CommentResult;
import com.meishe.yangquan.bean.SheepBarCommentInfo;
import com.meishe.yangquan.bean.SheepBarCommentInfoResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.input.InputDialog;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
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

import static com.meishe.yangquan.activity.SheepBarDetailActivity.TYPE_SHEEP_BAR_COMMEND;
import static com.meishe.yangquan.activity.SheepBarDetailActivity.TYPE_SHEEP_BAR_NEWEST;

@Deprecated
public class CommentBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private Context mContext = null;
    private BottomSheetBehavior mBehavior;
    private RecyclerView mRecyclerView;
    private MaterialProgress mLoading;
    private MultiFunctionAdapter mAdapter;
    private View mNoDate;

    private TextView mEditText;


    private int mMessageId;
    private String mCurCommentContent;
    private User mUser;
    private View mBtnClose;

    private int mListType;

    private List<Comment> mList;

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


    public void insetMessageId(int mMessageId, int listType) {
        this.mMessageId = mMessageId;
        this.mListType = listType;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.common_list);
        mLoading = view.findViewById(R.id.loading);
        mNoDate = view.findViewById(R.id.view_no_data);
        mEditText = view.findViewById(R.id.edit_text);
        mBtnClose = view.findViewById(R.id.bottom_sheet_dialog_close_btn);
        mLoading.show();
    }

    private void initData() {
        if (mMessageId > 0) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
            getCommentDataFromServer(mMessageId, mListType);
        }
    }


    private void initListener() {
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isFastDoubleClick()) {
                    return;
                }

                if (UserManager.getInstance(mContext).isNeedLogin()) {
                    //未登陆提示用户登陆
                    AppManager.getInstance().jumpActivity(getActivity(), LoginActivity.class);
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
                addComment(mMessageId, commentContent);
                mLoading.show();
            }

            @Override
            public void onRecordCommentContent(String commentContent) {
                mCurCommentContent = commentContent;
            }
        });
    }

    private void addComment(long messageId, String commentContent) {
        String str_count = Base64.encodeToString(commentContent.getBytes(), Base64.DEFAULT);
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
                    if (comment != null) {
                        mAdapter.addItem(0, comment);
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


    public void setNoDataVisible(int visible) {
        if (mNoDate != null) {
            mNoDate.setVisibility(visible);
        }
    }


    /**
     * 获取一级评论信息
     *
     * @param id       羊吧id
     * @param listType 是否只看楼主
     */
    private void getCommentDataFromServer(int id, int listType) {
        String token = UserManager.getInstance(mContext).getToken();
        if (Util.checkNull(token)) {
            return;
        }
        boolean onlySeeOwner = false;
        if (listType == TYPE_SHEEP_BAR_COMMEND) {
            onlySeeOwner = true;
        } else if (listType == TYPE_SHEEP_BAR_NEWEST) {
            onlySeeOwner = false;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("postId", id);
        param.put("onlySeeOwner", onlySeeOwner);
        param.put("orderType", "desc");

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_COMMENT_LIST_LEVEL1, new BaseCallBack<SheepBarCommentInfoResult>() {
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
            protected void onSuccess(Call call, Response response, SheepBarCommentInfoResult sheepBarCommentInfoResult) {
                mLoading.hide();
                if (sheepBarCommentInfoResult == null) {
                    ToastUtil.showToast(response.message());
                    setNoDataVisible(View.VISIBLE);
                    return;
                }
                if (sheepBarCommentInfoResult.getCode() != 1) {
                    ToastUtil.showToast(sheepBarCommentInfoResult.getMsg());
                    setNoDataVisible(View.VISIBLE);
                    return;
                }
                List<SheepBarCommentInfo> datas = sheepBarCommentInfoResult.getData();
                if (CommonUtils.isEmpty(datas)) {
                    ToastUtil.showToast("没有获取到评论信息！");
                    setNoDataVisible(View.VISIBLE);
                    return;
                }

                setNoDataVisible(View.GONE);
                mAdapter.addAll(datas);

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
        }, param, token);

    }

    public void setMessageId(int id, int listType) {
        this.mMessageId = id;
        this.mListType = listType;
    }
}
