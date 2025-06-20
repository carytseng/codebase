package cn.oj.codebase.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-17 17:50
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerMethodTitle {

    String value() default "";
}