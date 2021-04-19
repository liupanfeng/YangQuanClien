package com.meishe.yangquan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.AboutActivity;
import com.meishe.yangquan.activity.BusinessOpportunityActivity;
import com.meishe.yangquan.activity.CommonRecyclerActivity;
import com.meishe.yangquan.activity.ContactUsActivity;
import com.meishe.yangquan.activity.FeedGoodsDetailActivity;
import com.meishe.yangquan.activity.FeedShoppingDetailActivity;
import com.meishe.yangquan.activity.HomeQuotationHistoryActivity;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.activity.MessageCenterActivity;
import com.meishe.yangquan.activity.MineBreedingArchivesActivity;
import com.meishe.yangquan.activity.MineBreedingArchivesDetailActivity;
import com.meishe.yangquan.activity.MineCallBackActivity;
import com.meishe.yangquan.activity.MineFeedGoldActivity;
import com.meishe.yangquan.activity.MineMyCollectionActivity;
import com.meishe.yangquan.activity.MineMyFocusActivity;
import com.meishe.yangquan.activity.MineMyPointsActivity;
import com.meishe.yangquan.activity.MinePayPasswordActivity;
import com.meishe.yangquan.activity.MinePersonalInfoActivity;
import com.meishe.yangquan.activity.MineSystemMessageActivity;
import com.meishe.yangquan.activity.PerfectInformationActivity;
import com.meishe.yangquan.activity.ServiceMessageListActivity;
import com.meishe.yangquan.activity.ServiceTypeListActivity;
import com.meishe.yangquan.activity.VersionUpdateActivity;
import com.meishe.yangquan.bean.BUPictureInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.BusinessOpportunityResult;
import com.meishe.yangquan.bean.EmptyInfo;
import com.meishe.yangquan.bean.EndInfo;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.HomeMarketPictureInfo;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.QuotationInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServerZan;
import com.meishe.yangquan.bean.ServerZanResult;
import com.meishe.yangquan.bean.ServiceInfo;
import com.meishe.yangquan.bean.ServiceMessage;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.fragment.BaseRecyclerFragment;
import com.meishe.yangquan.helper.ButtonClickHelper;
import com.meishe.yangquan.helper.DataHelper;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.SelectMapTypeView;
import com.meishe.yangquan.pop.ShowBigPictureView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.LocationUtil;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.ListLoadingView;
import com.meishe.yangquan.viewhoder.BaseViewHolder;
import com.meishe.yangquan.viewhoder.EmptyHolder;
import com.meishe.yangquan.viewhoder.FooterHolder;
import com.meishe.yangquan.wiget.IosDialog;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    protected BaseRecyclerFragment mFragment;

    protected MaterialProgress materialProgress;

    private static final int VIEW_TYPE_BASE = 100;
    protected final int VIEW_TYPE_EMPTY = VIEW_TYPE_BASE;                                               //上滑正在加载
    protected final int VIEW_TYPE_LOADING = VIEW_TYPE_BASE + 1;                                         //上滑正在加载
    protected final int VIEW_TYPE_END = VIEW_TYPE_BASE + 2;                                             //分页结束
    protected static final int VIEW_SERVICE_MESSAGE = VIEW_TYPE_BASE + 3;                               //顶部无线循环消息
    protected static final int VIEW_SERVICE_TYPE = VIEW_TYPE_BASE + 4;                                  //服务类型
    protected static final int VIEW_SERVICE_TYPE_LIST = VIEW_TYPE_BASE + 5;                             //服务类型
    protected static final int VIEW_MINE_TYPE_LIST = VIEW_TYPE_BASE + 6;                                //我的中间菜单
    protected static final int VIEW_MESSAGE_TYPE_LIST = VIEW_TYPE_BASE + 7;                            //信息页面List列表
    protected static final int VIEW_SERVICE_NEWS_TYPE_LIST = VIEW_TYPE_BASE + 8;                        //服务咨询新闻列表
    protected static final int VIEW_SERVICE_LABEL = VIEW_TYPE_BASE + 9;                               //服务标签
    protected static final int VIEW_MESSAGE_CENTER_LIST = VIEW_TYPE_BASE + 10;                        //消息中心
    protected static final int VIEW_BUSINESS_CENTER_LIST = VIEW_TYPE_BASE + 11;                        //我的商机
    protected static final int VIEW_COMMENT_LIST = VIEW_TYPE_BASE + 13;                                //评论
    protected static final int VIEW_QUOTATION_LIST = VIEW_TYPE_BASE + 14;                                //行情列表
    protected static final int VIEW_MARKET_LIST = VIEW_TYPE_BASE + 15;                                //市场列表
    protected static final int VIEW_INDUSTRY_LIST = VIEW_TYPE_BASE + 16;                                //行业资讯
    protected static final int VIEW_SHEEP_BAR_ADD_PIC = VIEW_TYPE_BASE + 17;                                //羊吧信息发布 图片列表
    protected static final int VIEW_SHEEP_BAR_MESSAGE = VIEW_TYPE_BASE + 18;                                //羊吧信息列表
    protected static final int VIEW_SERVICE_LIST = VIEW_TYPE_BASE + 19;                                   //首页-市场列表
    protected static final int VIEW_COMMENT_LEVEL1_LIST = VIEW_TYPE_BASE + 20;                                   //羊吧一级评论列表
    protected static final int VIEW_COMMENT_LEVEL1_CHILD_LIST = VIEW_TYPE_BASE + 21;                                   //羊吧二级评论列表
    protected static final int VIEW_INDUSTRY_CONTENT_LIST = VIEW_TYPE_BASE + 22;                      //资讯详情页内容
    protected static final int VIEW_CUT_SHEEP_HAIR_LIST = VIEW_TYPE_BASE + 23;                        //剪羊毛列表
    protected static final int VIEW_MINE_MY_POINTS_LIST = VIEW_TYPE_BASE + 24;                        //我的积分
    protected static final int VIEW_MINE_SUYSTEM_MESSAGE_LIST = VIEW_TYPE_BASE + 25;                  //系统消息
    protected static final int VIEW_MINE_MY_MESSAGE_LIST = VIEW_TYPE_BASE + 26;                     //我的消息
    protected static final int VIEW_HOME_MARKET_PICTURE_LIST = VIEW_TYPE_BASE + 27;                     //市场-小图列表
    protected static final int VIEW_MINE_BREEDING_ARCHIVE_LIST = VIEW_TYPE_BASE + 28;                     //我的养殖档案
    protected static final int VIEW_SHEEP_BREEDING_FOOD_ANALYSIS_LIST = VIEW_TYPE_BASE + 29;              //饲料列表
    protected static final int VIEW_MINE_MY_FANS_LIST = VIEW_TYPE_BASE + 30;                             //粉丝列表
    protected static final int VIEW_MINE_MY_FOCUS_LIST = VIEW_TYPE_BASE + 31;                             //关注列表
    protected static final int VIEW_CUT_SHEEP_VACCINE_LIST = VIEW_TYPE_BASE + 32;                        //疫苗列表
    protected static final int VIEW_CUT_SHEEP_LOSS_LIST = VIEW_TYPE_BASE + 33;                        //损耗列表
    protected static final int VIEW_MINE_CALLBACK_LIST = VIEW_TYPE_BASE + 34;                        //我的反馈
    protected static final int VIEW_MINE_USER_MESSAGE_LIST = VIEW_TYPE_BASE + 35;                        //我的消息
    protected static final int VIEW_FEED_SHOPPING_LIST = VIEW_TYPE_BASE + 36;                        //饲料商店列表
    protected static final int VIEW_FEED_FOODS_LIST = VIEW_TYPE_BASE + 37;                        //饲料商品列表

    protected static final int VIEW_MARKET_LIST_SELL_LITTLE_SHEEP = VIEW_TYPE_BASE + 38;                                //市场列表
    protected static final int VIEW_MARKET_LIST_SELL_BIG_SHEEP = VIEW_TYPE_BASE + 39;                                //市场列表
    protected static final int VIEW_MARKET_LIST_BUY_BIG_SHEEP = VIEW_TYPE_BASE + 40;                                //市场列表
    protected static final int VIEW_MARKET_LIST_BUY_LITTLE_SHEEP = VIEW_TYPE_BASE + 41;                                //市场列表
    protected static final int VIEW_MINE_FEED_GOLD_LIST = VIEW_TYPE_BASE + 42;                                //饲料金列表
    protected static final int VIEW_MINE_ORDER_LIST = VIEW_TYPE_BASE + 43;                                //订单列表
    protected static final int VIEW_MINE_COLLECTION_LIST = VIEW_TYPE_BASE + 44;                                //我的-收藏
    protected static final int VIEW_FEED_SHOPPING_CAR_LIST = VIEW_TYPE_BASE + 45;                                //饲料-购物车
    protected static final int VIEW_FEED_RECEIVE_ADDRESS = VIEW_TYPE_BASE + 46;                                //饲料-收货地址


    protected static final int VIEW_BU_HOME_SHOPDATA_LIST = VIEW_TYPE_BASE + 50;                     //商版店铺数据
    protected static final int VIEW_BU_MESSAGE_LIST = VIEW_TYPE_BASE + 51;                     //商版店铺数据
    protected static final int VIEW_BU_PICTURE_LIST = VIEW_TYPE_BASE + 52;                     //图片列表
    protected static final int VIEW_BU_ALREADY_APPLY_SHOPPING_LIST = VIEW_TYPE_BASE + 53;                     //图片列表
    protected static final int VIEW_BU_GOODS_LIST = VIEW_TYPE_BASE + 54;                     //商品列表
    protected static final int VIEW_BU_GOODS_TYPE_LIST = VIEW_TYPE_BASE + 55;                     //商品类型  用于添加商品的类型选择
    protected static final int VIEW_BU_GOODS_SUB_TYPE_LIST = VIEW_TYPE_BASE + 56;                     //商品副类型  用于添加商品的类型选择
    protected static final int VIEW_BU_ORDER_LIST = VIEW_TYPE_BASE + 57;                     //订单列表
    protected static final int VIEW_BU_COMMENT_LIST = VIEW_TYPE_BASE + 58;                     //评论列表
    protected static final int VIEW_BU_REFUND_LIST = VIEW_TYPE_BASE + 59;                     //退货列表


    private IosDialog mDialog;

    protected int pageType;

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    public int getPageType() {
        return pageType;
    }

    public BaseRecyclerFragment getFragment() {
        return mFragment;
    }

    public void setFragment(BaseRecyclerFragment fragment) {
        this.mFragment = fragment;
    }

    public Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected RecyclerView mRecyclerView;
    protected boolean isNeedAutoScroll = false;

    private OnItemClickListener onItemClickListener;
    private List<BaseInfo> mList;

    private int mSelectPosition = -1;
    /*嵌套的recyclerview 选中的数据结构*/
    private BaseInfo mChildRecyclerViewSelect;

    public BaseRecyclerAdapter(final Context context, RecyclerView recyclerView) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mRecyclerView = recyclerView;
        mList = new ArrayList<>();
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        int height = 50;
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                holder = new FooterHolder(new ListLoadingView(mContext, height, R.string.list_loading_normal));
                break;
            case VIEW_TYPE_EMPTY:
                View view = new View(mContext);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 150);
                view.setLayoutParams(layoutParams);
                holder = new EmptyHolder(view);
                break;
            case VIEW_TYPE_END:
                holder = new EmptyHolder(new ListLoadingView(mContext, height, R.string.list_end));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setIsRecyclable(true);
        if (isNeedAutoScroll) {
            holder.bindViewHolder(mContext, mList.get(position % mList.size()), position, this);
        } else {
            holder.bindViewHolder(mContext, mList.get(position), position, this);
        }
    }


    public BaseInfo getChildRecyclerViewSelect() {
        return mChildRecyclerViewSelect;
    }

    public void setChildRecyclerViewSelect(BaseInfo childRecyclerViewSelect) {
        this.mChildRecyclerViewSelect = childRecyclerViewSelect;
    }

    @Override
    public int getItemViewType(int position) {
        BaseInfo baseInfo = getItem(position);
        if (baseInfo instanceof EndInfo) {
            return VIEW_TYPE_END;
        } else if (baseInfo instanceof EmptyInfo) {
            return VIEW_TYPE_EMPTY;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if (isNeedAutoScroll) {
            return Integer.MAX_VALUE;
        } else {
            return mList == null ? 0 : mList.size();
        }
    }

    @Override
    public void onClick(View v) {
        onItemClick(v);
    }

    public int getSelectPosition() {
        return mSelectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.mSelectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public void setMaterialProgress(MaterialProgress materialProgress) {
        this.materialProgress = materialProgress;
    }

    public MaterialProgress getMaterialProgress() {
        return materialProgress;
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    protected void onItemClick(View v) {
        BaseInfo info = (BaseInfo) v.getTag();
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, getItemPosition(info), info);
        }
        if (info instanceof ServiceTypeInfo) {
            Bundle bundle = new Bundle();
            bundle.putString("name", ((ServiceTypeInfo) info).getName());
            bundle.putInt("type", ((ServiceTypeInfo) info).getType());
            AppManager.getInstance().jumpActivity(getFragment().getActivity(), ServiceTypeListActivity.class, bundle);
        } else if (info instanceof FeedShoppingInfo) {
            UserManager.getInstance(mContext).setFeedShoppingInfo((FeedShoppingInfo) info);
            Bundle bundle = new Bundle();
//            bundle.putSerializable(Constants.FEED_SHOPPING_INFO, info);
            AppManager.getInstance().jumpActivity(mContext, FeedShoppingDetailActivity.class, bundle);
        } else if (info instanceof FeedGoodsInfo) {
            UserManager.getInstance(mContext).setFeedGoodsInfo((FeedGoodsInfo) info);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.FEED_GOODS_INFO, info);
            AppManager.getInstance().jumpActivity(mContext, FeedGoodsDetailActivity.class, bundle);
        } else if (info instanceof CommonPictureInfo) {
            if (((CommonPictureInfo) info).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                return;
            }
            ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, ((CommonPictureInfo) info).getFilePath());
            if (showBigPictureView != null) {
                showBigPictureView.show();
            }
        } else if (info instanceof HomeMarketPictureInfo) {
            if (((HomeMarketPictureInfo) info).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                return;
            }
            ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, ((HomeMarketPictureInfo) info).getFilePath());
            if (showBigPictureView != null) {
                showBigPictureView.show();
            }
        } else if (info instanceof BUPictureInfo) {
            if (((BUPictureInfo) info).getType() == CommonPictureInfo.TYPE_ADD_PIC) {
                return;
            }
            ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, ((BUPictureInfo) info).getFilePath());
            if (showBigPictureView != null) {
                showBigPictureView.show();
            }
        } else if (info instanceof MineTypeInfo) {
//            boolean isNeedLogin = UserManager.getInstance(mContext).isNeedLogin();
            boolean isNeedLogin = true;
            switch (((MineTypeInfo) info).getName()) {
                case "完善资料":
                    if (isNeedLogin) {
                        AppManager.getInstance().jumpActivity(getFragment().getActivity(), LoginActivity.class);
                        return;
                    }
//                    ToastUtil.showToast(mContext,"研发中敬请期待");
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), PerfectInformationActivity.class);
                    break;
                case "消息中心":
                    if (isNeedLogin) {
                        AppManager.getInstance().jumpActivity(getFragment().getActivity(), LoginActivity.class);
                        return;
                    }
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MessageCenterActivity.class);
                    break;
                case "我的商机":
                    if (isNeedLogin) {
                        AppManager.getInstance().jumpActivity(getFragment().getActivity(), LoginActivity.class);
                        return;
                    }
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), BusinessOpportunityActivity.class);
                    break;
                case "版本更新":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), VersionUpdateActivity.class);
                    break;
                case "联系我们":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), ContactUsActivity.class);
                    break;
                case "关于":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), AboutActivity.class);
                    break;
                //第二版
                case "个人信息":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MinePersonalInfoActivity.class);
                    break;

                case "饲料金":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineFeedGoldActivity.class);
                    break;
                case "我的消息":
