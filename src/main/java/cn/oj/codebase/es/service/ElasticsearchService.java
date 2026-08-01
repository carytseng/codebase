package cn.oj.codebase.es.service;

import cn.oj.codebase.es.entity.ProductDocument;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: codebase
 * @description: Elasticsearch 搜索服务（新版 Java API Client）
 * @author: 郑剑锋
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticsearchService {

    private final ElasticsearchClient esClient;

    /**
     * 默认索引名
     */
    private static final String DEFAULT_INDEX = "product_index";

    // ==================== 索引管理 ====================

    /**
     * 创建索引
     */
    public boolean createIndex(String indexName) throws IOException {
        boolean exists = existsIndex(indexName);
        if (exists) {
            log.warn("索引 [{}] 已存在", indexName);
            return false;
        }
        CreateIndexResponse response = esClient.indices().create(c -> c
                .index(indexName)
                .settings(s -> s
                        .numberOfShards("1")
                        .numberOfReplicas("1")
                )
                .mappings(m -> m
                        .properties("id", p -> p.long_(l -> l))
                        .properties("name", p -> p.text(t -> t.analyzer("ik_max_word")))
                        .properties("category", p -> p.keyword(k -> k))
                        .properties("price", p -> p.double_(d -> d))
                        .properties("stock", p -> p.integer(i -> i))
                        .properties("description", p -> p.text(t -> t.analyzer("ik_max_word")))
                        .properties("create_time", p -> p.date(d -> d))
                )
        );
        log.info("创建索引 [{}] 成功: {}", indexName, response.acknowledged());
        return response.acknowledged();
    }

    /**
     * 判断索引是否存在
     */
    public boolean existsIndex(String indexName) throws IOException {
        return esClient.indices().exists(ExistsRequest.of(e -> e.index(indexName))).value();
    }

    /**
     * 获取索引信息
     */
    public GetIndexResponse getIndex(String indexName) throws IOException {
        return esClient.indices().get(g -> g.index(indexName));
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String indexName) throws IOException {
        DeleteIndexResponse response = esClient.indices().delete(d -> d.index(indexName));
        log.info("删除索引 [{}] 成功: {}", indexName, response.acknowledged());
        return response.acknowledged();
    }

    // ==================== 文档操作 ====================

    /**
     * 新增文档（自动生成ID）
     */
    public ProductDocument save(String indexName, ProductDocument document) throws IOException {
        IndexResponse response = esClient.index(i -> i
                .index(indexName)
                .document(document)
        );
        log.info("新增文档 ID=[{}], result=[{}]", response.id(), response.result());
        document.setId(Long.valueOf(response.id()));
        return document;
    }

    /**
     * 新增文档（指定ID）
     */
    public ProductDocument saveById(String indexName, String id, ProductDocument document) throws IOException {
        IndexResponse response = esClient.index(i -> i
                .index(indexName)
                .id(id)
                .document(document)
        );
        log.info("新增文档 ID=[{}], result=[{}]", response.id(), response.result());
        return document;
    }

    /**
     * 根据ID查询文档
     */
    public ProductDocument getById(String indexName, String id) throws IOException {
        GetResponse<ProductDocument> response = esClient.get(g -> g
                        .index(indexName)
                        .id(id),
                ProductDocument.class
        );
        if (response.found()) {
            ProductDocument doc = response.source();
            log.info("查询文档 ID=[{}]: {}", id, doc);
            return doc;
        }
        log.warn("文档 ID=[{}] 不存在", id);
        return null;
    }

    /**
     * 更新文档
     */
    public ProductDocument update(String indexName, String id, ProductDocument document) throws IOException {
        UpdateResponse<ProductDocument> response = esClient.update(u -> u
                        .index(indexName)
                        .id(id)
                        .doc(document),
                ProductDocument.class
        );
        log.info("更新文档 ID=[{}], result=[{}]", response.id(), response.result());
        return getById(indexName, id);
    }

    /**
     * 删除文档
     */
    public boolean delete(String indexName, String id) throws IOException {
        DeleteResponse response = esClient.delete(d -> d
                .index(indexName)
                .id(id)
        );
        log.info("删除文档 ID=[{}], result=[{}]", id, response.result());
        return Objects.equals(response.result(), Result.Deleted);
    }

    // ==================== 搜索操作 ====================

    /**
     * 搜索全部
     */
    public List<ProductDocument> searchAll(String indexName) throws IOException {
        SearchResponse<ProductDocument> response = esClient.search(s -> s
                        .index(indexName)
                        .query(q -> q.matchAll(m -> m)),
                ProductDocument.class
        );
        return extractHits(response);
    }

    /**
     * 根据字段精确匹配搜索
     */
    public List<ProductDocument> searchByField(String indexName, String field, String value) throws IOException {
        SearchResponse<ProductDocument> response = esClient.search(s -> s
                        .index(indexName)
                        .query(q -> q.term(t -> t
                                .field(field)
                                .value(v -> v.stringValue(value))
                        )),
                ProductDocument.class
        );
        return extractHits(response);
    }

    /**
     * 全文搜索（模糊匹配）
     */
    public List<ProductDocument> searchByKeyword(String indexName, String keyword) throws IOException {
        SearchResponse<ProductDocument> response = esClient.search(s -> s
                        .index(indexName)
                        .query(q -> q.multiMatch(m -> m
                                .fields("name", "description", "category")
                                .query(keyword)
                        )),
                ProductDocument.class
        );
        return extractHits(response);
    }

    /**
     * 分页搜索
     */
    public List<ProductDocument> searchPage(String indexName, String keyword, int page, int size) throws IOException {
        int from = (page - 1) * size;
        SearchResponse<ProductDocument> response = esClient.search(s -> s
                        .index(indexName)
                        .query(q -> q.multiMatch(m -> m
                                .fields("name", "description", "category")
                                .query(keyword)
                        ))
                        .from(from)
                        .size(size),
                ProductDocument.class
        );
        TotalHits total = response.hits().total();
        log.info("搜索 [{}] 总命中数: {}", keyword, total != null ? total.value() : 0);
        return extractHits(response);
    }

    /**
     * 范围搜索（价格区间）
     */
    public List<ProductDocument> searchByPriceRange(String indexName, double minPrice, double maxPrice) throws IOException {
        SearchResponse<ProductDocument> response = esClient.search(s -> s
                        .index(indexName)
                        .query(q -> q.range(r -> r
                                .number(n -> n
                                        .field("price")
                                        .gte(minPrice)
                                        .lte(maxPrice)
                                )
                        )),
                ProductDocument.class
        );
        return extractHits(response);
    }

    /**
     * 批量插入文档
     */
    public int bulkInsert(String indexName, List<ProductDocument> documents) throws IOException {
        List<BulkOperation> operations = documents.stream()
                .map(doc -> BulkOperation.of(b -> b
                        .index(idx -> idx
                                .index(indexName)
                                .document(doc)
                        )
                ))
                .collect(Collectors.toList());

        BulkResponse response = esClient.bulk(b -> b
                .index(indexName)
                .operations(operations)
        );

        int successCount = 0;
        if (response.errors()) {
            log.error("批量插入存在错误");
            response.items().forEach(item -> {
                if (item.error() != null) {
                    log.error("插入失败: {}", item.error().reason());
                }
            });
        } else {
            successCount = response.items().size();
        }
        log.info("批量插入文档: 成功={}, 总={}", successCount, documents.size());
        return successCount;
    }

    // ==================== 辅助方法 ====================

    /**
     * 提取搜索结果
     */
    private List<ProductDocument> extractHits(SearchResponse<ProductDocument> response) {
        List<ProductDocument> results = new ArrayList<>();
        for (Hit<ProductDocument> hit : response.hits().hits()) {
            ProductDocument doc = hit.source();
            if (doc != null) {
                results.add(doc);
            }
        }
        return results;
    }

    /**
     * 使用默认索引创建文档
     */
    public ProductDocument save(ProductDocument document) throws IOException {
        return save(DEFAULT_INDEX, document);
    }

    /**
     * 使用默认索引搜索全部
     */
    public List<ProductDocument> searchAll() throws IOException {
        return searchAll(DEFAULT_INDEX);
    }

}
