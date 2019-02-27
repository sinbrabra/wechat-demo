package com.lifang.demo.util;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * @author czq
 * @date 2019/2/27
 */
public class OkHttpClientUtil {

    private final static OkHttpClient client = new OkHttpClient();

    /**
     * http同步get请求
     * @param url
     * @return
     */
    public static String httpGet(String url){
        String data = "";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if(response.isSuccessful()){
                data = response.body().string();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return data;
    }

}
