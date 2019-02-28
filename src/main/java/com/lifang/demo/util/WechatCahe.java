package com.lifang.demo.util;

import com.lifang.demo.pojo.bean.AccessToken;
import com.lifang.demo.pojo.bean.JsApiTicket;

/**
 * @author czq
 * @date 2019/2/27
 */
public class WechatCahe {

    /**
     * 全局缓存access_token和jsapiTicket
     */
    static class Single{

        private static Single INSTANCE = new Single();

        private AccessToken accessToken;

        private JsApiTicket ticket;

        public static void setINSTANCE(Single INSTANCE) {
            Single.INSTANCE = INSTANCE;
        }

        public AccessToken getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(AccessToken accessToken) {
            this.accessToken = accessToken;
        }

        public JsApiTicket getTicket() {
            return ticket;
        }

        public void setTicket(JsApiTicket ticket) {
            this.ticket = ticket;
        }

        public boolean isAccessTokenNeedRetresh(){
            return accessToken == null || accessToken.isNeedRefresh();
        }

        public boolean isJsapiTicketNeedRetresh(){
            return ticket == null || ticket.isNeedRefresh();
        }

    }

    public static Single getInstance(){
        return Single.INSTANCE;
    }

}
