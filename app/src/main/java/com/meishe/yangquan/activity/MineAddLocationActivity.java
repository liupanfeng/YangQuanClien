package com.meishe.yangquan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerViewAdapter;
import com.meishe.yangquan.adapter.BaseRecyclerViewHolder;
import com.meishe.yangquan.gaodelocation.GaoDeLocation;
import com.meishe.yangquan.gaodelocation.GaoDePOISearch;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.SpannableStringUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-添加定位信息页面
 */
public class MineAddLocationActivity extends BaseActivity {



    private ArrayList<PoiItem> mAroundPOIDataList;
    private AroundPOIRecyclerViewAdapter mAroundPOIAdapter;
    private EditText mEditText;
    private SmartRefreshLayout mNormalRefreshLayout;
    private SmartRefreshLayout mSearchRefreshLayout;
    private RecyclerView mAroundLocationList;
    private RecyclerView mSearchAroundLocationList;
    private ImageView mDeletaAllImg;


    private ArrayList<PoiItem> mAroundSearchPOIDataList;

    private GaoDeLocation mGaodeLocation;
    private int mCurNormalPOIPageNum = 0;
    private int mCurSearchPOIPageNum = 0;
    private AMapLocation mCurAMapLocation;
    private String mKeyWord = "";
    private AroundSearchPOIRecyclerViewAdapter mAroundSearchPOIAdapter;



    @Override
    protected int initRootView() {
        return R.layout.activity_mine_add_location;
    }

