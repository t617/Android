package com.example.zhiqiang.vq.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.zhiqiang.vq.R;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {
    private View view;
    private ListView listView;
    private RefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        bindView();
        setListener();
        initialData();
        return view;
    }
    public void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });
    }
    public void bindView() {
        listView = (ListView) view.findViewById(R.id.reposList);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
    }
    public void initialData() {
        List<Map<String, Object>> listItems = new ArrayList<>();
        SimpleAdapter simpleAdapter;
        for (int i = 0; i < 10; i++) {
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("name", "呵呵");
            listItem.put("language", "呵呵呵");
            listItem.put("description", "呵呵呵嗝");
            listItems.add(listItem);
        }
        simpleAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.repos,
                new String[]{"name", "language", "description"}, new int[]{R.id.name, R.id.language, R.id.description});
        listView.setAdapter(simpleAdapter);
    }
}
