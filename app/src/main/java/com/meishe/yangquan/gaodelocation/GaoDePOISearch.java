package com.meishe.yangquan.gaodelocation;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.meishe.yangquan.R;

import java.util.List;

public class GaoDePOISearch {

    private static final String TAG = "GaoDePOISearch";
    private Context mContext = null;
    private SearchResultListener mSearchResultListener = null;

    public GaoDePOISearch(Context context){
        mContext = context;
    }


    public void setSearchResultListener(SearchResultListener listener){
        mSearchResultListener = listener;
    }

    public void doAroundPOISearch(String keyWord,boolean isKeyWordForSearch,
                                  LatLonPoint lp, int radius, String city, int pageNum){
        final PoiSearch.Query query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(pageNum);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        if(!isKeyWordForSearch){
            // 设置搜索区域为以lp点为圆心，其周围radius米范围
            poiSearch.setBound(new PoiSearch.SearchBound(lp, radius, true));
        }
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rcode) {
                if (rcode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(query)) {// 是否是同一条
                            if(mSearchResultListener != null){
                                mSearchResultListener.onAroundPOISearchResult(result);
                            }

                            List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                            List<SuggestionCity> suggestionCities = result
                                    .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                            if (poiItems != null && poiItems.size() > 0) {
                                PoiItem item = poiItems.get(0);
                                Log.d(TAG, "getCityName" + item.getCityName());
                                Log.d(TAG, "cityCode: " + item.getCityCode());
                                Log.d(TAG, "AdCode: " + item.getAdCode());
                                Log.d(TAG, "AdName: " + item.getAdName());
                                Log.d(TAG, "businessArea: " + item.getBusinessArea());
                                Log.d(TAG, "Direction: " + item.getDirection());
                                Log.d(TAG, "ParkingType: " + item.getParkingType());
                                Log.d(TAG, "ProvinceName: " + item.getProvinceName());
                                Log.d(TAG, "ProvinceCode: " + item.getProvinceCode());
                                Log.d(TAG, "Snippet: " + item.getSnippet());
                                Log.d(TAG, "PoiId: " + item.getPoiId());
                                Log.d(TAG, "ShopID: " + item.getShopID());
                                Log.d(TAG, "Title: " + item.getTitle());
                            } else if (suggestionCities != null
                                    && suggestionCities.size() > 0) {
                                showSuggestCity(suggestionCities);
                            } else {
                                if(mSearchResultListener != null){
                                    mSearchResultListener.onAroundPOISearchNOData();
                                }
                                Log.d(TAG, "onPoiSearched: " + R.string.no_result);
                            }
                        }
                    } else {
                        if(mSearchResultListener != null){
                            mSearchResultListener.onAroundPOISearchNOData();
                        }
                        Log.d(TAG, "onPoiSearched: " + R.string.no_result);
                    }
                } else {
                    if(mSearchResultListener != null){
                        mSearchResultListener.onAroundPOISearchNOData();
                    }
                    Log.d(TAG, "onPoiSearched: error code: " + rcode);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        StringBuilder stringBuilder = new StringBuilder("推荐城市").append('\n');
        for (SuggestionCity suggestionCity : cities) {
            stringBuilder.append("城市名称:").append(suggestionCity.getCityName()).append("城市区号:")
                    .append(suggestionCity.getCityCode()).append("城市编码:")
                    .append(suggestionCity.getAdCode()).append('\n');
        }
        Log.d(TAG, "showSuggestCity: " + stringBuilder.toString());
    }

    public void doKeyworkPOISearch(String keyWord, String city, int pageNum){
        final PoiSearch.Query query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(pageNum);// 设置查第一页
        query.setCityLimit(true);

        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rcode) {
                if (rcode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(query)) {// 是否是同一条

                            if(mSearchResultListener != null){
                                mSearchResultListener.onKeywordPOISearchResult(result);
                            }

                            // 取得搜索到的poiitems有多少页
                            List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                            List<SuggestionCity> suggestionCities = result
                                    .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                            if (poiItems != null && poiItems.size() > 0) {

                            } else if (suggestionCities != null
                                    && suggestionCities.size() > 0) {
                                showSuggestCity(suggestionCities);
                            } else {
                                Log.d(TAG, "onPoiSearched: " + R.string.no_result);
                            }
                        }
                    } else {
                        Log.d(TAG, "onPoiSearched: " + R.string.no_result);
                    }
                } else {
                    Log.d(TAG, "onPoiSearched: error code: " + rcode);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    public interface SearchResultListener{
        void onAroundPOISearchResult(PoiResult result);
        void onKeywordPOISearchResult(PoiResult result);
        void onAroundPOISearchNOData();
    }
}
