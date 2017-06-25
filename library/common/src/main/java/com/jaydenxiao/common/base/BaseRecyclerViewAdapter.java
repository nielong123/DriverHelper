package com.jaydenxiao.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sherlock on 2016/8/23.
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected LayoutInflater mInflater;
    protected int mCount = 0;
    protected Context mContext = null;
    protected boolean isOnClickVisable = false;
    protected View emptyView;


    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public Object getItem(int position) {
        return null;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public void setisOnClickVisable() {
        isOnClickVisable = true;
    }

    public void setEmptyViewVisable(int visable) {
        if (emptyView != null) {
            emptyView.setVisibility(visable);
        }
    }

    protected abstract void setEmptyView(View EmptyView);
}
