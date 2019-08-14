package com.tgc.oa.exception;

/**
 * @author: Tgc
 * @date: 2019/8/13
 */
public enum ErrorCode {
    NULL_OBJ("10001","对象为空"),
    UNKNOWN_ERROR("10002","系统繁忙请稍后再试");

    private String code;
    private String message;

    private ErrorCode(String code,String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
