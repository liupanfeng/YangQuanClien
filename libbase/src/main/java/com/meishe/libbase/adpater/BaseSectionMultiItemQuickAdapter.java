package com.meishe.libbase.adpater;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.meishe.libbase.adpater.entity.IExpandable;
import com.meishe.libbase.adpater.entity.MultiItemEntity;
import com.meishe.libbase.adpater.entity.SectionMultiEntity;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 * 部分多项目适配器基类
 * Partial multi-project adapter base class
 */
public abstract class BaseSectionMultiItemQuickAdapter<T extends SectionMultiEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    private SparseIntArray layouts;

    private static final int DEFAULT_VIEW_TYPE = -0xff;
    public static final int TYPE_NOT_FOUND = -404;

    protected int mSectionHeadResId;
    protected static final int SECTION_HEADER_VIEW = 0x00000444;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *与QuickAdapter#QuickAdapter(Context,int)相同，但与
     * 一些初始化数据
     * @param sectionHeadResId The section head layout id for each item 每个项目的s头布局id
     * @param data             A new list is created out of this one to avoid mutable list 在此基础上创建一个新的列表，以避免可变列表
     */
    public BaseSectionMultiItemQuickAdapter(int sectionHeadResId, List<T> data) {
        super(data);
        this.mSectionHeadResId = sectionHeadResId;
    }

    @Override
    protected int getDefItemViewType(int position) {
        T item = mData.get(position);

        if (item != null) {
            /*
            * check the item type include header or not
            * 检查项目类型是否包含标题
            * */
            return item.isHeader ? SECTION_HEADER_VIEW : item.getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    /**
     * Sets default view type layout.
     * 设置默认的视图类型布局
     * @param layoutResId the layout res id 布局id
     */
    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        /*
        * add this to check viewType of section
        * 添加它来检查section的viewType
        * */
        if (viewType == SECTION_HEADER_VIEW) {
            return createBaseViewHolder(getItemView(mSectionHeadResId, parent));
        }

        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType, TYPE_NOT_FOUND);
    }

    /**
     * collect layout types you need
     * 收集您需要的布局类型
     * @param type        The key of layout type 布局类型的键
     * @param layoutResId The layoutResId of layout type 布局类型的layoutResId
     */
    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }

    @Override
    protected boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || type == SECTION_HEADER_VIEW;
    }

    @Override
    public void onBindViewHolder(@NonNull K holder, int position) {
        switch (holder.getItemViewType()) {
            case SECTION_HEADER_VIEW:
                setFullSpan(holder);
                convertHead(holder, getItem(position - getHeaderLayoutCount()));
                break;
            default:
                super.onBindViewHolder(holder, position);
                break;
        }
    }

    /**
     * Convert head.
     * 转换头
     * @param helper the helper helper
     * @param item   the item 项目
     */
    protected abstract void convertHead(K helper, T item);

    @Override
    public void remove(@IntRange(from = 0L) int position) {
        if (mData == null || position < 0
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
     * @param parent         父控件实体
     * @param parentPosition 父控件位置
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
     * 移除子控件时，移除父控件实体类中相关子控件数据，避免关闭后再次展开数据重现
     * When removing a child control, remove the relevant child control data from the parent control entity class to avoid reexpanding the data replay after closing
     * @param child 子控件实体
     */
    protected void removeDataFromParent(T child) {
        int position = getParentPosition(child);
        if (position >= 0) {
            IExpandable parent = (IExpandable) mData.get(position);
            parent.getSubItems().remove(child);
        }
    }
}


