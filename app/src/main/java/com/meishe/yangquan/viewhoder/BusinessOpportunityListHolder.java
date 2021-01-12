package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 我的商机
 */
public class BusinessOpportunityListHolder extends BaseViewHolder {

    private TextView tv_opportunity_time;
    private TextView tv_opportunity_title;
    private TextView tv_opportunity_content;

    public BusinessOpportunityListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        tv_opportunity_time=view.findViewById(R.id.tv_opportunity_time);
        tv_opportunity_title=view.findViewById(R.id.tv_opportunity_title);
        tv_opportunity_content=view.findViewById(R.id.tv_opportunity_content);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        if (info instanceof BusinessOpportunity){
            BusinessOpportunity businessOpportunity= (BusinessOpportunity) info;
            long createTime=businessOpportunity.getCreateTime();
            if (createTime>0){
                tv_opportunity_time.setText(FormatDateUtil.longToString(createTime, FormatDateUtil.FORMAT_TYPE));
            }
            User user=null;
            if (user!=null){
                String nickName=user.getNickname();
                tv_opportunity_title.setText("尊敬的"+nickName+":");
            }
            tv_opportunity_content.setText(businessOpportunity.getContent());
        }
    }


}
