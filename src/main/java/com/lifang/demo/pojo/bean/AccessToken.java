package com.lifang.demo.pojo.bean;

/**
 * @author czq
 * @date 2019/2/27
 */
public class AccessToken {

    /**
     * 微信发放的access_token,用于微信接口调用
     */
    private String accessToken;

    /**
     * access_token的有效时长，为7200秒(根据文档说明，可能会更改)，过期则需刷新
     */
    private Long expiresIn;

    /**
     * 最近一次的刷新时间戳，用来判断access_token是否过期
     */
    private Long refreshTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Long refreshTime) {
        this.refreshTime = refreshTime;
    }
}
