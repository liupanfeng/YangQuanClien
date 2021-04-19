package com.meishe.yangquan.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.MineBreedingArchivesDetailActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.pop.ConfirmOrderView;
import com.meishe.yangquan.pop.SelectCancelOrderTypeView;
import com.meishe.yangquan.pop.TipsCenterView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.ToastUtil;

import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/4/17
 * @Description :
 */
public class ButtonClickHelper implements DataHelper.OnCallBackListener {

    private Context mContext;

    private static ButtonClickHelper instance;

    private BaseRecyclerAdapter mAdapter;

    private BaseInfo mBaseInfo;


    public static ButtonClickHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ButtonClickHelper(context);
        }
        return instance;
    }


    private ButtonClickHelper(Context context) {
        this.mContext = context;
        DataHelper.getInstance().setOnCallBackListener(this);
    }

    /**
     * 统一处理点击事件
     *
     * @param v
     * @param info
     */
    public void doButtonClick(View v, final BaseInfo info, BaseRecyclerAdapter adapter){
        mBaseInfo=info;
        mAdapter=adapter;
        if (info instanceof MineOrderInfo) {
            //订单状态
            int orderState = ((MineOrderInfo) info).getOrderState();

            //我的订单的点击响应
            if (v.getId() == R.id.btn_left_function) {
                if (orderState == Constants.TYPE_LIST_TYPE_ORDER_WAIT_PAY_TYPE) {
                    //待付--取消订单
                    SelectCancelOrderTypeView selectCancelOrderTypeView = SelectCancelOrderTypeView.
                            create(mContext, "取消订单",new SelectCancelOrderTypeView.OnAttachListener() {
                                @Override
                                public void onSelect(String option) {
                                    DataHelper.getInstance().doCancelOrder(((MineOrderInfo) info).getOrderId(), option);
                                }
                            });
                    if (!selectCancelOrderTypeView.isShow()) {
                        selectCancelOrderTypeView.show();
                    }
                } else if (orderState == Constants.TYPE_LIST_TYPE_ORDER_WAIT_RECEIVE_TYPE) {
                    //待收货 - 申请退款

                    SelectCancelOrderTypeView selectCancelOrderTypeView = SelectCancelOrderTypeView.
                            create(mContext, "申请退款",new SelectCancelOrderTypeView.OnAttachListener() {
                                @Override
                                public void onSelect(String option) {
                                    DataHelper.getInstance().doApplyRefund(((MineOrderInfo) info).getOrderId(), option);
                                }
                            });
                    if (!selectCancelOrderTypeView.isShow()) {
                        selectCancelOrderTypeView.show();
                    }


                } else if (orderState == Constants.TYPE_LIST_TYPE_ORDER_WAIT_COMMENT_TYPE) {
                    //评价

                } else if (orderState == Constants.TYPE_LIST_TYPE_ORDER_REFUND_TYPE) {
                    //退款
                }
            } else if (v.getId() == R.id.btn_right_function) {
                if (orderState == Constants.TYPE_LIST_TYPE_ORDER_WAIT_PAY_TYPE) {
                    //待付--去支付
                    ConfirmOrderView confirmOrderView = ConfirmOrderView.create(mContext, ((MineOrderInfo) info).getPrice(), new ConfirmOrderView.OnAttachListener() {
                        @Override
                        public void onSelect(String password) {
                            DataHelper.getInstance().doPayOrder(((MineOrderInfo) info).getOrderId(), password);
                        }
                    });

                    if (!confirmOrderView.isShow()) {
                        confirmOrderView.show();
                    }

                } else if (orderState == Constants.TYPE_LIST_TYPE_ORDER_WAIT_RECEIVE_TYPE) {
                    //待收货 -确认收货

                    TipsCenterView tipsCenterView = TipsCenterView.create(mContext, "确认收货", "商品没有问题，确认收货？", new TipsCenterView.OnAttachListener() {
                        @Override
                        public void cancelClick() {

                        }

                        @Override
                        public void confirmClick(String content) {
                            DataHelper.getInstance().confirmReceiveGoods(((MineOrderInfo) info).getOrderId());
                        }
                    });

                    if (!tipsCenterView.isShow()) {
                        tipsCenterView.show();
                    }

                } else if (orderState == Constants.TYPE_LIST_TYPE_ORDER_WAIT_COMMENT_TYPE) {
                    //评价

                } else if (orderState == Constants.TYPE_LIST_TYPE_ORDER_REFUND_TYPE) {
                    //退款
                }
            }
        }else if (info instanceof MineBreedingArchivesInfo){
            if (v.getId()==R.id.btn_delete){
                DataHelper.getInstance().deleteBreedingArchive(((MineBreedingArchivesInfo) info).getId());
            }else{
                int id = ((MineBreedingArchivesInfo) info).getId();
                int currentCulturalQuantity = ((MineBreedingArchivesInfo) info).getCurrentCulturalQuantity();
                long initDate = ((MineBreedingArchivesInfo) info).getInitDate();

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.TYPE_KEY_BATCH_ID, id);
                bundle.putInt(Constants.TYPE_KEY_SHEEP_SURPLUS, currentCulturalQuantity);
                bundle.putLong(Constants.TYPE_KEY_SHEEP_INIT_TIME, initDate);
                AppManager.getInstance().jumpActivity(mContext, MineBreedingArchivesDetailActivity.class, bundle);
            }
        }
    }


    /**
     * 统一处理点击事件
     *
     * @param v
     * @param info
     */
    public void doButtonClick(View v, final BaseInfo info) {
        doButtonClick(v,info,null);
    }


    @Override
    public void onShowNoData() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(String content) {
        ToastUtil.showToast(content);
    }

    @Override
    public void onSuccess(List<? extends BaseInfo> baseInfos, int pageSize, int pageNum) {

    }

    @Override
    public void onFailure(Exception e) {
        ToastUtil.showToast("网络异常");
    }

    @Override
    public void onError(Exception e) {
        ToastUtil.showToast("网络异常");
    }

    @Override
    public void onSuccessNeedDeleteItem() {
        if (mAdapter!=null){
            List<BaseInfo> data = mAdapter.getData();
            if (!CommonUtils.isEmpty(data)){
                int itemPosition = mAdapter.getItemPosition(mBaseInfo);
                data.remove(mBaseInfo);
                mAdapter.notifyItemRemoved(itemPosition);
            }
        }
    }
}