//                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineMyMessageActivity.class);
                    CommonRecyclerActivity.newCommonRecyclerActivity(mContext, Constants.TYPE_COMMON_MY_MESSAGE, 0);
                    break;
                case "我的积分":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineMyPointsActivity.class);
                    break;
                case "我的关注":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineMyFocusActivity.class);
                    break;
                case "养殖档案":
//                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineBreedingArchivesActivity.class);
                    CommonRecyclerActivity.newCommonRecyclerActivity(mContext, Constants.TYPE_COMMON_BREEDING_ARCHIVE_TYPE, 0);
                    break;
                case "我的收藏":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineMyCollectionActivity.class);
                    break;
                case "系统消息":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineSystemMessageActivity.class);
                    break;
                case "支付密码":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MinePayPasswordActivity.class);
                    break;
                case "建议留言":
                    AppManager.getInstance().jumpActivity(getFragment().getActivity(), MineCallBackActivity.class);
                    break;
            }
        } else if (info instanceof ServiceInfo) {
            //首页服务
            if (v.getId() == R.id.iv_find_car_call_phone_number) { //拨打电话
                String phone = ((ServiceInfo) info).getPhone();
                if (Util.checkNull(phone)) {
                    return;
                }
                Util.callPhone(mContext, phone);
            } else if (v.getId() == R.id.iv_cover) { //查看大图
                List<String> images = ((ServiceInfo) info).getImages();
                if (CommonUtils.isEmpty(images)) {
                    return;
                }
                String url = images.get(0);
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, url);
                if (showBigPictureView != null) {
                    showBigPictureView.show();
                }
            }

        } else if (info instanceof MarketInfo) {
            //市场
            if (v.getId() == R.id.iv_market_phone) {
                String phone = ((MarketInfo) info).getPhone();
                if (Util.checkNull(phone)) {
                    return;
                }
                Util.callPhone(mContext, phone);
            } else if (v.getId() == R.id.iv_cover) {
                List<String> images = ((MarketInfo) info).getImages();
                if (CommonUtils.isEmpty(images)) {
                    return;
                }
                String url = images.get(0);
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, url);
                if (showBigPictureView != null) {
                    showBigPictureView.show();
                }
            } else if (v.getId() == R.id.tv_market_address) {
                final String address = ((MarketInfo) info).getAddress();
                if (!TextUtils.isEmpty(address)) {
                    SelectMapTypeView selectMapTypeView = SelectMapTypeView.create(mContext, new SelectMapTypeView.OnAttachListener() {
                        @Override
                        public void onSelect(int type) {
                            if (type == Constants.TYPE_MAP_BAIDU) {
                                LocationUtil.openBaidu(mContext, address);
                            } else if (type == Constants.TYPE_MAP_GAODE) {
                                LocationUtil.openGaode(mContext, address);
                            } else if (type == Constants.TYPE_MAP_TENGXUN) {

                            }
                        }
                    });
                    if (!selectMapTypeView.isShow()) {
                        selectMapTypeView.show();
                    }

                }
            }

        } else if (info instanceof QuotationInfo) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.QUOTATION_ID, ((QuotationInfo) info).getId());
            bundle.putInt(Constants.TYPE_QUOTATION, ((QuotationInfo) info).getType());
            AppManager.getInstance().jumpActivity(mContext, HomeQuotationHistoryActivity.class, bundle);
        } else if (info instanceof MineOrderInfo) {
            if (Util.isFastDoubleClick()){
                return;
            }
            ButtonClickHelper.getInstance(mContext).doButtonClick(v,info);
        } else if (info instanceof ServerCustomer) {
            boolean isNeedLogin = UserManager.getInstance(mContext).isNeedLogin();
            if (isNeedLogin) {
                AppManager.getInstance().jumpActivity(getFragment().getActivity(), LoginActivity.class);
                return;
            }
            User user = null;
            switch (v.getId()) {
                case R.id.btn_order:
                    if (user != null) {
                        String phoneNum = user.getPhoneNumber();
                        long fromUserId = user.getUserId();
                        long toUserId = ((ServerCustomer) info).getUserId();
                        if (fromUserId == toUserId) {
                            ToastUtil.showToast(mContext, "不能预约自己");
                            return;
                        }
                        showDialog(user.getUserId(), user.getPhoneNumber(), ((ServerCustomer) info).getUserId(), "您收到羊圈的新商机已将您的联系方式发送给对方，请注意接听电话！", "预约信息", "稍后将会把" + ((ServerCustomer) info).getNickname() + "的联系方式发送到您的消息中心，请查收！", "确定预约");
                    }
                    break;
                case R.id.cb_service_zan:

                    boolean isOldCheckedStatus = ((ServerCustomer) info).isIschecked();
                    if (isOldCheckedStatus) {//取消点赞
                        if (user != null) {
                            final MaterialProgress materialProgress = getMaterialProgress();
                            if (materialProgress != null) {
                                materialProgress.show();
                            }
                            deleteZan(user.getUserId(), (ServerCustomer) info);
                        } else {
                            ToastUtil.showToast(mContext, "用户信息异常，请重新登陆再取消赞");
                        }

                    } else {//点赞
                        if (user != null) {
                            final MaterialProgress materialProgress = getMaterialProgress();
                            if (materialProgress != null) {
                                materialProgress.show();
                            }
                            long userId = user.getUserId();
                            String nickName = user.getNickname();
                            String photoUrl = user.getPhotoUrl();
                            long serverId = ((ServerCustomer) info).getServerId();
                            addZan(userId, nickName, photoUrl, serverId + "", (ServerCustomer) info);
                        } else {
                            ToastUtil.showToast(mContext, "用户信息异常，请重新登陆再点赞");
                        }
                    }

                    break;
            }

        } else if (info instanceof Message) {

            switch (v.getId()) {
                case R.id.iv_message_comment:
                case R.id.tv_message_comment:
//                    CommentBottomSheetDialogFragment fragment = new CommentBottomSheetDialogFragment();
//                    fragment.setmMessageId(((Message) info).getMessageId());
//                    fragment.show(mFragment.getFragmentManager(), "dialog");
                    break;
                case R.id.btn_message_start_connect:    //我要联系
                    User user = null;
                    if (user != null) {
                        String phoneNum = user.getPhoneNumber();
                        long fromUserId = user.getUserId();
                        long toUserId = ((Message) info).getUserId();
                        if (fromUserId == toUserId) {
                            ToastUtil.showToast(mContext, "不能联系自己");
                            return;
                        }
                        showDialog(fromUserId, user.getPhoneNumber(), toUserId, "您有新的商机，" + user.getNickname() + "，电话：" + user.getPhoneNumber() + "想与您联系,请您把握商机，及时回复！", "联系信息", "将把您的联系方式" + phoneNum + "发送给对方", "确定联系");
                    }

                    break;

                case R.id.iv_message:   //点击图片的响应

                    break;
            }

        } else if (info instanceof ServiceMessage) {
            Intent intent = new Intent(mContext, ServiceMessageListActivity.class);
            mContext.startActivity(intent);
        } else if (info instanceof MineBreedingArchivesInfo) {
            ButtonClickHelper.getInstance(mContext).doButtonClick(v,info,this);
        }
    }


    private void showDialog(final long fromUserId, final String fromPhoneNumber, final long userId, final String content, String title, String diaContent, String sureText) {
        mDialog = new IosDialog.DialogBuilder(mContext)
                .setInputContent(diaContent)
                .setTitle(title)
                .setAsureText(sureText)
                .setCancelText("取消")
                .addListener(new IosDialog.OnButtonClickListener() {
                    @Override
                    public void onAsureClick() {
                        getMaterialProgress().show();
                        addBusinessOpportunity(fromUserId, fromPhoneNumber, userId, content);
                        mDialog.dismiss();
                    }

                    @Override
                    public void onCancelClick() {
                        mDialog.dismiss();
                    }
                }).create();
    }


    private void addBusinessOpportunity(Long fromUserId, String fromPhoneNumber, Long toUserId, String content) {
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("fromUserId", fromUserId);
        requestParam.put("fromPhoneNumber", fromPhoneNumber);
        requestParam.put("toUserId", toUserId);
        requestParam.put("content", content);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_MINE_ADD_BUSIJNESS, new BaseCallBack<BusinessOpportunityResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "预约失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, BusinessOpportunityResult result) {
                getMaterialProgress().hide();
                if (response != null && response.code() == 200) {
                    if (result == null && result.getStatus() != 200) {
                        return;
                    }
                    BusinessOpportunity businessOpportunity = result.getData();
                    if (businessOpportunity != null) {
                        ToastUtil.showToast(mContext, "已成功预约");
                    } else {
                        ToastUtil.showToast(mContext, "预约失败");
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
//                setNoDataVisible(View.VISIBLE);
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "预约失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }

    /**
     * @param userId
     * @param nickName
     * @param photoUrl
     */
    private void addZan(Long userId, String nickName, String photoUrl, String serverId, final ServerCustomer serverCustomer) {
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userId", userId);
        requestParam.put("nickName", nickName);
        requestParam.put("photoUrl", photoUrl);
        requestParam.put("serverId", serverId);
        OkHttpManager.getInstance().postRequest(HttpUrl.SERVICE_LIST_ADD_ZAN, new BaseCallBack<ServerZanResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "点赞失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerZanResult result) {
                getMaterialProgress().hide();
                if (response != null && response.code() == 200) {
                    if (result == null && result.getStatus() != 200) {
                        return;
                    }
                    ServerZan serverZan = result.getData();
                    if (serverZan != null) {
                        ToastUtil.showToast(mContext, "点赞成功");
                        serverCustomer.setIschecked(true);
                        List<ServerZan> zans = serverCustomer.getZans();
                        if (zans != null) {
                            zans.add(serverZan);
                        }
                        notifyDataSetChanged();
                    } else {
                        ToastUtil.showToast(mContext, "点赞失败");
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
//                setNoDataVisible(View.VISIBLE);
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "点赞失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }


    /**
     * 取消点赞
     */
    private void deleteZan(long userId, final ServerCustomer serverCustomer) {
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userId", userId);
        OkHttpManager.getInstance().postRequest(HttpUrl.SERVICE_LIST_DELETE_ZAN, new BaseCallBack<ServerZanResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "取消赞失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerZanResult result) {
                getMaterialProgress().hide();
                if (response != null && response.code() == 200) {
                    if (result == null && result.getStatus() != 200) {
                        return;
                    }
                    ServerZan serverZan = result.getData();
                    if (serverZan != null) {
                        List<ServerZan> zans = serverCustomer.getZans();
                        if (zans != null) {
                            Iterator<ServerZan> iterator = zans.iterator();
                            while (iterator.hasNext()) {
                                ServerZan zan = iterator.next();
                                if (zan.getUserId().longValue() == serverZan.getUserId().longValue()) {
                                    iterator.remove();
                                    serverCustomer.setIschecked(false);
                                    notifyDataSetChanged();
                                }
                            }
                            for (ServerZan zan : zans) {

                            }
                        }
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
//                setNoDataVisible(View.VISIBLE);
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "取消赞失败");
                        getMaterialProgress().hide();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }


    /**
     * 获得素材元素
     *
     * @param position
     * @return
     */
    public BaseInfo getItem(int position) {
        if (position < 0 || mList == null || position >= mList.size()) {
            return new BaseInfo();
        }
        return mList.get(position);
    }

    /**
     * 获得所有素材元素
     *
     * @return
     */
    public List<BaseInfo> getData() {
        return mList;
    }

    public int getItemPosition(BaseInfo info) {
        if (mList.contains(info)) {
            return mList.indexOf(info);
        }
        return -1;
    }

    public BaseInfo getSelectData() {
        return getItem(mSelectPosition);
    }

    public void updateListItem(int position, BaseInfo newInfo) {
        if (mList != null && position < mList.size()) {
            mList.set(position, newInfo);
        }
    }


    /**
     * 插入一个素材元素并只刷新插入项
     *
     * @param info
     */
    public void addItem(BaseInfo info) {
        if (info != null) {
            mList.add(info);
            int position = mList.indexOf(info);
            notifyItemRangeChanged(position - 1, 2);
//            notifyItemInserted(position);
        }
    }

    /**
     * 插入多个素材元素并只刷新插入项
     *
     * @param infos
     */
    public void addItems(int position, List<BaseInfo> infos) {
        if (infos != null && position < mList.size()) {
            mList.addAll(position, infos);
            notifyItemRangeChanged(position - 1, position + infos.size() - 1);
        }
    }

    /**
     * 指定位置插入一个素材元素并只刷新插入项
     *
     * @param info
     */
    public synchronized void addItem(int position, BaseInfo info) {
        if (info != null) {
            mList.add(position, info);
            notifyDataSetChanged();
        }
    }

    /**
     * 重新插入全部素材元素刷新整个列表
     *
     * @param list
     */
    public void addAll(List<? extends BaseInfo> list) {
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public boolean isNeedAutoScroll() {
        return isNeedAutoScroll;
    }

    public void setNeedAutoScroll(boolean needAutoScroll) {
        isNeedAutoScroll = needAutoScroll;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position, BaseInfo baseInfo);
    }
}
