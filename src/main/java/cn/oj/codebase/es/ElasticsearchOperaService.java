package cn.oj.codebase.es;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: codebase
 * @description: Elasticsearch operations service (stub - requires ES client library upgrade)
 * @author: 郑剑锋
 * @create: 2021-04-28 14:44
 *
 * Note: Elasticsearch RestHighLevelClient API has been deprecated and removed in newer versions.
 * This file is a stub and requires migration to the new Elasticsearch Java API client.
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html
 **/
@Slf4j
public class ElasticsearchOperaService {

    public String createElasticsearchIndex(String index) {
        log.info("Elasticsearch index creation stubbed for index: {}", index);
        return index;
    }
}
