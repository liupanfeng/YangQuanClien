package com.meishe.yangquan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceNotifyInfo;
import com.meishe.yangquan.viewhoder.BaseViewHolder;
import com.meishe.yangquan.viewhoder.ServiceNotifyHolder;

public class MultiFunctionAdapter extends BaseRecyclerAdapter {

    public MultiFunctionAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        BaseViewHolder viewHolder =super.onCreateViewHolder(parent,viewType);
        switch (viewType){
            case VIEW_SERVICE_NOTIFY:
                view=mLayoutInflater.inflate(R.layout.item_service_notify,parent,false);
                viewHolder=new ServiceNotifyHolder(view,this);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        BaseInfo baseInfo;
        if (isNeedAutoScroll()){
            baseInfo=getItem(0);
        }else{
            baseInfo=getItem(position);
        }
        if (baseInfo instanceof ServiceNotifyInfo){
            return VIEW_SERVICE_NOTIFY;
        }
        return super.getItemViewType(position);
    }
}
