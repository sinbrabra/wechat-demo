package com.lifang.demo.util;

import com.swetake.util.Qrcode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

/**
 * 功能描述:
 * <p/>
 * 版权所有：杭州立方控股
 * <p/>
 * 未经本公司许可，不得以任何方式复制或使用本程序任何部分
 *
 * @author jiqinwei 新增日期：2017-09-19
 * @author jiqinwei 修改日期：2017-09-19
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class QRCodeUtils {
    public static final int hexToByte = 1;
    public static final int strToByte = 2;

    /**
     * 生成二维码(QRCode)图片
     *
     * @param content 存储内容
     * @param imgPath 图片路径
     */
    public void encoderQRCode(String content, String imgPath) {
        this.encoderQRCode(content, imgPath, "png", 7);
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param content 存储内容
     * @param output  输出流
     */
    public void encoderQRCode(String content, OutputStream output) {
        this.encoderQRCode(content, output, "png", 7);
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param content 存储内容
     * @param imgPath 图片路径
     * @param imgType 图片类型
     */
    public void encoderQRCode(String content, String imgPath, String imgType, int byteType) {
        this.encoderQRCode(content, imgPath, imgType, 7, byteType);
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param content 存储内容
     * @param output  输出流
     * @param imgType 图片类型
     */
    public void encoderQRCode(String content, OutputStream output, String imgType, int byteType) {
        this.encoderQRCode(content, output, imgType, 7);
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param content 存储内容
     * @param imgPath 图片路径
     * @param imgType 图片类型
     * @param size    二维码尺寸
     */
    public void encoderQRCode(String content, String imgPath, String imgType, int size, int byteType) {
        try {
            BufferedImage bufImg = this.qRCodeCommon(content, size, byteType);

            File imgFile = new File(imgPath);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param content 存储内容
     * @param output  输出流
     * @param imgType 图片类型
     * @param size    二维码尺寸
     */
    public void encoderQRCode(String content, OutputStream output, String imgType, int size, int byteType) {
        try {
            BufferedImage bufImg = this.qRCodeCommon(content, size, byteType);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, imgType, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码(QRCode)图片的公共方法
     *
     * @param content 存储内容
     * @param size    二维码尺寸
     * @return
     */
    public static BufferedImage qRCodeCommon(String content, int size, int byteType) throws Exception {
        BufferedImage bufImg = null;
        Qrcode qrcodeHandler = new Qrcode();
        // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
        qrcodeHandler.setQrcodeErrorCorrect('M');
        qrcodeHandler.setQrcodeEncodeMode('B');
        // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
        qrcodeHandler.setQrcodeVersion(size);
        // 获得内容的字节数组，设置编码格式
        byte[] contentBytes = null;
        switch (byteType) {
            case hexToByte:
                contentBytes = hexStr2Bytes(content);
                break;
            case strToByte:
                contentBytes = content.getBytes();
                break;
            default:
                contentBytes = content.getBytes();
                break;
        }
        // 图片尺寸
        int imgSize = 67 + 12 * (size - 1);
        bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = bufImg.createGraphics();
        // 设置背景颜色
        gs.setBackground(Color.WHITE);
        gs.clearRect(0, 0, imgSize, imgSize);

        // 设定图像颜色> BLACK
        gs.setColor(Color.BLACK);
        // 设置偏移量，不设置可能导致解析出错
        int pixoff = 2;
        // 输出内容> 二维码
        if (contentBytes.length > 0 && contentBytes.length < 800) {
            boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
            for (int i = 0; i < codeOut.length; i++) {
                for (int j = 0; j < codeOut.length; j++) {
                    if (codeOut[j][i]) {
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        } else {
            throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
        }
        gs.dispose();
        bufImg.flush();
        return bufImg;
    }

    /**
     * bytes字符串转换为Byte值
     *
     * @param src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        String hexString = src.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            // 转换成字节需要两个16进制的字符，高位在先
            // 将hex 转换成byte   "&" 操作为了防止负数的自动扩展
            // hex转换成byte 其实只占用了4位，然后把高位进行右移四位
            // 然后“|”操作  低四位 就能得到 两个 16进制数转换成一个byte.
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }


}