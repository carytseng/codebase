package cn.oj.codebase.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;

/**
 * @program: codebase
 * @description: Elasticsearch 配置（新版 Java API Client）
 * @author: 郑剑锋
 * @create: 2021-05-09 16:11
 **/
@Configuration
public class ESConfig {

    @Value("${elasticsearch.hostlist:127.0.0.1:9200}")
    private String hostlist;

    private RestClient restClient;
    private ElasticsearchTransport transport;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // 解析主机地址
        String[] hosts = hostlist.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] parts = hosts[i].split(":");
            httpHosts[i] = new HttpHost(parts[0], Integer.parseInt(parts[1]), "http");
        }

        // 构建 RestClient
        restClient = RestClient.builder(httpHosts)
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Content-Type", "application/json")
                })
                .build();

        // 创建传输层
        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 创建 API Client
        return new ElasticsearchClient(transport);
    }

    @PreDestroy
    public void destroy() {
        try {
            if (transport != null) {
                transport.close();
            }
            if (restClient != null) {
                restClient.close();
            }
        } catch (Exception e) {
            // ignore
        }
    }

}
