package com.example.zhiqiang.lab9.adapter;

/**
 * Created by zhiqiang on 2017/12/12.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhiqiang.lab9.model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 妖精狸 on 2017/10/13.
 */

public abstract class CardAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener = null;

    public CardAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public void addData(User user) {
        Map<String, Object> listItem = new LinkedHashMap<>();
        listItem.put("login", user.getLogin());
        listItem.put("id", user.getId());
        listItem.put("blog", user.getBlog());
        mDatas.add((T) listItem);
        notifyDataSetChanged();
    }

    public T getName(int position) {
        return mDatas.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
    public abstract void convert(ViewHolder holder, T t);
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void removeItem(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        mDatas.removeAll(mDatas);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener = onItemClickListener;
    }
}