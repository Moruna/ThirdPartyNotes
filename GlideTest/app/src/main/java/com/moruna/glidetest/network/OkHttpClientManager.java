package com.moruna.glidetest.network;

import android.os.Handler;
import android.os.Looper;

import com.moruna.glidetest.callback.ResultCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Moruna
 * Date: 2017-08-05
 * Copyright (c) 2017,dudu Co.,Ltd. All rights reserved.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager instance;
    private OkHttpClient httpClient;
    private Handler handler;

    public OkHttpClientManager() {
        httpClient = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientManager.class) {
                if (instance == null) {
                    instance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }

    //同步加载
    private String _getSyncString(String url) throws IOException {
        Response execute = _getSync(url);
        return execute.body().string();
    }

    private Response _getSync(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    //异步加载
    private void _getAsync(String url, ResultCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        deliverResult(callback, request);
    }


    private void deliverResult(final ResultCallback callback, Request request) {
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(result);
                    }
                });
            }
        });
    }


    /************** 开放方法 *************/
    public static String getAsString(String url) throws IOException {
        return getInstance()._getSyncString(url);
    }

    public static void getAsync(String url, ResultCallback callback) {
        getInstance()._getAsync(url, callback);
    }
}
