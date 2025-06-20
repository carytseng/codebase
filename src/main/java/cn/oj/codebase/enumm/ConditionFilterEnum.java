package cn.oj.codebase.enumm;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @描述: 枚举例子
 * @参数:
 * @返回类型:
 * @作者: 郑剑锋
 * @日期: 2021/4/17
 */
@Getter
@AllArgsConstructor
public enum ConditionFilterEnum {


    SINGLE("单条件", 1, ""), OR("或", 2, " OR "), AND("且", 3, " AND "),
    DATE_SINGLE("单日期", 4, ""), DATE_RANGE("日期范围", 5, "");

    private String name;

    /**
     * 1、yml添加配置
     * mybatis:
     * configuration:
     * default-enum-type-handler: com.baomidou.mybatisplus.extension.handlers.MybatisEnumTypeHandler
     * 2、使用mybatis—plus save数据会将枚举值存入
     */
    @EnumValue
    private Integer value;

    private String operator;

    /* 根据name返回对应的枚举 */
    public static ConditionFilterEnum of(String name) {
        for (ConditionFilterEnum ty : values()) {
            if (Objects.equals(ty.getName(), name)) {
                return ty;
            }
        }
        throw new IllegalArgumentException("invalid name : " + name);
    }

}
