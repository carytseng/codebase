package cn.oj.codebase.dynamicDatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/***
 * @描述: 多数据源配置
 * @作者: 郑剑锋
 * @日期: 2021/4/17
 */
@Configuration
@Slf4j
public class DynamicDataSourceConfig {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private DynamicDataSourceProperty dynamicDataSourceProperty;

    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        //拿到Spring容器
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        /*获取yml所有数据源配置*/
        Map<String, Properties> datasource = dynamicDataSourceProperty.getDruid();
        Map<Object, Object> dataSourceMap = new HashMap<>(5);
        Optional.ofNullable(datasource).ifPresent(map -> {
            for (Map.Entry<String, Properties> entry : map.entrySet()) {
                String dataSourceId = entry.getKey();
                if (!isSupportedDataSource(dataSourceId)) {
                    log.debug("skip non-datasource druid config: {}", dataSourceId);
                    return;
                }
                //创建数据源对象
                DruidDataSource dataSource = null;
                try {
                    dataSource = configureDataSource(entry);
                } catch (Exception e) {
                    log.error("failed to create datasource: {}", dataSourceId, e);
                    return;
                }
                /*bean工厂注册每个数据源bean*/
                registerDataSource(listableBeanFactory, dataSourceMap, dataSourceId, dataSource);
            }
        });
        if (!dataSourceMap.containsKey(DataSourceType.MASTER.name())) {
            DruidDataSource masterDataSource = buildMasterDataSource();
            registerDataSource(listableBeanFactory, dataSourceMap, DataSourceType.MASTER.getCode(), masterDataSource);
        }
        //AbstractRoutingDataSource设置主从数据源
        return new DynamicDataSourceRouting(beanFactory.getBean(DataSourceType.MASTER.name(), DataSource.class), dataSourceMap);
    }

    /**
     * 配置@Transactional注解事务
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    //自动装载配置
    private DruidDataSource configureDataSource(Map.Entry<String, Properties> entry) throws Exception {
        Properties value = entry.getValue();
        return (DruidDataSource) DruidDataSourceFactory.createDataSource(value);
    }

    private void registerDataSource(DefaultListableBeanFactory beanFactory, Map<Object, Object> dataSourceMap,
                                    String dataSourceId, DruidDataSource dataSource) {
        String beanName = DataSourceType.getName(dataSourceId);
        beanFactory.registerSingleton(beanName, dataSource);
        dataSourceMap.put(beanName, dataSource);
    }

    private boolean isSupportedDataSource(String dataSourceId) {
        for (DataSourceType type : DataSourceType.values()) {
            if (type.getCode().equals(dataSourceId)) {
                return true;
            }
        }
        return false;
    }

    private DruidDataSource buildMasterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dynamicDataSourceProperty.getUrl());
        dataSource.setUsername(dynamicDataSourceProperty.getUsername());
        dataSource.setPassword(dynamicDataSourceProperty.getPassword());
        dataSource.setDriverClassName(dynamicDataSourceProperty.getDriverClassName());
        return dataSource;
    }
}
