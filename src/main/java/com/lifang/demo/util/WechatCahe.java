package com.lifang.demo.util;

import com.lifang.demo.pojo.bean.AccessToken;
import com.lifang.demo.pojo.bean.JsapiTicket;

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

        private JsapiTicket ticket;

        public static Single getInstance(){
            return INSTANCE;
        }

        public static Single getINSTANCE() {
            return INSTANCE;
        }

        public static void setINSTANCE(Single INSTANCE) {
            Single.INSTANCE = INSTANCE;
        }

        public AccessToken getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(AccessToken accessToken) {
            this.accessToken = accessToken;
        }

        public JsapiTicket getTicket() {
            return ticket;
        }

        public void setTicket(JsapiTicket ticket) {
            this.ticket = ticket;
        }
    }

}
