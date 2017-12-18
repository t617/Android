package com.example.zhiqiang.lab9.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhiqiang on 2017/12/18.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;//存储list_Item的子View
    private View mConvertView;//存储list_Item

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        ViewHolder holder = new ViewHolder(context, itemView, parent);//为当前ItemView创建ViewHolder
        return holder;
    }
    /**
     * 通过viewId获取控件，类似于findViewById()，但速度更快
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            //创建view
            view = mConvertView.findViewById(viewId);
            //将view存入mViews
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
