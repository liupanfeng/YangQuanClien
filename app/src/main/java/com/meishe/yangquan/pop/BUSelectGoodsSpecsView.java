package com.meishe.yangquan.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.BottomPopupView;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUGoodsSubTypeInfo;
import com.meishe.yangquan.bean.BUGoodsTypeInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/4 13:53
 * @Description : 商版-底部弹窗，选择商品规格
 */
public class BUSelectGoodsSpecsView extends BottomPopupView {

    private View iv_close;
    private View btn_confirm;
    private View ll_root_container;
    private RecyclerView recyclerView;

    private MultiFunctionAdapter mAdapter;

    private OnAttachListener mAttachListener;
    private Context mContext;

    private List<String> mData;

    public static BUSelectGoodsSpecsView create(Context context, List<String> list,OnAttachListener attachListener) {
        return (BUSelectGoodsSpecsView) new XPopup.Builder(context)
                .asCustom(new BUSelectGoodsSpecsView(context,list).
                        setAttachListener(attachListener));
    }

    public BUSelectGoodsSpecsView(@NonNull Context context,List<String> data) {
        super(context);
        this.mContext = context;
        this.mData=data;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        iv_close = findViewById(R.id.iv_close);
        btn_confirm = findViewById(R.id.btn_confirm);
        ll_root_container = findViewById(R.id.ll_root_container);
        recyclerView = findViewById(R.id.recyclerView);

        if (recyclerView != null) {
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            mAdapter = new MultiFunctionAdapter(mContext, recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);

            initGoodsTypeData();
        }

        if (ll_root_container != null) {
            ViewGroup.LayoutParams layoutParams = ll_root_container.getLayoutParams();
            layoutParams.height = (int) (ScreenUtils.getScreenHeight(mContext) * 0.5);
            ll_root_container.setLayoutParams(layoutParams);
        }

        if (iv_close != null) {
            iv_close.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        if (btn_confirm != null) {
            btn_confirm.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseInfo selectData = mAdapter.getSelectData();
                    if (selectData == null) {
                        ToastUtil.showToast("请选择商品规格");
                        return;
                    }
                    if (selectData instanceof BUGoodsSubTypeInfo ) {
                        if (mAttachListener != null) {
                            mAttachListener.onSelectGoodsType(((BUGoodsSubTypeInfo) selectData).getName());
                            dismiss();
                        }
                    }
                }
            });
        }

    }

    private void initGoodsTypeData() {
        final List<BUGoodsSubTypeInfo> datas = new ArrayList<>();
        if (CommonUtils.isEmpty(mData)){
            return;
        }
        for (int i=0;i<mData.size();i++){
            String s = mData.get(i);
            BUGoodsSubTypeInfo buGoodsTypeInfo = new BUGoodsSubTypeInfo();
            buGoodsTypeInfo.setName(s);
            datas.add(buGoodsTypeInfo);
        }

        mAdapter.addAll(datas);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof BUGoodsSubTypeInfo){
                    mAdapter.setSelectPosition(position);
                }
            }
        });
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_select_goods_specs;
    }


    public interface OnAttachListener {
        /**
         * 商品规格
         */
        void onSelectGoodsType(String goodsSpecs);

    }

    public BUSelectGoodsSpecsView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }

}
