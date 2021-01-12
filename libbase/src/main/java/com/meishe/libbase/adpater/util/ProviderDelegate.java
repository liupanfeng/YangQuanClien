package com.meishe.libbase.adpater.util;

import android.util.SparseArray;

import com.meishe.libbase.adpater.provider.BaseItemProvider;


/**
 * https://github.com/chaychan
 * 供应商委托类
 * Supplier delegation class
 */
public class ProviderDelegate {

    private SparseArray<BaseItemProvider> mItemProviders = new SparseArray<>();

    /**
     * Register provider.
     * 注册供应商
     * @param provider the provider
     */
    public void registerProvider(BaseItemProvider provider){
        if (provider == null){
            throw new ItemProviderException("ItemProvider can not be null");
        }

        int viewType = provider.viewType();

        if (mItemProviders.get(viewType) == null){
            mItemProviders.put(viewType,provider);
        }
    }

    /**
     * Get item providers sparse array.
     * 获取项目提供程序数组
     * @return the sparse array
     */
    public SparseArray<BaseItemProvider> getItemProviders(){
        return mItemProviders;
    }

}
