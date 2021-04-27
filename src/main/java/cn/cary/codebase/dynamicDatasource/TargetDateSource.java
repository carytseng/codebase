package cn.cary.codebase.dynamicDatasource;

import java.lang.annotation.*;

/**
* @描述: 数据源类型注解
* @作者: 郑剑锋
* @日期: 2021/4/17
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface TargetDateSource {
    DataSourceType value() default DataSourceType.MASTER;
}
