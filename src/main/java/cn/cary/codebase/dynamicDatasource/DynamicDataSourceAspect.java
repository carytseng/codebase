package cn.cary.codebase.dynamicDatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/***
 * @描述: 用于切换数据源
 * @作者: 郑剑锋
 * @日期: 2021/4/17
 */
@Component
@Aspect
@Order(-1) //保证在@Transactional之前执行，必须加上，不然无法分辨是哪个数据源在执行事务
@Slf4j
public class DynamicDataSourceAspect {

    @Before("service()")
    public void before(JoinPoint point) {// 切点为service包下的方法，@Transactional，@TargetDateSource注解最好是放在service下的方法上。
        try {
            TargetDateSource annotationOfClass = point.getTarget().getClass().getAnnotation(TargetDateSource.class);
            String methodName = point.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
            Method method = point.getTarget().getClass().getMethod(methodName, parameterTypes);
            TargetDateSource methodAnnotation = method.getAnnotation(TargetDateSource.class);
            methodAnnotation = methodAnnotation == null ? annotationOfClass : methodAnnotation;
            DataSourceType dataSourceType = methodAnnotation != null && methodAnnotation.value() != null ? methodAnnotation.value() : DataSourceType.MASTER;
            DataSourceContextHolder.setDataSource(dataSourceType.name());
        } catch (NoSuchMethodException e) {
            log.error("error", e);
        }
    }

    @After("service()")
    public void after(JoinPoint point) {
        DataSourceContextHolder.clearDataSource();
    }

    @Pointcut("execution(* com.unicom.*.server.service..*.*(..))")
    public void service() {
    }

    @Pointcut("execution(* com.unicom.*.server.dao..*.*(..))")
    public void dao() {
    }
}
