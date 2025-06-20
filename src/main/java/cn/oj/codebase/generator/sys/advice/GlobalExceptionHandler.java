package cn.oj.codebase.generator.sys.advice;

import cn.oj.codebase.generator.sys.exception.GlobalException;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName GlobalExceptionHandler.java
 * @Description 全局异常拦截处理
 * @createTime 2021年08月04日 10:27:00
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler<T> {

    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R sendErrorResponse(Exception exception) {
        return R.failed(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R sendErrorResponseSystem(Exception exception) {
        if (exception instanceof GlobalException) {
            return this.sendErrorResponse(exception);
        }
        return R.failed(ApiErrorCode.FAILED);
    }
}
