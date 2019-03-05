package com.lifang.demo.pojo.bean;

/**
 * @author czq
 * @date 2019/3/5
 */
public class LongQrCode {

    /**
     * errorCode : 0
     * errMsg : null
     * data : {"longQrCode":"5C7E43C417391600EB5255A67FAC2D7857E6D8B48EB552F105CE0C71"}
     */

    private int errorCode;
    private Object errMsg;
    private DataBean data;

    public LongQrCode(Object errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * longQrCode : 5C7E43C417391600EB5255A67FAC2D7857E6D8B48EB552F105CE0C71
         */

        private String longQrCode;

        public String getLongQrCode() {
            return longQrCode;
        }

        public void setLongQrCode(String longQrCode) {
            this.longQrCode = longQrCode;
        }
    }
}
