package com.meishe.yangquan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, K extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<K> {
    private static final String TAG = "BaseRecyclerViewAdapter";
    private List<T> list;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    public Context context;

    public int size(){
        return list.size();
    }

    public OnItemLongClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BaseRecyclerViewAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        K holder = onCreateHolder(parent, viewType);
        bindListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public void onBindViewHolder(@NonNull K holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onHolder(holder, list.get(position), position);
        } else {
            onHolder(holder, list.get(position), position, payloads);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public abstract void onHolder(K holder, T bean, int position);

    public abstract void onHolder(@NonNull K holder, T bean, int position, @NonNull List<Object> payloads);

    public abstract K onCreateHolder(ViewGroup parent, int viewType);

    public View getViewByRes(int res) {
        return LayoutInflater.from(context).inflate(res, null, false);
    }

    public View getViewByRes(int res, ViewGroup prent) {
        return LayoutInflater.from(context).inflate(res, prent, false);
    }

    /**
     * 绑定事件
     *
     * @param holder
     */
    private void bindListener(final K holder) {
        if (holder == null) {
            return;
        }
        View itemView = holder.itemView;
        if (itemView == null) {
            return;
        }

        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        if (getItemClickListener() != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItemClickListener().onItemClick(BaseRecyclerViewAdapter.this, v, holder.getLayoutPosition());
                }
            });
        }

        if (getItemLongClickListener() != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return getItemLongClickListener().onItemLongClick(BaseRecyclerViewAdapter.this, v, holder.getLayoutPosition());
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(BaseRecyclerViewAdapter adapter, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseRecyclerViewAdapter adapter, View view, int position);
    }

    public void setNewData(List<T> lt) {
        if (list == null) {
            list = new ArrayList<T>();
        }

        if (lt == null) {
            lt = new ArrayList<T>();
        }
        list = lt;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return list;
    }
}