    @Override
    public void initView() {
//        mTvTitle = findViewById(R.id.tv_title);
//        mTvTitle.setTextColor(Color.WHITE);
//        mIvBack = findViewById(R.id.iv_back);
//        mIvBack.setBackgroundResource(R.mipmap.bar_back_white);

        mEditText = findViewById(R.id.search_edit);
        mAroundLocationList = findViewById(R.id.normal_location_list);
        mSearchAroundLocationList = findViewById(R.id.search_location_list);
        mSearchRefreshLayout = findViewById(R.id.search_refresh_layout);
        mNormalRefreshLayout = findViewById(R.id.normal_refresh_layout);
        mLoading = findViewById(R.id.loading);
        mDeletaAllImg = findViewById(R.id.delete_all);
        initAroundPOIRecyclerView();
        initAroundPOISearchRecyclerView();

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard();

                    mCurSearchPOIPageNum = 0;
                    mAroundSearchPOIDataList.clear();
                    mLoading.show();
                    loadMoreData(true);
                    mLoading.hide();

                    mNormalRefreshLayout.setVisibility(View.GONE);
                    mSearchRefreshLayout.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                mKeyWord = content;
                if(mKeyWord.isEmpty()){
                    mNormalRefreshLayout.setVisibility(View.VISIBLE);
                    mSearchRefreshLayout.setVisibility(View.GONE);
                    mDeletaAllImg.setVisibility(View.GONE);
                }else{
                    mDeletaAllImg.setVisibility(View.VISIBLE);
                }
            }
        });

        mDeletaAllImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
            }
        });
    }

    @Override
    public void initData() {

        mLoading.show();
        mGaodeLocation = new GaoDeLocation(getApplicationContext());
        mGaodeLocation.setLocationChangedListener(new GaoDeLocation.LocationChangedListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
//                if(mAroundPOIDataList.size() == 0){
//                    PoiItem item = new PoiItem(null, null, "不显示我的位置", null);
//                    PoiItem item1 = new PoiItem(location.getCity(), new LatLonPoint(location.getLatitude(), location.getLongitude()), location.getCity(), location.getCity());
//                    mAroundPOIDataList.add(item);
//                    mAroundPOIDataList.add(item1);
//                }
                mCurAMapLocation = location;
                loadMoreData(false);
                mLoading.hide();
            }
        });

    }

    @Override
    public void initTitle() {
//        mTvTitle.setText("设置位置");
    }

    @Override
    public void initListener() {

//        mIvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        mNormalRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData(false);
                mNormalRefreshLayout.finishLoadMore();
            }
        });

        mSearchRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData(true);
                mSearchRefreshLayout.finishLoadMore();
            }
        });

    }

    @Override
    public void release() {
        if(mGaodeLocation != null){
            mGaodeLocation.destroyLocation();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity(){
        hideSoftKeyboard();
        AppManager.getInstance().finishActivity();
    }

    private void initAroundPOIRecyclerView()
    {
        mAroundPOIDataList = new ArrayList<PoiItem>();
        mAroundPOIAdapter = new AroundPOIRecyclerViewAdapter(mAroundPOIDataList);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        mAroundLocationList.setLayoutManager(manager);
        mAroundLocationList.setAdapter(mAroundPOIAdapter);
    }

    private void initAroundPOISearchRecyclerView()
    {
        mAroundSearchPOIDataList = new ArrayList<PoiItem>();
        mAroundSearchPOIAdapter = new AroundSearchPOIRecyclerViewAdapter(mAroundSearchPOIDataList);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        mSearchAroundLocationList.setLayoutManager(manager);
        mSearchAroundLocationList.setAdapter(mAroundSearchPOIAdapter);
    }



    private class AroundPOIRecyclerViewAdapter extends BaseRecyclerViewAdapter<PoiItem, AroundPOIRecyclerViewHolder>{

        public AroundPOIRecyclerViewAdapter(List<PoiItem> list) {
            super(list);
        }

        @Override
        public void onHolder(@NonNull AroundPOIRecyclerViewHolder holder, PoiItem bean, int position, @NonNull List<Object> payloads) {

        }

        @Override
        public void onHolder(AroundPOIRecyclerViewHolder holder, PoiItem bean, int position) {
            holder.city_name.setText(bean.getTitle());
            if(bean.getSnippet() != null){
                holder.address.setText(bean.getSnippet());
                if(bean.getDistance() >= 0){
                    holder.distance.setText(String.valueOf(bean.getDistance())+"m");
                }
                holder.addressLayout.setVisibility(View.VISIBLE);
                holder.city_name.setTextColor(getResources().getColor(R.color.white));
            }else{

//                String title = "不显示我的位置";
//                if(title.equals(bean.getTitle())){
//                    holder.city_name.setTextColor(Color.parseColor("#A2A2A7"));
//                }
//                holder.addressLayout.setVisibility(View.GONE);
//                holder.address.setText("");
//                holder.distance.setText("");
            }
        }

        @Override
        public AroundPOIRecyclerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            setItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseRecyclerViewAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("PoiItem", mAroundPOIDataList.get(position));
                    setResult(MinePersonalInfoActivity.SHOW_ADD_LOCATION_ACTIVITY_RESULT, intent);
                    finishActivity();
                }
            });
            return new AroundPOIRecyclerViewHolder(getViewByRes(R.layout.add_location_list_item_layout, parent));
        }
    }


    private static class AroundPOIRecyclerViewHolder extends BaseRecyclerViewHolder{

        public TextView city_name;
        public TextView address;
        public TextView distance;
        public RelativeLayout addressLayout;

        public AroundPOIRecyclerViewHolder(View itemView) {
            super(itemView);

            city_name = itemView.findViewById(R.id.city_name);
            address = itemView.findViewById(R.id.address);
            distance = itemView.findViewById(R.id.distance);
            addressLayout = itemView.findViewById(R.id.address_layout);
        }
    }

    private class AroundSearchPOIRecyclerViewAdapter extends BaseRecyclerViewAdapter<PoiItem, AroundSearchPOIRecyclerViewHolder>{

        public AroundSearchPOIRecyclerViewAdapter(List<PoiItem> list) {
            super(list);
        }

        @Override
        public void onHolder(@NonNull AroundSearchPOIRecyclerViewHolder holder, PoiItem bean, int position, @NonNull List<Object> payloads) {

        }


        @Override
        public void onHolder(AroundSearchPOIRecyclerViewHolder holder, PoiItem bean, int position) {
            SpannableString spannableString = SpannableStringUtils.matcherSearchK(getResources().getColor(R.color.location_select_font_color), bean.getTitle(), mKeyWord);
            holder.city_name.setText(spannableString);
            holder.address.setText(bean.getSnippet());
            holder.distance.setText(String.valueOf(bean.getDistance())+"m");
            holder.distance.setVisibility(View.GONE);
        }

        @Override
        public AroundSearchPOIRecyclerViewHolder onCreateHolder(ViewGroup parent, int viewType) {

            setItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseRecyclerViewAdapter adapter, View view, int position) {
                   Intent intent = new Intent();
                    intent.putExtra("PoiItem", mAroundPOIDataList.get(position));
                    setResult(MinePersonalInfoActivity.SHOW_ADD_LOCATION_ACTIVITY_RESULT, intent);
                    finishActivity();
                }
            });

            return new AroundSearchPOIRecyclerViewHolder(getViewByRes(R.layout.add_location_list_item_layout, parent));
        }


    }

    private static class AroundSearchPOIRecyclerViewHolder extends BaseRecyclerViewHolder{

        public TextView city_name;
        public TextView address;
        public TextView distance;

        public AroundSearchPOIRecyclerViewHolder(View itemView) {
            super(itemView);

            city_name = itemView.findViewById(R.id.city_name);
            address = itemView.findViewById(R.id.address);
            distance = itemView.findViewById(R.id.distance);

        }
    }

    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) mEditText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void loadMoreData(boolean isKeyWordForSearch){
        if (mCurAMapLocation == null){
            return;
        }
        GaoDePOISearch search = new GaoDePOISearch(getApplicationContext());
        LatLonPoint point = new LatLonPoint(mCurAMapLocation.getLatitude(), mCurAMapLocation.getLongitude());
        int pageNum = 0;
        if(mKeyWord.isEmpty()){
            pageNum = mCurNormalPOIPageNum;
        }else{
            pageNum = mCurSearchPOIPageNum;
        }
        search.doAroundPOISearch(mKeyWord,isKeyWordForSearch, point, 5000, mCurAMapLocation.getCity(), pageNum);
        search.setSearchResultListener(new GaoDePOISearch.SearchResultListener() {
            @Override
            public void onAroundPOISearchResult(PoiResult result) {
                if (result == null){
                    return;
                }
                if(mKeyWord.isEmpty()){
                    mAroundPOIDataList.addAll(result.getPois());
                    mAroundPOIAdapter.notifyDataSetChanged();
                    mCurNormalPOIPageNum++;

                    mNormalRefreshLayout.finishLoadMore();
                }else{
                    mAroundSearchPOIDataList.addAll(result.getPois());
                    mAroundSearchPOIAdapter.notifyDataSetChanged();
                    mCurSearchPOIPageNum++;
                    mSearchRefreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onAroundPOISearchNOData() {
                if(mKeyWord.isEmpty()){

                    if(mAroundPOIDataList.size() != 0){
                        ToastUtil.showToast(getApplicationContext(), R.string.no_data_remain);
                    }else{
                        ToastUtil.showToast(getApplicationContext(), R.string.no_result);
                    }

                }else{

                    if(mAroundSearchPOIDataList.size() != 0){
                        ToastUtil.showToast(getApplicationContext(), R.string.no_data_remain);
                    }else{
                        ToastUtil.showToast(getApplicationContext(), R.string.no_result);
                    }

                }
            }

            @Override
            public void onKeywordPOISearchResult(PoiResult result) {

            }
        });
    }




}
