package cn.cary.codebase.dynamicDatasource;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * @描述: 数据源类型
 * @作者: 郑剑锋
 * @日期: 2021/4/17
 */
@Getter
@AllArgsConstructor
public enum DataSourceType {
    MASTER("master"),
    SLAVE("slave"),
    PRESTO("presto");

    private String code;


    public static String getName(String code) {
        for (DataSourceType ty : values()) {
            if (ty.getCode().equals(code)) {
                return ty.name();
            }
        }
        throw new IllegalArgumentException("invalid type : " + code);
    }
}
