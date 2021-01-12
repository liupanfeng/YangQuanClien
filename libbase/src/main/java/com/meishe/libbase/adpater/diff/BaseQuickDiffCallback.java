package com.meishe.libbase.adpater.diff;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Extend this method to quickly implement DiffUtil
 * 将该方法扩展到快速实现DiffUtil
 * @param <T> Data type
 */
public abstract class BaseQuickDiffCallback<T> extends DiffUtil.Callback {

    private List<T> newList;
    private List<T> oldList;

    public BaseQuickDiffCallback(@Nullable List<T> newList) {
        this.newList = newList == null ? new ArrayList<T>() : newList;
    }

    /**
     * Gets new list.
     * 获得新集合
     * @return the new list
     */
    public List<T> getNewList() {
        return newList;
    }

    /**
     * Gets old list.
     * 获得旧集合
     * @return the old list
     */
    public List<T> getOldList() {
        return oldList;
    }

    public void setOldList(@Nullable List<T> oldList) {
        this.oldList = oldList == null ? new ArrayList<T>() : oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    /**
     * @param oldItem New data
     * @param newItem old Data
     * @return Return false if items are no same
     */
    protected abstract boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem);

    /**
     * @param oldItem New data
     * @param newItem old Data
     * @return Return false if item content are no same
     */
    protected abstract boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem);

    /**
     *  改变有效载荷
     * Varying payload
     * @param oldItem New data
     * @param newItem old Data
     * @return Payload info
     */
    @Nullable
    protected Object getChangePayload(@NonNull T oldItem, @NonNull T newItem) {
        return null;
    }
}
