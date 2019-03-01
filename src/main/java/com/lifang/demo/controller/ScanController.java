package com.lifang.demo.controller;

import com.google.gson.Gson;
import com.lifang.demo.pojo.bean.Response;
import com.lifang.demo.pojo.bean.WechatConfig;
import com.lifang.demo.pojo.bean.WechatUrl;
import com.lifang.demo.util.OkHttpClientUtil;
import com.lifang.demo.util.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
     * 调用微信js-sdk中的扫一扫开门
     * */
    @GetMapping(value = "/qrCode")
    public String scanQrCode(Model model){
        model.addAttribute("appId", WechatConfig.getAppId());
        model.addAllAttributes(WechatUtil.sign(WechatUtil.getJsapiTicket(), WechatUrl.SERVERS));
        return "scan/scan";
    }

    /**
     * 用二维码信息请求keyfree开门
     * */
    @GetMapping(value = "/openDoor")
    @ResponseBody
    public Response openDoor(String qrCode){
        String data = OkHttpClientUtil.httpGet("http://service.key1.cn/custom-api/temporary/scanOpenDoor?qrCode="+qrCode);
        if(!StringUtils.isEmpty(data)){
            Gson gson = new Gson();
            return gson.fromJson(data, Response.class);
        }
        return new Response("网络请求异常");
    }


    /**
     * 获得通行码,设备反扫开门
     * */
    @GetMapping(value = "/passphrase")
    public String passphrase(){

        return "passphrase/info";
    }


    /**
     * 请求keyfree获得通行码
     * */
    @GetMapping(value = "/getPassphrase")
    @ResponseBody
    public Response getPassphrase(){
        String data = OkHttpClientUtil.httpGet("http://service.key1.cn/custom-api/temporary/queryPassCode");
        if(!StringUtils.isEmpty(data)){
            Gson gson = new Gson();
            return gson.fromJson(data, Response.class);
        }
        return new Response("网络请求异常");
    }

}
