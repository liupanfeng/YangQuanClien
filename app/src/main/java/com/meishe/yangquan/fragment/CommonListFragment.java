package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.helper.DataHelper;
import com.meishe.yangquan.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 21:08
 * @Description : 通用的列表fragment
 */
public class CommonListFragment extends BaseRecyclerFragment implements DataHelper.OnCallBackListener {

    private static final String TAG = "CommonListFragment" ;
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
     *
     * @param isNeedLazLoad 是否需要懒加载
     * @param type  页面类型
     * @param subType   请求类型  如果不需要可以传0
     * @return
     */
    public static CommonListFragment newInstance(boolean isNeedLazLoad,int type,int subType) {
        CommonListFragment commonListFragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.COMMON_TYPE, type);
        bundle.putInt(Constants.COMMON_SUB_TYPE, subType);
        bundle.putBoolean(Constants.COMMON_LAZ_LOAD_TYPE, isNeedLazLoad);
        commonListFragment.setArguments(bundle);
        return commonListFragment;
    }


    /**
     *
     * @param isNeedLazLoad 是否需要懒加载
     * @param type  页面类型
     * @param subType   请求类型  如果不需要可以传0
     * @param listType   请求类型  如果不需要可以传0
     * @return
     */
    public static CommonListFragment newInstance(boolean isNeedLazLoad,int type,int listType,int subType) {
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
        View view=inflater.inflate(R.layout.fragment_common_list_view,container,false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mNoDataView = view.findViewById(R.id.ll_no_data);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {
        Log.d(TAG,"initData mType="+mType);
        if (!mLazLoad){
            getDataFromServer();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (mLazLoad){
            Log.d(TAG,"lazyLoad mType="+mType);
            mIsLoadMore=false;
            getDataFromServer();
        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                mPageNum = 1;
                mIsLoadMore = false;
                getDataFromServer();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsLoadMore = true;
                getDataFromServer();
            }
        });
    }

    private void getDataFromServer() {
//        showLoading();
        //全部订单
        DataHelper.getInstance().setOnCallBackListener(this);
        switch (mType){
            case Constants.TYPE_COMMON_MY_ORDER_TYPE:
                DataHelper.getInstance().getOrderData(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MY_MESSAGE:
                DataHelper.getInstance().getMyMessageData(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_FEED_GOLD_TYPE:
                DataHelper.getInstance().getFeedGoldDataFromServer(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_BREEDING_ARCHIVE_TYPE:
                DataHelper.getInstance().getBreedingArchivesData(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MINE_MY_FOCUS:  //我的-我的关注
                DataHelper.getInstance().getFocusData(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MINE_MY_FANS:  //我的-我的粉丝
                DataHelper.getInstance().getFansData(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MINE_COLLECT_SHOPPING:
                //收藏店铺
            case Constants.TYPE_COMMON_MINE_COLLECT_GOODS:
                //收藏商品
                DataHelper.getInstance().getCollectionDataFromServer(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);

                break;
            case Constants.TYPE_COMMON_FEED_FEED:
                //饲料-饲料
            case Constants.TYPE_COMMON_FEED_CORN:
                //饲料-玉米
            case Constants.TYPE_COMMON_FEED_TOOLS:
                //饲料-工具
                DataHelper.getInstance().getShoppingData(mList,mSubType,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore);

                break;

            case Constants.TYPE_COMMON_MARKET:
                //首页-市场
                DataHelper.getInstance().getMarketDataFromServer(mList,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore,mListType,mSubType);

                break;
            case Constants.TYPE_COMMON_SERVICE:
                //首页-服务
                DataHelper.getInstance().getServiceDataFromServer(mList,mPageSize,mPageNum,
                        mIsLoadFinish,mIsLoadMore,mListType,mSubType);

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
        changeNoDataViewVisible(View.GONE);
        hideUIState();
    }

    @Override
    public void onSuccess(List<? extends BaseInfo> baseInfos,int pageSize,int pageNum) {
        changeNoDataViewVisible(View.GONE);
        hideUIState();
        mPageNum=pageNum+1;
        mList.addAll(mList.size(), baseInfos);
        mAdapter.addAll(mList);
    }


    @Override
    public void onFailure(Exception e) {
        hideUIState();
        changeNoDataViewVisible(View.VISIBLE);
    }

    @Override
    public void onError(Exception e) {
        hideUIState();
        changeNoDataViewVisible(View.VISIBLE);
    }


    private void changeNoDataViewVisible(int visible){
        mNoDataView.setVisibility(visible);
        if (visible==View.VISIBLE){
            mAdapter.addAll(null);
        }
    }
}
