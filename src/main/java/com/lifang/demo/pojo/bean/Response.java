package com.lifang.demo.pojo.bean;

public class Response<T> {

    /**
     * 0为无错误,其他值都表示有错误
     */
    private Integer errorCode = 0;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 返回数据域
     */
    private T data;

    public Response(String errMsg, T data) {
        this.errMsg = errMsg;
        this.data = data;
    }

    public Response() {

    }

    public static <T> Response success(T data){
        return new Response("success", data);
    }

    public static Response fail(String errMsg){
        return new Response(errMsg);
    }

    public Response(String errMsg) {
        errorCode = -1;
        this.errMsg = errMsg;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(Integer errorCode, String errMsg) {
        this.errorCode = errorCode;
        this.errMsg = errMsg;
    }

    public Response(Integer errorCode, String errMsg,T data) {
        this.errorCode = errorCode;
        this.errMsg = errMsg;
        this.data = data;
    }

    /**
     * 若有错误信息则返回true
     * 若无错误信息则返回false
     */
    public boolean hasError() {
        return errorCode != 0;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
