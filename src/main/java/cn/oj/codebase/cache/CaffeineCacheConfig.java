package cn.oj.codebase.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CaffeineCacheConfig.java
 * @author 郑剑锋
 * @version 1.0.0
 * @Description 缓存手动加载
 * @createTime 2021/8/4 9:41 上午
 */
@Configuration
public class CaffeineCacheConfig {

    /**
     * @title doorCaffeineCache
     * @description 
     * @author 郑剑锋 
     * @updateTime 2021/8/4 9:37 上午
     */
    @Bean("doorCaffeineCache")
    public Cache<String, String> doorCaffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入超过 2 分钟后过期
                .expireAfterWrite(2, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(50)
                // 缓存的最大条数
                .maximumSize(10000)
                .removalListener((String key, String graph, RemovalCause cause) -> System.out.println("监听键值移除")
                )
                .build();
    }

}
