package cn.cary.codebase.generator.sys.exception;

import cn.cary.codebase.generator.sys.enumm.Code;
import cn.cary.codebase.generator.sys.enumm.ResponseCode;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName GlobalException.java
 * @Description 全局异常
 * @createTime 2021年08月04日 10:22:00
 */
public final class GlobalException extends RuntimeException {
    private Code responseCode;
    private String code;
    private String msg;
    private Exception exception;

    public GlobalException(Code responseCode, Exception exception) {
        this.responseCode = responseCode;
        this.exception = exception;
    }

    public GlobalException(String code, String msg, Exception exception) {
        this.code = code;
        this.msg = msg;
        this.exception = exception;
    }

    public GlobalException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(Code responseCode) {
        this.responseCode = responseCode;
    }

    public Code getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(Code responseCode) {
        this.responseCode = responseCode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "GlobalException{responseCode=" + this.responseCode + ", code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + '}';
    }

    public static GlobalException getInstance(String code, String msg) {
        return new GlobalException(code, msg);
    }

    public static GlobalException getInstance(String code, String msg, Exception exception) {
        return new GlobalException(code, msg, exception);
    }

    public static GlobalException getInstance(String msg) {
        return new GlobalException(ResponseCode.FAILED.getCode(), msg);
    }

    public static GlobalException getInstance(String msg, Exception exception) {
        return new GlobalException(ResponseCode.FAILED.getCode(), msg, exception);
    }

    public static GlobalException getInstance(Code responseCode) {
        return new GlobalException(responseCode);
    }

    public static GlobalException getInstance(Code responseCode, Exception exception) {
        return new GlobalException(responseCode, exception);
    }

    public static void throwEx(Code responseCode) {
        throw new GlobalException(responseCode);
    }

    public static void throwEx(Code responseCode, Exception exception) {
        throw new GlobalException(responseCode, exception);
    }

    public static void throwEx(String msg) {
        throw new GlobalException(ResponseCode.FAILED.getCode(), msg);
    }

    public static void throwEx(String msg, Exception exception) {
        throw new GlobalException(ResponseCode.FAILED.getCode(), msg, exception);
    }

    public static void throwEx(String code, String msg) {
        throw new GlobalException(code, msg);
    }

    public static void throwEx(String code, String msg, Exception exception) {
        throw new GlobalException(code, msg, exception);
    }
}
