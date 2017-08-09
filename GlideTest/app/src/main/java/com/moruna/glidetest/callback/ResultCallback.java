package com.moruna.glidetest.callback;

/**
 * Author: Moruna
 * Date: 2017-08-07
 * Copyright (c) 2017,dudu Co.,Ltd. All rights reserved.
 */
public abstract class ResultCallback<T> {
    public abstract void onResult(T result);

    public abstract void onError(Exception e);
}
