package com.lifang.demo.pojo.bean;

/**
 * @author czq
 * @date 2019/2/27
 */
public class JsapiTicket {
    /**
     * 微信发放的ticket,用于调用微信js-sdk
     */
    private String ticket;

    /**
     * ticket的有效时长，为7200秒(根据文档说明，可能会更改)，过期则需刷新
     */
    private Long expiresIn;

    /**
     * 最近一次的刷新时间戳，用来判断ticket是否过期
     */
    private Long refreshTime;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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
