package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.PointRecordInfo;
import com.meishe.yangquan.bean.SheepHairInfo;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的-我的积分
 *
 * @author 86188
 */
public class MineMyPointsHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*积分类型*/
    private TextView tv_point_type;
    /*积分时间*/
    private TextView tv_point_time;
    /*积分值*/
    private TextView tv_point_value;

    private Map<String, String> dataMaps = new HashMap<>();


    public MineMyPointsHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
        dataMaps.put("SUGGEST_ACCEPT", "建议被采纳");
        dataMaps.put("ADD_COMMENT", "羊吧评论");
        dataMaps.put("ADD_POST", "羊吧发帖子");
        dataMaps.put("SIGN_IN", "羊吧签到");
        dataMaps.put("LOGIN", "每日登录");
        dataMaps.put("ON_LINE", "在线超过30分钟");
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_point_type = view.findViewById(R.id.tv_point_type);
        tv_point_time = view.findViewById(R.id.tv_point_time);
        tv_point_value = view.findViewById(R.id.tv_point_value);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof PointRecordInfo) {
            tv_point_type.setText(dataMaps.get(((PointRecordInfo) info).getFromType()));
            tv_point_value.setText(((PointRecordInfo) info).getWealth() + "");
            tv_point_time.setText(FormatDateUtil.longToString(((PointRecordInfo) info).getInitDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
        }

    }


}
