package cn.oj.codebase.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @program: codebase
 * @description: Elasticsearch 工具类封装
 * @author: 郑剑锋
 **/
@Slf4j
@Component
public class EsUtil {

    @Autowired(required = false)
    private ElasticsearchClient esClient;

    @PostConstruct
    public void init() {
        if (esClient != null) {
            log.info("Elasticsearch 客户端已初始化");
        } else {
            log.warn("Elasticsearch 客户端未配置，ES功能不可用");
        }
    }

    /**
     * 获取 Elasticsearch 客户端
     */
    public ElasticsearchClient getClient() {
        return esClient;
    }

    /**
     * 检查 ES 是否可用
     */
    public boolean isAvailable() {
        try {
            if (esClient == null) {
                return false;
            }
            return esClient.ping().value();
        } catch (Exception e) {
            log.warn("Elasticsearch 不可用: {}", e.getMessage());
            return false;
        }
    }

}
