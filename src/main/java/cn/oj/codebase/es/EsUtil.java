package cn.oj.codebase.es;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @description es工具类封装 (stub - requires ES client library upgrade)
 * @author 郑剑锋
 * @updateTime 2022/3/18 08:57
 *
 * Note: Elasticsearch RestHighLevelClient API has been deprecated and removed in newer versions.
 * This file is a stub and requires migration to the new Elasticsearch Java API client.
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html
 */
@Component
public class EsUtil {

    private static Logger log = LoggerFactory.getLogger(EsUtil.class);

    public void init() {
        log.info("Elasticsearch utility initialized");
    }
}

