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

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { WechatConfig.getToken(), timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr);
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature) : false;
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

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }


    public static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


}
