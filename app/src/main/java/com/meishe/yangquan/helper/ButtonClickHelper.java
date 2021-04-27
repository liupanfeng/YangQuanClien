package com.meishe.yangquan.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.MineBreedingArchivesDetailActivity;
import com.meishe.yangquan.activity.MineOrderCommentActivity;
import com.meishe.yangquan.activity.MineRefundProgressActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUOrderInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.pop.BUChangePriceCenterView;
import com.meishe.yangquan.pop.ConfirmOrderView;
import com.meishe.yangquan.pop.SelectCancelOrderTypeView;
import com.meishe.yangquan.pop.TipsCenterView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

/**
 * @Author : lpf
 * @CreateDate : 2021/4/17
 * @Description :
 */
public class ButtonClickHelper implements DataHelper.OnClickItemCallBackListener {


    private static ButtonClickHelper instance;

    private BaseRecyclerAdapter mAdapter;

    private BaseInfo mBaseInfo;

    public static ButtonClickHelper getInstance() {
        if (instance == null) {
            instance = new ButtonClickHelper();
        }
        return instance;
    }


    private ButtonClickHelper() {
        DataHelper.getInstance().setOnClickItemCallBackListener(this);
    }

    /**
     * 统一处理点击事件
     *
     * @param v
     * @param info
     */
    public void doButtonClick(View v, final BaseInfo info, BaseRecyclerAdapter adapter) {
        mBaseInfo = info;
        mAdapter = adapter;
        Context context = mAdapter.getContext();
        if (info instanceof MineOrderInfo) {
            //订单状态
            int pageType = ((MineOrderInfo) info).getType();//页面类型
            int orderState = ((MineOrderInfo) info).getOrderState();
            //我的订单的点击响应
            if (v.getId() == R.id.btn_left_function) {
                if (pageType == Constants.TYPE_LIST_TYPE_ORDER_WAIT_PAY_TYPE) {
                    //待付--取消订单
                    SelectCancelOrderTypeView selectCancelOrderTypeView = SelectCancelOrderTypeView.
                            create(context, "取消订单", new SelectCancelOrderTypeView.OnAttachListener() {
                                @Override
                                public void onSelect(String option) {
                                    DataHelper.getInstance().doCancelOrder(((MineOrderInfo) info).getOrderId(), option);
                                }
                            });
                    if (!selectCancelOrderTypeView.isShow()) {
                        selectCancelOrderTypeView.show();
                    }
                } else if (pageType == Constants.TYPE_LIST_TYPE_ORDER_WAIT_RECEIVE_TYPE) {
                    //待收货 - 申请退款

                    SelectCancelOrderTypeView selectCancelOrderTypeView = SelectCancelOrderTypeView.
                            create(context, "申请退款", new SelectCancelOrderTypeView.OnAttachListener() {
                                @Override
                                public void onSelect(String option) {
                                    DataHelper.getInstance().doApplyRefund(((MineOrderInfo) info).getOrderId(), option);
                                }
                            });
                    if (!selectCancelOrderTypeView.isShow()) {
                        selectCancelOrderTypeView.show();
                    }


                } else if (pageType == Constants.TYPE_LIST_TYPE_ORDER_WAIT_COMMENT_TYPE) {
                    //评价

                } else if (pageType == Constants.TYPE_LIST_TYPE_ORDER_REFUND_TYPE) {
                    //退款
                }
            } else if (v.getId() == R.id.btn_right_function) {
                if (pageType == Constants.TYPE_LIST_TYPE_ORDER_WAIT_PAY_TYPE) {
                    //待付--去支付
                    final ConfirmOrderView confirmOrderView = ConfirmOrderView.create(context, ((MineOrderInfo) info).getPrice(), new ConfirmOrderView.OnAttachListener() {
                        @Override
                        public void onSelect(String password) {
                            DataHelper.getInstance().doPayOrder(((MineOrderInfo) info), password);
                        }
                    });
                    confirmOrderView.show(((MineOrderInfo) info).getPrice());

                } else if (pageType == Constants.TYPE_LIST_TYPE_ORDER_WAIT_RECEIVE_TYPE) {
                    //待收货 -确认收货
                    if (orderState==2){
                        //未发货
                        TipsCenterView tipsCenterView = TipsCenterView.create(context, "提示", "商品还没发货，请等待", new TipsCenterView.OnAttachListener() {
                            @Override
                            public void cancelClick() {

                            }

                            @Override
                            public void confirmClick(String content) {

                            }
                        });

                        if (!tipsCenterView.isShow()) {
                            tipsCenterView.show();
                        }
                    }else if (orderState==3){
                        //已发货
                        TipsCenterView tipsCenterView = TipsCenterView.create(context, "确认收货", "商品没有问题，确认收货？", new TipsCenterView.OnAttachListener() {
                            @Override
                            public void cancelClick() {

                            }

                            @Override
                            public void confirmClick(String content) {
                                DataHelper.getInstance().confirmReceiveGoods(((MineOrderInfo) info));
                            }
                        });

                        if (!tipsCenterView.isShow()) {
                            tipsCenterView.show();
                        }
                    }


                } else if (pageType == Constants.TYPE_LIST_TYPE_ORDER_WAIT_COMMENT_TYPE) {
                    if (orderState==5){
                        //已经评价过了，不能再次评价
                        return;
                    }
                    //评价--评价
                    UserManager.getInstance(mAdapter.getContext()).setMineOrderInfo((MineOrderInfo) info);
                    AppManager.getInstance().jumpActivity(mAdapter.getContext(), MineOrderCommentActivity.class);

                } else if (pageType == Constants.TYPE_LIST_TYPE_ORDER_REFUND_TYPE) {
                    //退款--退货进度
                    Bundle bundle=new Bundle();
                    bundle.putInt(Constants.TYPE_ORDER_ID,((MineOrderInfo) info).getOrderId());
                    AppManager.getInstance().jumpActivity(mAdapter.getContext(), MineRefundProgressActivity.class,bundle);

                }
            }
        } else if (info instanceof MineBreedingArchivesInfo) {
            if (v.getId() == R.id.btn_delete) {
                TipsCenterView tipsCenterView = TipsCenterView.create(context, "提示", "删除无法恢复，确定删除养殖档案？", new TipsCenterView.OnAttachListener() {
                    @Override
                    public void cancelClick() {

                    }

                    @Override
                    public void confirmClick(String content) {
                        DataHelper.getInstance().deleteBreedingArchive((MineBreedingArchivesInfo) info);
                    }
                });

                if (!tipsCenterView.isShow()) {
                    tipsCenterView.show();
                }


            } else {
                int id = ((MineBreedingArchivesInfo) info).getId();
                int currentCulturalQuantity = ((MineBreedingArchivesInfo) info).getCurrentCulturalQuantity();
                long initDate = ((MineBreedingArchivesInfo) info).getInitDate();

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.TYPE_KEY_BATCH_ID, id);
                bundle.putInt(Constants.TYPE_KEY_SHEEP_SURPLUS, currentCulturalQuantity);
                bundle.putLong(Constants.TYPE_KEY_SHEEP_INIT_TIME, initDate);
                AppManager.getInstance().jumpActivity(context, MineBreedingArchivesDetailActivity.class, bundle);
            }
        } else if (info instanceof BUOrderInfo) {
            int state = ((BUOrderInfo) info).getState();
            if (state == Constants.TYPE_COMMON_BU_ORDER_WAIT_PAY_TYPE) {
                //待付---改价
                BUChangePriceCenterView buChangePriceCenterView = BUChangePriceCenterView.create(mAdapter.getContext(), info, new BUChangePriceCenterView.OnChangePriceListener() {
                    @Override
                    public void onChangePrice(String oldPrice, String newPrice) {
                        ((BUOrderInfo) info).setPrice(Float.valueOf(newPrice));
                        DataHelper.getInstance().changeOrderPrice((BUOrderInfo) info);
                    }
                });

                if (buChangePriceCenterView != null && !buChangePriceCenterView.isShow()) {
                    buChangePriceCenterView.show();
                }

            } else if (state == Constants.TYPE_COMMON_BU_ORDER_WAIT_SEND_TYPE) {
                //待发货 ---发货
                TipsCenterView tipsCenterView = TipsCenterView.create(context, "提示", "已核对买家信息，确认发货", new TipsCenterView.OnAttachListener() {
                    @Override
                    public void cancelClick() {

                    }

                    @Override
                    public void confirmClick(String content) {
                        DataHelper.getInstance().sendGoods(((BUOrderInfo) info));
                    }
                });

                if (!tipsCenterView.isShow()) {
                    tipsCenterView.show();
                }

            } else if (state == Constants.TYPE_COMMON_BU_ORDER_ALREADY_SEND_TYPE) {
                //已发货


            } else if (state == Constants.TYPE_COMMON_BU_ORDER_FINISH_TYPE) {
                //完成

            }

        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(String content) {
        ToastUtil.showToast(content);
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
    public void onSuccessNeedUpdateItem(BaseInfo baseInfo) {
        if (mAdapter != null) {
            int itemPosition = mAdapter.getItemPosition(baseInfo);
//            List<BaseInfo> data = mAdapter.getData();
//            data.remove(itemPosition);
//            data.add(itemPosition,baseInfo);
            mAdapter.notifyItemChanged(itemPosition);
        }
    }
}
