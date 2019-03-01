package com.lifang.demo.controller;

import com.lifang.demo.util.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author czq
 * @date 2019/3/1
 */
@Controller
@RequestMapping("/wx")
public class WechatTokenCheckController {


    /**
     * 验证请求是否是微信发来的
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping("/token")
    @ResponseBody
    public String cheackTolen(String signature, String timestamp, String nonce, String echostr){

        if(signature != null && WechatUtil.checkSignature(signature, timestamp, nonce)){
            return echostr;
        }

        return "";
    }

}
