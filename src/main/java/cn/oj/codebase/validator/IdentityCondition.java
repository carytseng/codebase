package cn.oj.codebase.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @program:
 * @description: 自定义条件校验器注解
 * @author: 郑剑锋
 * @create: 2021-04-23 14:30
 **/
@Documented
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentityConditionValidator.class)
public @interface IdentityCondition {
    String message() default "输入条件不合法"; Class[] groups() default {}; Class[] payload() default {};
}
