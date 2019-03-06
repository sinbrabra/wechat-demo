package com.lifang.demo.controller;

import com.google.gson.Gson;
import com.lifang.demo.pojo.bean.LongQrCode;
import com.lifang.demo.pojo.bean.Response;
import com.lifang.demo.pojo.bean.WechatConfig;
import com.lifang.demo.pojo.bean.WechatUrl;
import com.lifang.demo.util.OkHttpClientUtil;
import com.lifang.demo.util.QRCodeUtils;
import com.lifang.demo.util.WechatUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @author czq
 * @date 2019/2/27
 */
@Controller
@RequestMapping(value = "scan")
public class ScanController {

    /**
     * 调用微信js-sdk中的扫一扫开门
     */
    @GetMapping(value = "/qrCode")
    public String scanQrCode(Model model) {
        model.addAttribute("appId", WechatConfig.getAppId());
        model.addAllAttributes(WechatUtil.sign(WechatUtil.getJsapiTicket(), WechatUrl.SERVERS));
        return "scan/scan";
    }

    /**
     * 用二维码信息请求keyfree开门
     */
    @GetMapping(value = "/openDoor")
    @ResponseBody
    public Response openDoor(String qrCode) {
        LoggerFactory.getLogger(ScanController.class).info(qrCode);

        if (StringUtils.isEmpty(qrCode)) {
            return new Response("二维码解析为空字符串");
        }

        int pos = qrCode.indexOf("$$$");
        if (pos != -1) {
            qrCode = qrCode.substring(pos, qrCode.length());
        }

        String data = OkHttpClientUtil.httpGet("http://service.key1.cn/custom-api/temporary/scanOpenDoor?qrCode=" + qrCode);
        if (!StringUtils.isEmpty(data)) {
            Gson gson = new Gson();
            return gson.fromJson(data, Response.class);
        }
        return new Response("网络请求异常");
    }


    /**
     * 获得通行码,设备反扫开门
     */
    @GetMapping(value = "/passphrase")
    public String passphrase() {

        return "passphrase/info";
    }


    /**
     * 请求keyfree获得通行码
     */
    @GetMapping(value = "/getPassphrase")
    @ResponseBody
    public Response getPassphrase() throws Exception {
        String data = OkHttpClientUtil.httpGet("http://service.key1.cn/custom-api/temporary/queryPassCode");
        if (!StringUtils.isEmpty(data)) {
            Gson gson = new Gson();
            LongQrCode longQrCode =  gson.fromJson(data, LongQrCode.class);
            return Response.success(generatePictureBase64String(longQrCode.getData().getLongQrCode()));
        }
        return Response.fail("网络请求异常");
    }

    public String generatePictureBase64String(String userQrCode) throws Exception {
        byte[] bytes = buildQrCodePicByte(userQrCode);
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 将字符串转化为图片字节
     *
     * @param visitorQrCode
     * @return
     */
    private byte[] buildQrCodePicByte(String visitorQrCode) throws Exception {
        BufferedImage bufferedImage = QRCodeUtils.qRCodeCommon(visitorQrCode, 7, QRCodeUtils.hexToByte);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", output);
        return output.toByteArray();
    }

}
