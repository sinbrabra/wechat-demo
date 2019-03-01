package com.lifang.demo.pojo.bean;

public class WechatUrl {

    /**
     * 获得accessToken的接口地址
     */
    public final static String ACCESSTOKENAPI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获得jsapiTicket的接口地址
     */
    public final static String JSAPI = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    /**
     * 需调用微信js服务器地址
     */
    public final static String SERVERS = "http://face.key1.cn/scan/qrCode";
}
