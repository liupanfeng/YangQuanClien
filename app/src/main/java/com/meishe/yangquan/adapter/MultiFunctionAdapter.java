package com.meishe.yangquan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.Label;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServiceMessage;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.bean.SystemNotification;
import com.meishe.yangquan.viewhoder.BaseViewHolder;
import com.meishe.yangquan.viewhoder.BusinessOpportunityListHolder;
import com.meishe.yangquan.viewhoder.CommentListHolder;
import com.meishe.yangquan.viewhoder.MessageCenterListHolder;
import com.meishe.yangquan.viewhoder.MessageListHolder;
import com.meishe.yangquan.viewhoder.MineTypeHolder;
import com.meishe.yangquan.viewhoder.ServiceLabelHolder;
import com.meishe.yangquan.viewhoder.ServiceMessageListHolder;
import com.meishe.yangquan.viewhoder.ServiceSheepNewsHolder;
import com.meishe.yangquan.viewhoder.ServiceMessageHolder;
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
            case VIEW_SERVICE_MESSAGE:
                if (pageType==1){
                    view=mLayoutInflater.inflate(R.layout.item_service_message,parent,false);
                    viewHolder=new ServiceMessageHolder(view,this);
                }else if (pageType==2){
                    view=mLayoutInflater.inflate(R.layout.item_service_message_lsit,parent,false);
                    viewHolder=new ServiceMessageListHolder(view,this);
                }

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
            case VIEW_SERVICE_NEWS_TYPE_LIST:
                view=mLayoutInflater.inflate(R.layout.item_service_news_type,parent,false);
                viewHolder=new ServiceSheepNewsHolder(view,this);
                break;
            case VIEW_SERVICE_LABEL:
                view=mLayoutInflater.inflate(R.layout.item_label_server,parent,false);
                viewHolder=new ServiceLabelHolder(view,this);
                break;
            case VIEW_MESSAGE_CENTER_LIST:
                view=mLayoutInflater.inflate(R.layout.item_message_center,parent,false);
                viewHolder=new MessageCenterListHolder(view,this);
                break;
            case VIEW_BUSINESS_CENTER_LIST:
                view=mLayoutInflater.inflate(R.layout.item_business_opportunity,parent,false);
                viewHolder=new BusinessOpportunityListHolder(view,this);
                break;
            case VIEW_COMMENT_LIST:
                view=mLayoutInflater.inflate(R.layout.item_comment,parent,false);
                viewHolder=new CommentListHolder(view,this);
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
        if (baseInfo instanceof ServiceMessage){
            return VIEW_SERVICE_MESSAGE;
        }else if (baseInfo instanceof ServiceTypeInfo){
            return VIEW_SERVICE_TYPE;
        }else if (baseInfo instanceof ServerCustomer){
            return VIEW_SERVICE_TYPE_LIST;
        }else if (baseInfo instanceof MineTypeInfo){
            return VIEW_MINE_TYPE_LIST;
        }else if (baseInfo instanceof Message){
            return VIEW_MESSAGE_TYPE_LIST;
        }else if (baseInfo instanceof SheepNews){
            return VIEW_SERVICE_NEWS_TYPE_LIST;
        }else if (baseInfo instanceof Label){
            return VIEW_SERVICE_LABEL;
        }else if (baseInfo instanceof SystemNotification){
            return VIEW_MESSAGE_CENTER_LIST;
        }else if (baseInfo instanceof BusinessOpportunity){
            return VIEW_BUSINESS_CENTER_LIST;
        }else if (baseInfo instanceof Comment){
            return VIEW_COMMENT_LIST;
        }
        return super.getItemViewType(position);
    }
}
