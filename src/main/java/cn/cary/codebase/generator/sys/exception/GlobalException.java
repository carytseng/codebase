package cn.cary.codebase.generator.sys.exception;

import lombok.Data;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName GlobalException.java
 * @Description 全局异常
 * @createTime 2021年08月04日 10:22:00
 */
@Data
public class GlobalException extends RuntimeException {

    private int code;

    private String message;

    public GlobalException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
