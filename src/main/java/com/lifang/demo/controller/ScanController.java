package com.lifang.demo.controller;

import com.lifang.demo.util.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public String scanQrCode(){
        return WechatUtil.getAccessToken();
    }
}
