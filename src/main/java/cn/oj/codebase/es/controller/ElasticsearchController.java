package cn.oj.codebase.es.controller;

import cn.oj.codebase.es.entity.ProductDocument;
import cn.oj.codebase.es.service.ElasticsearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: codebase
 * @description: Elasticsearch 搜索控制器
 * @author: 郑剑锋
 **/
@Slf4j
@RestController
@RequestMapping("/es")
@RequiredArgsConstructor
@Tag(name = "Elasticsearch 搜索管理")
public class ElasticsearchController {

    private final ElasticsearchService esService;

    private static final String DEFAULT_INDEX = "product_index";

    // ==================== 索引管理 ====================

    @PostMapping("/index/{indexName}")
    @Operation(summary = "创建索引")
    public Map<String, Object> createIndex(@PathVariable String indexName) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = esService.createIndex(indexName);
            result.put("code", success ? 0 : 1);
            result.put("msg", success ? "创建成功" : "索引已存在");
            result.put("data", indexName);
        } catch (Exception e) {
            log.error("创建索引失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/index/{indexName}")
    @Operation(summary = "删除索引")
    public Map<String, Object> deleteIndex(@PathVariable String indexName) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = esService.deleteIndex(indexName);
            result.put("code", success ? 0 : 1);
            result.put("msg", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            log.error("删除索引失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/index/{indexName}/exists")
    @Operation(summary = "判断索引是否存在")
    public Map<String, Object> existsIndex(@PathVariable String indexName) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean exists = esService.existsIndex(indexName);
            result.put("code", 0);
            result.put("data", exists);
            result.put("msg", exists ? "索引存在" : "索引不存在");
        } catch (Exception e) {
            log.error("查询索引失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // ==================== 文档操作 ====================

    @PostMapping("/document")
    @Operation(summary = "新增文档")
    public Map<String, Object> saveDocument(@RequestBody ProductDocument document) {
        Map<String, Object> result = new HashMap<>();
        try {
            ProductDocument saved = esService.save(DEFAULT_INDEX, document);
            result.put("code", 0);
            result.put("data", saved);
            result.put("msg", "新增成功");
        } catch (Exception e) {
            log.error("新增文档失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/document/{id}")
    @Operation(summary = "根据ID查询文档")
    public Map<String, Object> getDocument(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            ProductDocument doc = esService.getById(DEFAULT_INDEX, id);
            result.put("code", 0);
            result.put("data", doc);
            result.put("msg", doc != null ? "查询成功" : "文档不存在");
        } catch (Exception e) {
            log.error("查询文档失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @PutMapping("/document/{id}")
    @Operation(summary = "更新文档")
    public Map<String, Object> updateDocument(@PathVariable String id, @RequestBody ProductDocument document) {
        Map<String, Object> result = new HashMap<>();
        try {
            ProductDocument updated = esService.update(DEFAULT_INDEX, id, document);
            result.put("code", 0);
            result.put("data", updated);
            result.put("msg", "更新成功");
        } catch (Exception e) {
            log.error("更新文档失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/document/{id}")
    @Operation(summary = "删除文档")
    public Map<String, Object> deleteDocument(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = esService.delete(DEFAULT_INDEX, id);
            result.put("code", success ? 0 : 1);
            result.put("msg", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            log.error("删除文档失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // ==================== 搜索操作 ====================

    @GetMapping("/search/all")
    @Operation(summary = "搜索全部文档")
    public Map<String, Object> searchAll() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ProductDocument> list = esService.searchAll();
            result.put("code", 0);
            result.put("data", list);
            result.put("msg", "查询成功");
        } catch (Exception e) {
            log.error("搜索失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/search")
    @Operation(summary = "关键字搜索（全文搜索）")
    @Parameter(name = "keyword", description = "搜索关键字")
    @Parameter(name = "page", description = "页码（从1开始）")
    @Parameter(name = "size", description = "每页条数")
    public Map<String, Object> searchByKeyword(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ProductDocument> list = esService.searchPage(DEFAULT_INDEX, keyword, page, size);
            result.put("code", 0);
            result.put("data", list);
            result.put("msg", "搜索成功");
        } catch (Exception e) {
            log.error("搜索失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/search/price")
    @Operation(summary = "按价格区间搜索")
    public Map<String, Object> searchByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ProductDocument> list = esService.searchByPriceRange(DEFAULT_INDEX, minPrice, maxPrice);
            result.put("code", 0);
            result.put("data", list);
            result.put("msg", "搜索成功");
        } catch (Exception e) {
            log.error("搜索失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @PostMapping("/document/batch")
    @Operation(summary = "批量插入文档")
    public Map<String, Object> batchInsert(@RequestBody List<ProductDocument> documents) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = esService.bulkInsert(DEFAULT_INDEX, documents);
            result.put("code", 0);
            result.put("data", count);
            result.put("msg", "批量插入成功");
        } catch (Exception e) {
            log.error("批量插入失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

}
