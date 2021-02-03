package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BreedingArchivesInfo;
import com.meishe.yangquan.bean.HouseKeeperSourceAnalysisInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 养殖助手-饲料分析-饲料列表
 *
 * @author 86188
 */
public class BreedingArchivesFoodAnalysisHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*饲料名称*/
    private TextView tv_name;
    /*饲料数量*/
    private EditText et_input_amount;
    /*输入单价*/
    private EditText et_input_single_price;
    /*日配比*/
    private TextView tv_percent;
    /*日配比*/
    private TextView tv_total_price;


    public BreedingArchivesFoodAnalysisHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_name = view.findViewById(R.id.tv_name);
        et_input_amount = view.findViewById(R.id.et_input_amount);
        tv_percent = view.findViewById(R.id.tv_percent);
        et_input_single_price = view.findViewById(R.id.et_input_single_price);
        tv_total_price = view.findViewById(R.id.tv_total_price);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof HouseKeeperSourceAnalysisInfo) {
            tv_name.setText(((HouseKeeperSourceAnalysisInfo) info).getName());
        }

    }


}
