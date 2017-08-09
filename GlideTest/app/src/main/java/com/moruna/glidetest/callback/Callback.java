package com.moruna.glidetest.callback;

import okhttp3.Response;

/**
 * Author: Moruna
 * Date: 2017-08-05
 * Copyright (c) 2017,dudu Co.,Ltd. All rights reserved.
 */
public abstract class Callback<T> {

    public abstract T parseNetworkResponse(Response response, int id) throws Exception;
}
