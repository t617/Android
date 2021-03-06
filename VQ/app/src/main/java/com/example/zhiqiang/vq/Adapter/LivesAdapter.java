package com.example.zhiqiang.vq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhiqiang.vq.R;
import com.example.zhiqiang.vq.entity.Lives;

import java.util.List;


public class LivesAdapter extends BaseAdapter {

    private List<Lives> mLives;
    private Context mContext;
    private LayoutInflater mInflater;

    public LivesAdapter(Context context, List<Lives> lives) {
        mLives = lives;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mLives.size();
    }

    @Override
    public Object getItem(int position) {
        return mLives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_lives, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Lives lives = mLives.get(position);
        holder.tvNameAndCity.setText("来自" + lives.getCity() + "的" + lives.getCreator().getName());
        holder.description.setText(lives.getCreator().getDescription());
        Glide.with(mContext)
                .load(lives.getCreator().getPortrait())
                .centerCrop()
                .placeholder(R.mipmap.icon)
                .crossFade()
                .into(holder.ivPortrait);

        return convertView;
    }

    private static class ViewHolder {
        TextView tvNameAndCity;
        ImageView ivPortrait;
        TextView description;

        public ViewHolder(View convertView) {
            tvNameAndCity = (TextView) convertView.findViewById(R.id.tvNameAndCity);
            ivPortrait = (ImageView) convertView.findViewById(R.id.ivPortrait);
            description = (TextView) convertView.findViewById(R.id.description);
        }
    }

}
