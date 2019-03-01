package com.lifang.demo.pojo.bean;

/**
 * @author czq
 * @date 2019/2/27
 */
public class WechatConfig {

    private final static String appId = "wxcdce85fe39acd77f";

    private final static String appSecret = "8ab02e832859b6141acd2b868124b3bb";

    private final static String token = "lifang";

    public static String getAppId() {
        return appId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getToken() {
        return token;
    }
}
