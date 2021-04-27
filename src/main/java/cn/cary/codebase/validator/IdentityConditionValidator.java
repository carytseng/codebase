package cn.cary.codebase.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @program:
 * @description: 条件校验器
 * @author: 郑剑锋
 * @create: 2021-04-23 14:33
 **/
@Slf4j
public class IdentityConditionValidator implements ConstraintValidator<IdentityCondition, Object> {

    @Override
    public void initialize(IdentityCondition identityCondition) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        boolean flag = true;
        String message="参数校验错误";

        if(!flag) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
        return flag;
    }
}
