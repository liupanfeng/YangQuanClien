package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected BaseRecyclerAdapter mAdapter;
    public BaseViewHolder(@NonNull View itemView,Object ...obj) {
        super(itemView);
        initViewHolder(itemView,obj);
    }

    protected abstract void initViewHolder(View view, Object... obj);

    public abstract void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener);


    public void onHolderDestroy() {
        mAdapter = null;
    }


}
