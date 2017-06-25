package com.driverhelper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.driverhelper.R;
import com.jaydenxiao.common.base.BaseRecyclerViewAdapter;

/**
 * Created by Administrator on 2017/6/2.
 */

public final class HistoryRecyvlerViewAdapter extends BaseRecyclerViewAdapter {
    private TextView time;
    private TextView address;

    public HistoryRecyvlerViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected void setEmptyView(View EmptyView) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView address;

        ViewHolder(View view) {
            super(view);
            this.address = (TextView) view.findViewById(R.id.address);
            this.time = (TextView) view.findViewById(R.id.time);
        }
    }
}
