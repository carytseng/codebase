package cn.cary.codebase.dynamicDatasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

/**
 * @program: codebase
 * @description: 配置映射
 * @author: 郑剑锋
 * @create: 2021-04-17 17:06
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DynamicDataSourceProperty {
    //只映射druid
    private Map<String, Properties> druid;
}