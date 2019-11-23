package com.meishe.yangquan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServiceNotifyInfo;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.viewhoder.BaseViewHolder;
import com.meishe.yangquan.viewhoder.MessageListHolder;
import com.meishe.yangquan.viewhoder.MineTypeHolder;
import com.meishe.yangquan.viewhoder.ServiceNotifyHolder;
import com.meishe.yangquan.viewhoder.ServiceTypeHolder;
import com.meishe.yangquan.viewhoder.ServiceTypeListHolder;

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
            case VIEW_SERVICE_TYPE:
                view=mLayoutInflater.inflate(R.layout.item_service_type,parent,false);
                viewHolder=new ServiceTypeHolder(view,this);
                break;
            case VIEW_SERVICE_TYPE_LIST:
                view=mLayoutInflater.inflate(R.layout.item_service_type_list,parent,false);
                viewHolder=new ServiceTypeListHolder(view,this);
                break;
            case VIEW_MINE_TYPE_LIST:
                view=mLayoutInflater.inflate(R.layout.item_mine_type,parent,false);
                viewHolder=new MineTypeHolder(view,this);
                break;
            case VIEW_MESSAGE_TYPE_LIST:
                view=mLayoutInflater.inflate(R.layout.item_message_type_list,parent,false);
                viewHolder=new MessageListHolder(view,this);
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
        }else if (baseInfo instanceof ServiceTypeInfo){
            return VIEW_SERVICE_TYPE;
        }else if (baseInfo instanceof ServerCustomer){
            return VIEW_SERVICE_TYPE_LIST;
        }else if (baseInfo instanceof MineTypeInfo){
            return VIEW_MINE_TYPE_LIST;
        }else if (baseInfo instanceof Message){
            return VIEW_MESSAGE_TYPE_LIST;
        }
        return super.getItemViewType(position);
    }
}
