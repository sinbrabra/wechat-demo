package com.lifang.demo.pojo.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author czq
 * @date 2019/2/28
 */
public class ProofTime {

    /**
     * 凭证有效时间
     * */
    @SerializedName("expires_in")
    private Long expiresIn;

    /**
     * 最近一次的刷新时间戳，用来判断ticket是否过期
     */
    private Long refreshTime;

    public ProofTime() {
    }


    public ProofTime(Long expiresIn, Long refreshTime) {
        this.expiresIn = expiresIn;
        this.refreshTime = refreshTime;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Long refreshTime) {
        this.refreshTime = refreshTime;
    }

    protected boolean isDue(){
        if(expiresIn == null || refreshTime == null){
            return true;
        }else if(System.currentTimeMillis() - refreshTime > expiresIn*1000 - 600*1000){
            return true;
        }else {
            return false;
        }
    }

}
