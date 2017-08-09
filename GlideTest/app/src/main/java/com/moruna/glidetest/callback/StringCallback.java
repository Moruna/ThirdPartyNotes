package com.moruna.glidetest.callback;

import okhttp3.Response;

/**
 * Author: Moruna
 * Date: 2017-08-05
 * Copyright (c) 2017,dudu Co.,Ltd. All rights reserved.
 */
public class StringCallback extends Callback {
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().toString();
    }
}
