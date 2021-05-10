package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineFeedGoldInfo;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 我的-饲料金列表
 *
 * @author 86188
 */
public class MineFeedGoldHolder extends BaseViewHolder {


    /*饲料金操作时间*/
    private TextView tv_feed_gold_time;
    /*饲料金操作数量*/
    private TextView tv_feed_gold_amount;
    /*饲料金操作类型*/
    private TextView tv_feed_gold_type;

    public MineFeedGoldHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_feed_gold_time = view.findViewById(R.id.tv_feed_gold_time);
        tv_feed_gold_amount = view.findViewById(R.id.tv_feed_gold_amount);
        tv_feed_gold_type = view.findViewById(R.id.tv_feed_gold_type);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineFeedGoldInfo) {
            int type = ((MineFeedGoldInfo) info).getType();
            String sign="";
            if (type == Constants.TYPE_FEED_GOLD_IN_TYPE) {
//                param.put("type", "in");
                sign="+";
            } else if (type == Constants.TYPE_FEED_GOLD_OUT_TYPE) {
//                param.put("type", "out");
                sign="-";
            }

            tv_feed_gold_time.setText(FormatDateUtil.longToString(((MineFeedGoldInfo) info).getInitDate(), FormatDateUtil.FORMAT_TYPE));
            tv_feed_gold_amount.setText(sign+((MineFeedGoldInfo) info).getGold());
            tv_feed_gold_type.setText(((MineFeedGoldInfo) info).getTypeName());
        }

    }


}
