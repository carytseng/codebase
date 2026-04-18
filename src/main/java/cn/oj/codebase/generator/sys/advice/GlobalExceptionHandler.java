package cn.oj.codebase.generator.sys.advice;

import cn.oj.codebase.generator.sys.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName GlobalExceptionHandler.java
 * @Description 全局异常拦截处理
 * @createTime 2021年08月04日 10:27:00
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> sendErrorResponse(Exception exception) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("msg", exception.getMessage());
        result.put("data", null);
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> sendErrorResponseSystem(Exception exception) {
        if (exception instanceof GlobalException) {
            return this.sendErrorResponse(exception);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("msg", "System error");
        result.put("data", null);
        return result;
    }
}
