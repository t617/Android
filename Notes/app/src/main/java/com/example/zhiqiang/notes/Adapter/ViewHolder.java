package com.example.zhiqiang.notes.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ts13 on 2017/12/19.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    //存储list_item的子View
    private SparseArray<View> mViews;
    //存储list_Item
    private View mConvertView;
    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView= itemView;
        mViews= new SparseArray<View>();
    }
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView= LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder= new ViewHolder(context, itemView, parent);
        return holder;
    }
    public <T extends View> T getView(int viewId) {
        View view= mViews.get(viewId);
        if (view== null) {
            //创建view
            view= mConvertView.findViewById(viewId);
            //将view放入mViews
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}