package cn.oj.codebase.dynamicDatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
* @描述: 动态数据源路由类
* @作者: 郑剑锋
* @日期: 2021/4/17
*/
public class DynamicDataSourceRouting extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

    public DynamicDataSourceRouting(DataSource defaultDataSource, Map<Object, Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

}
