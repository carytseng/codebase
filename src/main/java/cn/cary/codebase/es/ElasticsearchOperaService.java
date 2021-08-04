package cn.cary.codebase.es;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-28 14:44
 **/
@Slf4j
public class ElasticsearchOperaService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    public String createElasticsearchIndex(String index) throws IOException {
        String esIndex = index;
        JSONObject settings = this.buildSettings();
        JSONObject mappings = this.buildMappings();
        this.createIndex(esIndex, mappings, settings);
        return esIndex;
    }

    private void createIndex(String index, JSONObject mapping, JSONObject settings) throws IOException {
        log.info("正在生成索引");
        CreateIndexRequest request = new CreateIndexRequest(index);

        if (MapUtils.isNotEmpty(mapping)) {
            request.mapping(ESConstant.ES_TYPE, XContentFactory.contentBuilder(XContentType.JSON).map(mapping));
        }
        if (MapUtils.isNotEmpty(settings)) {
            request.settings(settings);
        }

        final CreateIndexResponse response = this.restHighLevelClient.indices().create(request, ESConstant.REQUEST_OPTIONS);

        boolean isSuccess = Objects.nonNull(response) && response.isAcknowledged() && response.isShardsAcknowledged();
        log.info("执行完毕，创建索引状态：{}！", isSuccess ? "成功" : "失败");
    }

    private JSONObject buildSettings() {
        log.info("正在构建settings");
        JSONObject settings = new JSONObject(true);
        settings.put("index.mapping.nested_fields.limit", "2048");
        // mapping总字段限制
        settings.put("index.mapping.total_fields.limit", "200000");
       // mapping字段最大可接受深度
        settings.put("index.mapping.depth.limit", "10000");
        // 分片数
        settings.put("index.number_of_shards", 3);
        // 副本数
        settings.put("index.number_of_replicas", 2);
        // 默认值为1000000
        settings.put("index.highlight.max_analyzed_offset", 9999999);
        // 文档支持最大分页记录数(巨耗内存，推荐scroll)
        settings.put("index.max_result_window", Integer.MAX_VALUE);
        settings.put("index.refresh_interval", "30s");
        return settings;

    }

    private JSONObject buildMappings() {

        log.info("正在构建mappings");
        return null;

    }
}
