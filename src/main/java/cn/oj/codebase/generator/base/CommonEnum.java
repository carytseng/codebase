package cn.oj.codebase.generator.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用枚举
 */
@Getter
@AllArgsConstructor
public enum CommonEnum {

    /**
     * 是、逻辑删除0-正常、启用
     */
    ZERO("0", "0"),

    /**
     * 否、逻辑删除1-删除、停用
     */
    ONE("1", "1");


    private final String code;
    private final String msg;


}
