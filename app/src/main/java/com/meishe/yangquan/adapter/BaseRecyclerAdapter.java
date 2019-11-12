package com.meishe.yangquan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.meishe.yangquan.activity.ServiceTypeListActivity;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.fragment.BaseRecyclerFragment;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.viewhoder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    protected BaseRecyclerFragment mFragment;

    private static final int VIEW_TYPE_BASE=100;
    protected static final int VIEW_SERVICE_NOTIFY=VIEW_TYPE_BASE+1;                                //顶部系统通知
    protected static final int VIEW_SERVICE_TYPE=VIEW_TYPE_BASE+2;                                  //服务类型
    protected static final int VIEW_SERVICE_TYPE_LIST=VIEW_TYPE_BASE+3;                                  //服务类型

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
    protected boolean isNeedAutoScroll=false;

    public BaseRecyclerAdapter(final Context context, RecyclerView recyclerView) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mRecyclerView = recyclerView;
        mList = new ArrayList<>();
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setIsRecyclable(true);
        BaseInfo info=getItem(position);
        if (isNeedAutoScroll){
            holder.bindViewHolder(mContext,mList.get(position%mList.size()),this);
        }else {
            holder.bindViewHolder(mContext,mList.get(position),this);
        }
    }

    @Override
    public int getItemCount() {
        if (isNeedAutoScroll){
            return Integer.MAX_VALUE;
        }else{
            return mList == null ? 0 : mList.size();
        }
    }

    @Override
    public void onClick(View v) {
        onItemClick(v);
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    protected void onItemClick(View v) {
        BaseInfo info= (BaseInfo) v.getTag();
        if (info instanceof ServiceTypeInfo){
            AppManager.getInstance().jumpActivity(getFragment().getActivity(), ServiceTypeListActivity.class);
        }
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
            notifyItemRangeChanged(position-1, position + infos.size() - 1);
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
