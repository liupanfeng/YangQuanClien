package com.meishe.yangquan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BusinessOpportunityActivity;
import com.meishe.yangquan.activity.ContactUsActivity;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.activity.MessageCenterActivity;
import com.meishe.yangquan.activity.PerfectInformationActivity;
import com.meishe.yangquan.activity.ServiceTypeListActivity;
import com.meishe.yangquan.activity.VersionUpdateActivity;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.BusinessOpportunityResult;
import com.meishe.yangquan.bean.EndInfo;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.fragment.BaseRecyclerFragment;
import com.meishe.yangquan.fragment.CommentBottomSheetDialogFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.PerformClickAction;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.view.ListLoadingView;
import com.meishe.yangquan.viewhoder.BaseViewHolder;
import com.meishe.yangquan.viewhoder.EmptyHolder;
import com.meishe.yangquan.viewhoder.FooterHolder;
import com.meishe.yangquan.wiget.IosDialog;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    protected BaseRecyclerFragment mFragment;

    protected MaterialProgress materialProgress;

    private static final int VIEW_TYPE_BASE = 100;
    protected final int VIEW_TYPE_EMPTY = VIEW_TYPE_BASE;                                               //上滑正在加载
    protected final int VIEW_TYPE_LOADING = VIEW_TYPE_BASE + 1;                                         //上滑正在加载
    protected final int VIEW_TYPE_END = VIEW_TYPE_BASE + 2;                                             //分页结束
    protected static final int VIEW_SERVICE_MESSAGE = VIEW_TYPE_BASE + 3;                               //顶部无线循环消息
    protected static final int VIEW_SERVICE_TYPE = VIEW_TYPE_BASE + 4;                                  //服务类型
    protected static final int VIEW_SERVICE_TYPE_LIST = VIEW_TYPE_BASE + 5;                             //服务类型
    protected static final int VIEW_MINE_TYPE_LIST = VIEW_TYPE_BASE + 6;                                //我的中间菜单
    protected static final int VIEW_MESSAGE_TYPE_LIST = VIEW_TYPE_BASE + 7;                            //信息页面List列表
    protected static final int VIEW_SERVICE_NEWS_TYPE_LIST = VIEW_TYPE_BASE + 8;                        //服务咨询新闻列表
    protected static final int VIEW_SERVICE_LABEL = VIEW_TYPE_BASE + 9;                               //服务标签
    protected static final int VIEW_MESSAGE_CENTER_LIST = VIEW_TYPE_BASE + 10;                        //消息中心
    protected static final int VIEW_BUSINESS_CENTER_LIST = VIEW_TYPE_BASE + 11;                        //我的商机
    protected static final int VIEW_COMMENT_LIST = VIEW_TYPE_BASE + 13;                                //评论
    private IosDialog mDialog;


    public BaseRecyclerFragment getFragment() {
        return mFragment;
    }

    public void setFragment(BaseRecyclerFragment fragment) {
        this.mFragment = fragment;
    }

    public Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected List<BaseInfo> mList;
    protected RecyclerView mRecyclerView;
    protected boolean isNeedAutoScroll = false;

    public BaseRecyclerAdapter(final Context context, RecyclerView recyclerView) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mRecyclerView = recyclerView;
        mList = new ArrayList<>();
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        int height = 50;
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                holder = new FooterHolder(new ListLoadingView(mContext, height, R.string.list_loading_normal));
                break;
            case VIEW_TYPE_EMPTY:
                holder = new EmptyHolder(new View(mContext));
                break;
            case VIEW_TYPE_END:
                holder = new EmptyHolder(new ListLoadingView(mContext, height, R.string.list_end));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setIsRecyclable(true);
        if (isNeedAutoScroll) {
            holder.bindViewHolder(mContext, mList.get(position % mList.size()), this);
        } else {
            holder.bindViewHolder(mContext, mList.get(position), this);
        }
    }


    @Override
    public int getItemViewType(int position) {
        BaseInfo baseInfo = getItem(position);
        if (baseInfo instanceof EndInfo) {
            return VIEW_TYPE_END;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if (isNeedAutoScroll) {
            return Integer.MAX_VALUE;
        } else {
            return mList == null ? 0 : mList.size();
        }
    }

    @Override
    public void onClick(View v) {
        onItemClick(v);
    }

    public void setMaterialProgress(MaterialProgress materialProgress) {
        this.materialProgress = materialProgress;
    }

    public MaterialProgress getMaterialProgress() {
        return materialProgress;
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    protected void onItemClick(View v) {
        BaseInfo info = (BaseInfo) v.getTag();
        if (info instanceof ServiceTypeInfo) {
            Bundle bundle = new Bundle();
            bundle.putString("name", ((ServiceTypeInfo) info).getName());
            bundle.putInt("type", ((ServiceTypeInfo) info).getType());
            AppManager.getInstance().jumpActivity(getFragment().getActivity(), ServiceTypeListActivity.class, bundle);
        } else if (info instanceof MineTypeInfo) {
            boolean isNeedLogin = UserManager.getInstance(mContext).isNeedLogin();
            switch (((MineTypeInfo) info).getName()) {
                case "完善资料":
                    if (isNeedLogin) {
                        AppManager.getInstance().jumpActivity(getFragment().getActivity(), LoginActivity.class);
                        return;
                    }
//                    ToastUtil.showToast(mContext,"研发中敬请期待");
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), PerfectInformationActivity.class);
                    break;
                case "消息中心":
                    if (isNeedLogin) {
                        AppManager.getInstance().jumpActivity(getFragment().getActivity(), LoginActivity.class);
                        return;
                    }
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MessageCenterActivity.class);
                    break;
                case "我的商机":
                    if (isNeedLogin) {
                        AppManager.getInstance().jumpActivity(getFragment().getActivity(), LoginActivity.class);
                        return;
                    }
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), BusinessOpportunityActivity.class);
                    break;
                case "版本更新":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), VersionUpdateActivity.class);
                    break;
                case "联系我们":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), ContactUsActivity.class);
                    break;

            }
        } else if (info instanceof SheepNews) {
            ToastUtil.showToast(mContext, "暂无历史数据");
        } else if (info instanceof ServerCustomer) {
            switch (v.getId()) {
                case R.id.btn_order:
                    User user=UserManager.getInstance(mContext).getUser();
                    if (user!=null){
                        String phoneNum=user.getPhoneNumber();
                        long fromUserId=user.getUserId();
                        long toUserId=((ServerCustomer) info).getUserId();
                        if (fromUserId==toUserId){
                            ToastUtil.showToast(mContext,"不能预约自己");
                            return;
                        }
                        showDialog(user.getUserId(),user.getPhoneNumber(),((ServerCustomer) info).getUserId(),"商机信息","预约信息","将把您的联系方式"+phoneNum+"发送给对方","确定预约");
                    }
                    break;
                case R.id.iv_service_zan:
                    final MaterialProgress materialProgress = getMaterialProgress();
                    if (materialProgress != null) {
                        materialProgress.show();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialProgress.hide();
                            ToastUtil.showToast(mContext, "点赞成功");
                        }
                    }, 2000);

                    break;
            }

        } else if (info instanceof Message) {

            switch (v.getId()) {
                case R.id.iv_message_comment:
                case R.id.tv_message_comment:
                    CommentBottomSheetDialogFragment fragment = new CommentBottomSheetDialogFragment();
                    fragment.setmMessageId(((Message) info).getMessageId());
                    fragment.show(mFragment.getFragmentManager(), "dialog");
                    break;
                case R.id.btn_message_start_connect:    //我要联系
                    User user=UserManager.getInstance(mContext).getUser();
                    if (user!=null){
                        String phoneNum=user.getPhoneNumber();
                        long fromUserId=user.getUserId();
                        long toUserId=((Message) info).getUserId();
                        if (fromUserId==toUserId){
                            ToastUtil.showToast(mContext,"不能联系自己");
                            return;
                        }
                        showDialog(fromUserId,user.getPhoneNumber(),toUserId,"商机信息","联系信息","将把您的联系方式"+phoneNum+"发送给对方","确定联系");
                    }

                    break;
            }

        }

    }


    private void showDialog(final long fromUserId, final String fromPhoneNumber, final long userId, final String content, String title, String diaContent, String sureText) {
        mDialog = new IosDialog.DialogBuilder(mContext)
                .setInputContent(diaContent)
                .setTitle(title)
                .setAsureText(sureText)
                .setCancelText("取消")
                .addListener(new IosDialog.OnButtonClickListener() {
                    @Override
                    public void onAsureClick() {
                        getMaterialProgress().show();
                        addBusinessOpportunity(fromUserId,fromPhoneNumber,userId,content);
                        mDialog.dismiss();
                    }

                    @Override
                    public void onCancelClick() {
                        mDialog.dismiss();
                    }
                }).create();
    }


    private void addBusinessOpportunity(Long fromUserId,String fromPhoneNumber,Long toUserId,String content) {
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("fromUserId", fromUserId);
        requestParam.put("fromPhoneNumber", fromPhoneNumber);
        requestParam.put("toUserId", toUserId);
        requestParam.put("content", content);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_MINE_ADD_BUSIJNESS, new BaseCallBack<BusinessOpportunityResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext,"商机信息发送失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, BusinessOpportunityResult result) {
                getMaterialProgress().hide();
                if (response != null && response.code() == 200) {
                    if (result == null && result.getStatus() != 200) {
                        return;
                    }
                    BusinessOpportunity businessOpportunity = result.getData();
                    if (businessOpportunity != null ) {
                        ToastUtil.showToast(mContext,"商机信息已发送");
                    } else {
                        ToastUtil.showToast(mContext,"商机信息发送失败");
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
//                setNoDataVisible(View.VISIBLE);
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext,"商机信息发送失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }



    /**
     * 获得素材元素
     *
     * @param position
     * @return
     */
    public BaseInfo getItem(int position) {
        if (position < 0 || mList == null || position >= mList.size()) return new BaseInfo();
        return mList.get(position);
    }

    public int getItemPosition(BaseInfo info) {
        if (mList.contains(info)) {
            return mList.indexOf(info);
        }
        return -1;
    }

    public void updateListItem(int position, BaseInfo newInfo) {
        if (mList != null && position < mList.size()) {
            mList.set(position, newInfo);
        }
    }


    /**
     * 插入一个素材元素并只刷新插入项
     *
     * @param info
     */
    public void addItem(BaseInfo info) {
        if (info != null) {
            mList.add(info);
            int position = mList.indexOf(info);
            notifyItemRangeChanged(position - 1, 2);
//            notifyItemInserted(position);
        }
    }

    /**
     * 插入多个素材元素并只刷新插入项
     *
     * @param infos
     */
    public void addItems(int position, List<BaseInfo> infos) {
        if (infos != null && position < mList.size()) {
            mList.addAll(position, infos);
            notifyItemRangeChanged(position - 1, position + infos.size() - 1);
        }
    }

    /**
     * 指定位置插入一个素材元素并只刷新插入项
     *
     * @param info
     */
    public synchronized void addItem(int position, BaseInfo info) {
        if (info != null) {
            mList.add(position, info);
            notifyItemRangeChanged(position, 2);
        }
    }

    /**
     * 重新插入全部素材元素刷新整个列表
     *
     * @param list
     */
    public void addAll(List<? extends BaseInfo> list) {
        if (list != null && list.size() > 0) {
            mList.clear();
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public boolean isNeedAutoScroll() {
        return isNeedAutoScroll;
    }

    public void setNeedAutoScroll(boolean needAutoScroll) {
        isNeedAutoScroll = needAutoScroll;
    }
}
