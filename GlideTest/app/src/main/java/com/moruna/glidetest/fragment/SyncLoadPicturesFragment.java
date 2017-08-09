package com.moruna.glidetest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moruna.glidetest.R;
import com.moruna.glidetest.adapter.RecyclerViewAdapter;
import com.moruna.glidetest.constants.NetAddress;
import com.moruna.glidetest.network.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Moruna
 * Date: 2017-08-07
 * 同步加载图片
 */
public class SyncLoadPicturesFragment extends Fragment {
    private static final String TAG = "AsyncLoadPicturesFragment";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter viewAdapter;
    private ArrayList<String> list = new ArrayList<>();
    private int page = 1;

    public SyncLoadPicturesFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pics, container, false);
        init(view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadApi(page);
            }
        }).start();
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        //使用StaggeredGridLayoutManager需设置，不然没有竖直方向的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL));
        viewAdapter = new RecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollToEnd(recyclerView)) {
                    Log.e(TAG, "onScrolled: page=" + page);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            page += 1;
                            loadApi(page);
                        }
                    }).start();
                }
            }
        });
    }

    private void loadApi(int page) {
        try {
            String result = OkHttpClientManager.getAsString(NetAddress.ADDRESS_GANK + page);
            list.addAll(getUrl(result));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewAdapter.notifyDataSetChanged();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getUrl(String result) {
        ArrayList<String> list = new ArrayList<>();
        String url;
        try {
            JSONObject json = new JSONObject(result);
            JSONArray array = json.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                url = array.getJSONObject(i).getString("url");
                Log.e(TAG, "getUrl: " + url);
                if (!TextUtils.isEmpty(url)) {
                    list.add(url);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private boolean isScrollToEnd(RecyclerView view) {
        if (view == null) return false;
        if (view.computeVerticalScrollExtent() + view.computeVerticalScrollOffset()
                >= view.computeVerticalScrollRange())
            return true;
        return false;
    }
}
