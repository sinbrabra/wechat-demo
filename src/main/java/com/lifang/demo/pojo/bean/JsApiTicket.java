package com.lifang.demo.pojo.bean;

import org.springframework.util.StringUtils;

/**
 * @author czq
 * @date 2019/2/27
 */
public class JsApiTicket extends ProofTime{
    /**
     * 微信发放的ticket,用于调用微信js-sdk
     */
    private String ticket;

    public JsApiTicket() {
    }

    public JsApiTicket(String ticket, Long expiresIn, Long refreshTime) {
        super(expiresIn, refreshTime);
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public boolean isNeedRefresh(){
        return  StringUtils.isEmpty(ticket) || isDue();
    }
}
