package com.example.myrobot.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by DELL 15 on 2017/9/18.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
