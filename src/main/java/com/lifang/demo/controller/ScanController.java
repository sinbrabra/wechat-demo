package com.lifang.demo.controller;

import com.lifang.demo.pojo.bean.WechatConfig;
import com.lifang.demo.pojo.bean.WechatUrl;
import com.lifang.demo.util.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author czq
 * @date 2019/2/27
 */
@Controller
@RequestMapping(value = "scan")
public class ScanController {

    /**
     * 调用微信js-sdk中的扫一扫
     * */
    @GetMapping(value = "/qrCode")
    public String scanQrCode(Model model){
        model.addAttribute("appId", WechatConfig.getAppId());
        model.addAllAttributes(WechatUtil.sign(WechatUtil.getJsapiTicket(), WechatUrl.servers));
        return "/scan/scan";
    }

    /**
     * 调用微信js-sdk中的扫一扫
     * */
    @GetMapping(value = "/js")
    @ResponseBody
    public Map<String, String> js(){
        return WechatUtil.sign(WechatUtil.getJsapiTicket(), WechatUrl.servers);
    }

}
