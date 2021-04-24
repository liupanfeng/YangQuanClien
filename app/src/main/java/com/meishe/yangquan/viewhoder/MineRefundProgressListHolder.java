package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineRefundProgressInfo;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 我的-退货进度
 *
 * @author 86188
 */
public class MineRefundProgressListHolder extends BaseViewHolder {


    private TextView tv_refund_desc;
    private TextView tv_refund_time;

    public MineRefundProgressListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        tv_refund_desc = view.findViewById(R.id.tv_refund_desc);
        tv_refund_time = view.findViewById(R.id.tv_refund_time);

    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineRefundProgressInfo) {
            tv_refund_desc.setText(((MineRefundProgressInfo) info).getDescription());
            long initDate = ((MineRefundProgressInfo) info).getInitDate();
            tv_refund_time.setText(FormatDateUtil.longToString(initDate,FormatDateUtil.FORMAT_TYPE));
        }

    }


}
