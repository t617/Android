package com.example.zhiqiang.electronicdictionary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhiqiang.electronicdictionary.Data.Present;
import com.example.zhiqiang.electronicdictionary.R;

import java.util.List;

public class PresentListAdapter extends BaseAdapter {

    private Context context;
    private List<Present> dataList;

    public  PresentListAdapter(Context context, List<Present> dataList) {


        this.context = context;
        this.dataList = dataList;
    }

    public List<Present> getDataList() {
        return dataList;
    }

    @Override
    public int getCount() {
        if (isNUll()) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        if (isNUll()) {
            return null;
        }
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;
        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.present_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (TextView) convertView.findViewById(R.id.first);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setText(dataList.get(i).getFirstLetter());
        viewHolder.name.setText(dataList.get(i).getName());
        return convertView;
    }

    private boolean isNUll() {
        return dataList == null;
    }

    private class ViewHolder {
        public TextView image;
        public TextView name;
    }
}
