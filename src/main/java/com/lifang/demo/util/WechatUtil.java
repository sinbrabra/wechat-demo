package com.lifang.demo.util;

import com.lifang.demo.pojo.bean.WechatConfig;
import com.lifang.demo.pojo.bean.WechatUrl;

public class WechatUtil {

    public static String getAccessToken(){

        return OkHttpClientUtil.httpGet(WechatUrl.ACCESSTOKENAPI
                .replace("APPID", WechatConfig.getAppId())
                .replace("APPSECRET", WechatConfig.getAppSecret()));
    }

}
