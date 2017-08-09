package com.moruna.glidetest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moruna.glidetest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Moruna
 * Date: 2017-08-05
 * Copyright (c) 2017,dudu Co.,Ltd. All rights reserved.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<String> list;
    private LayoutInflater inflater;
    private Context context;
    private static final String TAG = "RecyclerViewAdapter";
    public RecyclerViewAdapter(Context context, ArrayList<String> list) {
        Log.e(TAG, "RecyclerViewAdapter: " );
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: " );
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position))
                .placeholder(R.mipmap.ic_launcher) //占位符
                .error(R.mipmap.ic_launcher_round) //异常占位符
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存功能
                //圆角，发现加载比较缓慢
                //.bitmapTransform(new CropCircleTransformation(context))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
