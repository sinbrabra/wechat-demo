package com.lifang.demo.pojo.bean;

import com.google.gson.annotations.SerializedName;
import org.springframework.util.StringUtils;

/**
 * @author czq
 * @date 2019/2/27
 */
public class AccessToken extends ProofTime{

    /**
     * 微信发放的access_token,用于微信接口调用
     */
    @SerializedName("access_token")
    private String accessToken;

    public AccessToken() {
    }

    public AccessToken(String accessToken, Long expiresIn, Long refreshTime) {
        super(expiresIn, refreshTime);
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isNeedRefresh(){
        return  StringUtils.isEmpty(accessToken) || isDue();
    }

}
