package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.helper.DataHelper;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.NetworkUtil;
import com.meishe.yangquan.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_USER_INFO;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 21:08
 * @Description : 通用的列表fragment
 */
public class CommonListFragment extends BaseRecyclerFragment implements DataHelper.OnCallBackListener {

    private static final String TAG = "CommonListFragment";
    protected List<BaseInfo> mList = new ArrayList<>();

    /*这个是请求那一类接口*/
    private int mType;
    /*这个是请求数据的类型*/
    private int mSubType;
    /*是否懒加载，解决ViewPage的预加载问题*/
    private boolean mLazLoad;
    private View mNoDataView;
    /*推荐还是最新*/
    private int mListType;

    /**
     * @param isNeedLazLoad 是否需要懒加载
     * @param type          页面类型
     * @param subType       请求类型  如果不需要可以传0
     * @return
     * 注意这个方法没有ListType 如果需要listType不能用这个方法
     */
    public static CommonListFragment newInstance(boolean isNeedLazLoad, int type, int subType) {
        CommonListFragment commonListFragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.COMMON_TYPE, type);
        bundle.putInt(Constants.COMMON_SUB_TYPE, subType);
        bundle.putBoolean(Constants.COMMON_LAZ_LOAD_TYPE, isNeedLazLoad);
        commonListFragment.setArguments(bundle);
        return commonListFragment;
    }


    /**
     * @param isNeedLazLoad 是否需要懒加载
     * @param type          页面类型
     * @param listType      请求类型  如果不需要可以传0   //listType 属于页面类型
     * @param subType       请求类型  如果不需要可以传0     // 店铺属于商店id
     * @return
     */
    public static CommonListFragment newInstance(boolean isNeedLazLoad, int type, int listType, int subType) {
        CommonListFragment commonListFragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.COMMON_TYPE, type);
        bundle.putInt(Constants.COMMON_SUB_TYPE, subType);
        bundle.putInt(Constants.MARKET_LIST_TYPE, listType);
        bundle.putBoolean(Constants.COMMON_LAZ_LOAD_TYPE, isNeedLazLoad);
        commonListFragment.setArguments(bundle);
        return commonListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.COMMON_TYPE);
            mSubType = arguments.getInt(Constants.COMMON_SUB_TYPE);
            mListType = arguments.getInt(Constants.MARKET_LIST_TYPE);
            mLazLoad = arguments.getBoolean(Constants.COMMON_LAZ_LOAD_TYPE);
        }
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_common_list_view, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mNoDataView = view.findViewById(R.id.ll_no_data);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {
        Log.d(TAG, "initData mType=" + mType);
        if (!mLazLoad) {
            getDataFromServer(mType,mSubType);
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (mLazLoad) {
            Log.d(TAG, "lazyLoad mType=" + mType);
            mIsLoadMore = false;
            getDataFromServer(mType,mSubType);
        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refrashData();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsLoadMore = true;
                getDataFromServer(mType,mSubType);
            }
        });
    }

    private void refrashData() {
        mList.clear();
        mPageNum = 1;
        mIsLoadMore = false;
        getDataFromServer(mType,mSubType);
    }

    private void getDataFromServer(int type,int subType) {
        if (!NetworkUtil.checkedNetWork(mContext)){
            ToastUtil.showToast("网络不可用，请稍后再试！");
            hideUIState();
            return;
        }
        //全部订单
        DataHelper.getInstance().setOnCallBackListener(this);
        switch (type) {
            case Constants.TYPE_COMMON_MY_ORDER_TYPE:
                DataHelper.getInstance().getOrderData(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MY_MESSAGE:
                DataHelper.getInstance().getMyMessageData(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_FEED_GOLD_TYPE:
                DataHelper.getInstance().getFeedGoldDataFromServer(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_BREEDING_ARCHIVE_TYPE:
                DataHelper.getInstance().getBreedingArchivesData(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MINE_MY_FOCUS:  //我的-我的关注
                DataHelper.getInstance().getFocusData(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MINE_MY_FANS:  //我的-我的粉丝
                DataHelper.getInstance().getFansData(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MINE_COLLECT_SHOPPING:
                //收藏店铺
            case Constants.TYPE_COMMON_MINE_COLLECT_GOODS:
                //收藏商品
                DataHelper.getInstance().getCollectionDataFromServer(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);

                break;
            case Constants.TYPE_COMMON_FEED_FEED:
                //饲料-饲料
            case Constants.TYPE_COMMON_FEED_CORN:
                //饲料-玉米
            case Constants.TYPE_COMMON_FEED_TOOLS:
                //饲料-工具
                DataHelper.getInstance().getShoppingData(mList, subType, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore);

                break;

            case Constants.TYPE_COMMON_MARKET:
                //首页-市场
                DataHelper.getInstance().getMarketDataFromServer(mList, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore, mListType, subType);

                break;
            case Constants.TYPE_COMMON_SERVICE:
                //首页-服务
                DataHelper.getInstance().getServiceDataFromServer(mList, mPageSize, mPageNum,
                        mIsLoadFinish, mIsLoadMore, mListType, subType);

                break;
            case Constants.TYPE_COMMON_QUOTATION:
                //首页-行情
                DataHelper.getInstance().getQuotationDataFromServer(mList, subType, mPageSize, mPageNum,
                        mIsLoadMore);

                break;
            case Constants.TYPE_COMMON_SHOPPING:
                //饲料-商店-商品
                DataHelper.getInstance().getShoppingGoodsData(mList, mPageSize, mPageNum,
                        mIsLoadMore, mListType, mSubType);
                break;

            //////////////////////////商版///////////////////////////////////////////
            case Constants.TYPE_COMMON_BU_ORDER:
                //饲料-商店-订单管理
                DataHelper.getInstance().getGoodsDataFromServer(mList, mListType, mPageSize, mPageNum,
                        mIsLoadMore);
            case Constants.TYPE_COMMON_BU_REFUND:
                //饲料-商店-退货管理
                DataHelper.getInstance().getRefundDataFromServer(mList, mSubType, mPageSize, mPageNum,
                        mIsLoadMore);
                break;
            default:
                break;
        }
    }


    @Override
    public void onShowNoData() {
        hideUIState();
        changeNoDataViewVisible(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        ToastUtil.showToast("暂无更多内容！");
        changeNoDataViewVisible(View.GONE);
        hideUIState();
    }

    @Override
    public void onSuccess(String content) {

    }

    @Override
    public void onSuccess(List<? extends BaseInfo> baseInfos, int pageSize, int pageNum) {
        changeNoDataViewVisible(View.GONE);
        hideUIState();
        mPageNum = pageNum + 1;
        mList.addAll(mList.size(), baseInfos);
        mAdapter.addAll(mList);
    }


    @Override
    public void onFailure(Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideUIState();
                changeNoDataViewVisible(View.VISIBLE);
            }
        });
    }

    @Override
    public void onError(Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideUIState();
                changeNoDataViewVisible(View.VISIBLE);
            }
        });

    }

    @Override
    public void onSuccessNeedDeleteItem() {

    }


    private void changeNoDataViewVisible(int visible) {
        mNoDataView.setVisibility(visible);
        if (visible == View.VISIBLE) {
            mAdapter.addAll(null);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this); //解除注册
    }

    /**
     * On message event.
     * 消息事件
     *
     * @param event the event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        mType = event.getEventType();
        mSubType = event.getListType();
        getDataFromServer(mType,mSubType);
    }
}
