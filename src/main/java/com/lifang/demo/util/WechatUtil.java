package com.lifang.demo.util;

import com.google.gson.Gson;
import com.lifang.demo.pojo.bean.AccessToken;
import com.lifang.demo.pojo.bean.JsApiTicket;
import com.lifang.demo.pojo.bean.WechatConfig;
import com.lifang.demo.pojo.bean.WechatUrl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WechatUtil {

    /**
     * accessToken未过期从缓存中直接取，已过期则刷新
     * 存入全局缓存中
     * **/
    public static String getAccessToken(){

        WechatCahe.Single single = WechatCahe.getInstance();

        if(single.isAccessTokenNeedRetresh()){
            String data = OkHttpClientUtil.httpGet(WechatUrl.ACCESSTOKENAPI
                    .replace("APPID", WechatConfig.getAppId())
                    .replace("APPSECRET", WechatConfig.getAppSecret()));

            Gson gson = new Gson();
            AccessToken accessToken = gson.fromJson(data, AccessToken.class);
            single.setAccessToken(new AccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn(), System.currentTimeMillis()));
            System.out.println(accessToken.getAccessToken());
            return accessToken.getAccessToken();
        }

        return single.getAccessToken().getAccessToken();
    }

    /**
     * jsapiTicket未过期从缓存中直接取，已过期则刷新
     * 存入全局缓存中
     * **/
    public static String getJsapiTicket(){

        WechatCahe.Single single = WechatCahe.getInstance();

        if(single.isJsapiTicketNeedRetresh()){
            String data = OkHttpClientUtil.httpGet(WechatUrl.JSAPI
                    .replace("ACCESS_TOKEN", getAccessToken()));

            Gson gson = new Gson();
            JsApiTicket jsApiTicket = gson.fromJson(data, JsApiTicket.class);
            single.setTicket(new JsApiTicket(jsApiTicket.getTicket(), jsApiTicket.getExpiresIn(), System.currentTimeMillis()));
            System.out.println(jsApiTicket.getTicket());
            return jsApiTicket.getTicket();
        }

        return single.getTicket().getTicket();
    }

    public static Map<String, String> sign(String jsapiTicket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println("string1:"+string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8")); //对string1 字符串进行SHA-1加密处理
            signature = byteToHex(crypt.digest());  //对加密后字符串转成16进制
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    public static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


}
