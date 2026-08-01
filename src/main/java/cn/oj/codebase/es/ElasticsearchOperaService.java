package cn.oj.codebase.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: codebase
 * @description: Elasticsearch 操作服务（新版 Java API Client）
 * @author: 郑剑锋
 **/
@Slf4j
@Service
public class ElasticsearchOperaService {

    @Autowired(required = false)
    private ElasticsearchClient esClient;

    /**
     * 创建索引（简化版）
     */
    public String createElasticsearchIndex(String index) {
        try {
            if (esClient == null) {
                log.warn("Elasticsearch 客户端未配置，无法创建索引: {}", index);
                return index;
            }
            boolean exists = esClient.indices().exists(e -> e.index(index)).value();
            if (!exists) {
                esClient.indices().create(c -> c.index(index));
                log.info("成功创建索引: {}", index);
            } else {
                log.info("索引已存在: {}", index);
            }
        } catch (Exception e) {
            log.error("创建索引失败: {}", index, e);
        }
        return index;
    }

    /**
     * 判断索引是否存在
     */
    public boolean existsIndex(String index) {
        try {
            if (esClient == null) {
                return false;
            }
            return esClient.indices().exists(e -> e.index(index)).value();
        } catch (Exception e) {
            log.error("检查索引失败: {}", index, e);
            return false;
        }
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String index) {
        try {
            if (esClient == null) {
                return false;
            }
            return esClient.indices().delete(d -> d.index(index)).acknowledged();
        } catch (Exception e) {
            log.error("删除索引失败: {}", index, e);
            return false;
        }
    }

}
