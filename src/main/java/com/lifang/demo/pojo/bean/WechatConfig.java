package com.lifang.demo.pojo.bean;

/**
 * @author czq
 * @date 2019/2/27
 */
public class WechatConfig {

    private final static String appId = "wx3213404de0c7bade";

    private final static String appSecret = "bee6d39ddb5635b5d6c108283de466ec";

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
