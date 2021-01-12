package com.meishe.libbase.adpater;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;

import com.meishe.libbase.adpater.entity.IExpandable;
import com.meishe.libbase.adpater.entity.MultiItemEntity;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * 多项目快速适配器
 * Multi-project fast adapter
 */

public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    /**
     * layouts indexed with their types
     */
    private SparseIntArray layouts;

    private static final int DEFAULT_VIEW_TYPE = -0xff;
    public static final int TYPE_NOT_FOUND = -404;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * 与QuickAdapter#QuickAdapter(Context,int)相同，但与
     * 一些初始化数据
     * @param data A new list is created out of this one to avoid mutable list
     *             在此基础上创建一个新的列表，以避免可变列表
     */
    public BaseMultiItemQuickAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected int getDefItemViewType(int position) {
        T item = mData.get(position);
        if (item != null) {
            return item.getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType, TYPE_NOT_FOUND);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }


    @Override
    public void remove(@IntRange(from = 0L) int position) {
        if (mData == null
                || position < 0
                || position >= mData.size()) {
            return;
        }

        T entity = mData.get(position);
        if (entity instanceof IExpandable) {
            removeAllChild((IExpandable) entity, position);
        }
        removeDataFromParent(entity);
        super.remove(position);
    }

    /**
     * 移除父控件时，若父控件处于展开状态，则先移除其所有的子控件
     * When you remove a parent control, remove all its children first if the parent control is expanded
     * @param parent         父控件实体 Parent control entity
     * @param parentPosition 父控件位置 Parent control position
     */
    protected void removeAllChild(IExpandable parent, int parentPosition) {
        if (parent.isExpanded()) {
            List<MultiItemEntity> chidChilds = parent.getSubItems();
            if (chidChilds == null || chidChilds.size() == 0) {
                return;
            }

            int childSize = chidChilds.size();
            for (int i = 0; i < childSize; i++) {
                remove(parentPosition + 1);
            }
        }
    }

    /**
     * 移除子控件时，移除父控件实体类中相关子 控件数据，避免关闭后再次展开数据重现
     * When removing a child control, remove the relevant child control data from the parent control entity class to avoid reexpanding the data replay after closing
     * @param child 子控件实体
     */
    protected void removeDataFromParent(T child) {
        int position = getParentPosition(child);
        if (position >= 0) {
            IExpandable parent = (IExpandable) mData.get(position);
            if (parent != child) {
                parent.getSubItems().remove(child);
            }
        }
    }

    /**
     * 该方法用于 IExpandable 树形列表。
     * 如果不存在 Parent，则 return -1。
     * This method is used for IExpandable tree lists.
     * If Parent does not exist, return-1
     * @param position 所处列表的位置
     * @return 父 position 在数据列表中的位置
     */
    public int getParentPositionInAll(int position) {
        List<T> data = getData();
        MultiItemEntity multiItemEntity = getItem(position);

        if (isExpandable(multiItemEntity)) {
            IExpandable IExpandable = ( IExpandable) multiItemEntity;
            for (int i = position - 1; i >= 0; i--) {
                MultiItemEntity entity = data.get(i);
                if (isExpandable(entity) && IExpandable.getLevel() > ((IExpandable) entity).getLevel()) {
                    return i;
                }
            }
        } else {
            for (int i = position - 1; i >= 0; i--) {
                MultiItemEntity entity = data.get(i);
                if (isExpandable(entity)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isExpandable(MultiItemEntity item) {
        return item != null && item instanceof IExpandable;
    }
}


