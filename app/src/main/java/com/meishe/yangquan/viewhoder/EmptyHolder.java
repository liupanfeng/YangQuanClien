package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.meishe.yangquan.bean.BaseInfo;

public class EmptyHolder extends BaseViewHolder {


    public EmptyHolder(@NonNull View itemView, Object... obj) {
        super(itemView, obj);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {

    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info,  int position,View.OnClickListener listener) {

    }
}
