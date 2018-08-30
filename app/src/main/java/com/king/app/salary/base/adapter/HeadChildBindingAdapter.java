package com.king.app.salary.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/17 9:33
 */
public abstract class HeadChildBindingAdapter<VH extends ViewDataBinding, VI extends ViewDataBinding, H, I> extends RecyclerView.Adapter {

    private final int TYPE_HEAD = 0;
    private final int TYPE_ITEM = 1;

    protected List<Object> list;

    public void setList(List<Object> list) {
        this.list = list;
    }

    public OnHeadChildListener<H, I> onHeadChildListener;

    protected abstract Class getItemClass();

    public void setOnHeadChildListener(OnHeadChildListener<H, I> onHeadChildListener) {
        this.onHeadChildListener = onHeadChildListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getClass() == getItemClass()) {
            return TYPE_ITEM;
        }
        return TYPE_HEAD;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            VH binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                    , getHeaderRes(), parent, false);
            BindingHolder holder = new BindingHolder(binding.getRoot());
            binding.getRoot().setOnClickListener(view -> onClickHead(view, holder.getLayoutPosition(), (H) list.get(holder.getLayoutPosition())));
            return holder;
        }
        else {
            VI binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                    , getItemRes(), parent, false);
            BindingHolder holder = new BindingHolder(binding.getRoot());
            binding.getRoot().setOnClickListener(view -> onClickItem(view, holder.getLayoutPosition(), (I) list.get(holder.getLayoutPosition())));
            return holder;
        }
    }

    protected void onClickHead(View view, int position, H head) {
        if (onHeadChildListener != null) {
            onHeadChildListener.onClickHead(view, position, head);
        }
    }

    protected void onClickItem(View view, int position, I item) {
        if (onHeadChildListener != null) {
            onHeadChildListener.onClickItem(view, position, item);
        }
    }

    protected abstract int getHeaderRes();

    protected abstract int getItemRes();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEAD) {
            VH binding = DataBindingUtil.getBinding(holder.itemView);
            onBindHead(binding, position, (H) list.get(position));
            binding.executePendingBindings();
        }
        else {
            VI binding = DataBindingUtil.getBinding(holder.itemView);
            onBindItem(binding, position, (I) list.get(position));
            binding.executePendingBindings();
        }
    }

    protected abstract void onBindHead(VH binding, int position, H head);

    protected abstract void onBindItem(VI binding, int position, I item);

    @Override
    public int getItemCount() {
        return list == null ? 0:list.size();// 首尾分别为header和footer
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        public BindingHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnHeadChildListener<H, I> {
        void onClickHead(View view, int position, H item);
        void onClickItem(View view, int position, I item);
    }
}
