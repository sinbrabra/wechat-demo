package com.lifang.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String scanQrCode(){
        return "scan/scan";
    }
}
