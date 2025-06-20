package cn.oj.codebase.es;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description es工具类封装
 * @author 郑剑锋
 * @updateTime 2022/3/18 08:57
 */
@Component
public class EsUtil {

    private static Logger log = LoggerFactory.getLogger(EsUtil.class);

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private final String STATUS = "OK";

    /**
     * 创建索引
     *
     * @param indexs 索引名称
     * @return true 创建成功,false创建失败
     */
    public boolean createIndex(String... indexs) throws Exception {
        for (String index : indexs) {
            GetIndexRequest request = new GetIndexRequest();
            request.indices(index);
            if (restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT)) {
                log.info("index: {} exists", index);
                return true;
            }else{
                //创建索引请求
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
                //设置mapping映射-实现字段分词,暂时没有装ik，暂时先注释
                //创建执行请求
                CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            }
        }
        return true;
    }

    /**
     * 创建索引
     *
     * @param index 索引名称
     * @param obj   对象
     * @return true 创建成功,false创建失败
     */
    public boolean createIndex(String index, Object obj) throws Exception {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        if (restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT)) {
            log.info("索引:{}已存在", index);
            return true;
        }
        //创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        //设置mapping映射-实现字段分词,暂时没有装ik，暂时先注释
        XContentBuilder builder = XContentFactory.jsonBuilder();

        Map<String, Object> mappObj = getObjectToMap(obj);
        builder.startObject();
        //properties固定
        builder.startObject("properties");
        {
            //设置字段
            for (String key : mappObj.keySet()) {
                builder.startObject(key);
                {
                    //设置字段类型
                    builder.field("type", "text") //text:分词  keyword:不分词 都是字符串
                            //是否索引
                            .field("index", "true")
                            //插入时分词ik_smart
                            //分词器选择：0-not_analyzed 1-ik_smart 2-ik_max_word 3-ik-index(自定义分词器)
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_smart")
                            .startObject("fields")
                            .startObject("keyword")
                            .field("type", "keyword")
                            .endObject()
                            .endObject();
                }
                builder.endObject();
            }

        }
        builder.endObject();
        builder.endObject();
        //设置mapping映射
        createIndexRequest.mapping("doc", builder);

        //创建执行请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return true;
    }

    /**
     * 查看索引是否存在
     *
     * @return true存在 false不存在
     * @throws IOException es连接异常
     */
    private boolean getIndex(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);
        return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @return true删除成功 false删除失败或索引不存在
     */
    public boolean deleteIndex(String index) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        try {
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existMapping(String index){
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.indices(index);
        GetMappingsResponse getMappingResponse;
        try {
            getMappingResponse = restHighLevelClient.indices().getMapping(getMappingsRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return false;
        }
        ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = getMappingResponse.mappings();
        for(ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>> indexEntry : mappings){
            if(indexEntry.value.isEmpty() ){
                return false;
            }
        }
        return true;
    }

    /**
     * 插入数据
     *
     * @param index 索引名称
     * @param data  数据 可以为单个对象也可以为数组
     * @return true 插入成功 false插入失败
     * @throws IOException es连接异常
     */
    public boolean insertBathData(String index, JSONArray data) throws Exception {
        //判断索引是否存在
        boolean flag = getIndex(index);
        if (!flag) {
            createIndex(index);
        }
        if (CollectionUtil.isEmpty(data)) {
            return true;
        }
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("15s");
        for (int i = 0; i < data.size(); i++) {
            JSONObject m = (JSONObject) data.get(i);
            String id = (String) m.get("id");
            bulkRequest.add(new IndexRequest(index).id(id).source(JSON.toJSONString(data.get(i)), XContentType.JSON).type("doc"));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        //是否失败 bulk.hasFailures()=false 代表成功
        return !bulk.hasFailures();
    }

    /**
     * 插入数据
     *
     * @param index 索引名称
     * @param data  数据 可以为单个对象也可以为数组
     * @return true 插入成功 false插入失败
     * @throws IOException es连接异常
     */
    public boolean insertData(String index, Object data) throws Exception {
        //判断索引是否存在
        boolean flag = getIndex(index);
        if (!flag) {
            createIndex(index);
        }
        IndexRequest indexRequest = new IndexRequest(index);

        indexRequest.timeout("1s");      // 设置超时时间

        // 4. 将数据放入到请求中
        indexRequest.source(JSON.toJSONString(data), XContentType.JSON).type("doc");
        Map<String, Object> obj = getObjectToMap(data);
        String id = (String) obj.get("id");
        if (StrUtil.isNotBlank(id)) {
            indexRequest.id(id);
        }
        // 5. 使用客户端发送请求
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        //是否失败 bulk.hasFailures()=false 代表成功
        return STATUS.equals(response.status().toString());
    }

    /**
     * 修改数据
     *
     * @param index 索引
     * @param id    esId
     * @param data  修改后的数据
     * @return true修改成功 false修改失败
     * @throws IOException es连接异常
     */
    public boolean updateData(String index, String id, Object data) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, "doc", id);
        updateRequest.timeout("1s");
        UpdateRequest doc = updateRequest.doc(JSON.toJSONString(data), XContentType.JSON);

        //XContentBuilder builder = XContentFactory.jsonBuilder();

        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

       /* //测试批量
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(index, id);
        //updateByQueryRequest.setDocTypes(JSON.toJSONString(data));

        BulkB yScrollResponse bulkByScrollResponse = restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);*/

        return STATUS.equals(update.status().toString());
    }

    /**
     * 删除数据(可批量删除，可单条删除)
     *
     * @param index 索引
     * @param id    esId
     * @return true删除成功 false删除失败
     * @throws IOException es连接异常
     */
    public boolean deleteData(String index, String... id) throws IOException {

        BulkRequest request = new BulkRequest();
        for (String s : id) {
            DeleteRequest deleteRequest = new DeleteRequest(index, "doc", s);
            deleteRequest.timeout("1s");
            request.add(deleteRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        //DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return STATUS.equals(bulk.status().toString());
    }

    /**
     * 根据分页查询数据 查询全部
     *
     * @param index 索引名称
     * @param form  页数 如果查看第一页传1
     * @param size  数量 默认传10
     * @return 查询好的SearchHit数组
     * @throws IOException es连接异常
     */
    public SearchHits queryPage(String index, Integer form, Integer size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //QueryBuilders.matchAllQuery查询所有
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(matchAllQueryBuilder);
        //构建分页
        if (form == 1) {
            searchSourceBuilder.from();
        } else {
            searchSourceBuilder.from(form);
        }
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search.getHits();
    }

    /**
     *  查询全部
     *
     * @param index      索引名称
     * @return 查询好的SearchHit数组
     * @throws IOException es连接异常
     */
    public List<Map<String, Object>> queryAllData(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建分页
        searchSourceBuilder.from();
        searchSourceBuilder.size(10000);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String, Object>> resultMap = toList(search.getHits());
        return resultMap;
    }

    /**
     * 分词查找
     *
     * @param index 索引名称
     * @param form  页数 如果查看第一页传1
     * @param size  数量 默认传10
     * @param name  查询参数名
     * @param value 查询值
     * @return 查询好的SearchHit数组
     * @throws IOException es连接异常
     */
    public SearchHit[] boolPage(String index, Integer form, Integer size, String name, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //QueryBuilders.termQuery(); //精确查找
        /*TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
        searchSourceBuilder.query(termQueryBuilder);*/

        //使用bool检索
        /*bool:几种格式
        must:所有分句都必须匹配，与 AND 相同。
        must_not:所有分句都必须不匹配，与 NOT 相同。
        should: 至少有一个分句匹配，与 OR 相同。
        1 term查询（精准查询）
        2 math查询（分词匹配查询）
        3 fuzzy查询（模糊查询）
        4 wildcard(通配符查询)
        5 bool查询（布尔查询）
        */
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery(name, value)
                        .analyzer("ik_max_word")
                        .operator(Operator.OR));
        searchSourceBuilder.query(boolQueryBuilder);

        /**
         * math查询（分词匹配查询），只要与其中一个关键字匹配就可以查出来
         */
       /* MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(name, value);
        searchSourceBuilder.query(matchQueryBuilder);*/

        // math查询（分词匹配查询）可以支持多个字段检索
        /*QueryBuilder multiMatch = QueryBuilders.multiMatchQuery("zhou", "first_name","last_name");
        searchSourceBuilder.query(multiMatch);*/

        // math查询（分词匹配查询）一定要包含全部关键字
        /*MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("about", "in this world");
        searchSourceBuilder.query(matchPhraseQueryBuilder);*/

        //构建分页
        if (form == 1) {
            searchSourceBuilder.from();
        } else {
            searchSourceBuilder.from(form);
        }
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search.getHits().getHits();
    }

    /**
     * 精确查找
     *
     * @param index 索引名称
     * @param form  页数 如果查看第一页传1
     * @param size  数量 默认传10
     * @param name  查询参数名
     * @param value 查询值
     * @return 查询好的SearchHit数组
     * @throws IOException es连接异常
     */
    public SearchHit[] queryPage(String index, Integer form, Integer size, String name, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //QueryBuilders.termQuery(); //精确查找
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
        searchSourceBuilder.query(termQueryBuilder);

        //使用bool检索
        /*bool:几种格式
        must:所有分句都必须匹配，与 AND 相同。
        must_not:所有分句都必须不匹配，与 NOT 相同。
        should: 至少有一个分句匹配，与 OR 相同。
        1 term查询（精准查询）
        2 math查询（分词匹配查询）
        3 fuzzy查询（模糊查询）
        4 wildcard(通配符查询)
        5 bool查询（布尔查询）
        */
        /*BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery(name,value)
                        .analyzer("ik_max_word")
                        .operator(Operator.OR));
        searchSourceBuilder.query(boolQueryBuilder);*/


        /**
         * math查询（分词匹配查询），只要与其中一个关键字匹配就可以查出来
         */
        /*MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(name, value);
        searchSourceBuilder.query(matchQueryBuilder);*/

        // math查询（分词匹配查询）可以支持多个字段检索
        /*QueryBuilder multiMatch = QueryBuilders.multiMatchQuery("zhou", "first_name","last_name");
        searchSourceBuilder.query(multiMatch);*/

        // math查询（分词匹配查询）一定要包含全部关键字
        /*MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("about", "in this world");
        searchSourceBuilder.query(matchPhraseQueryBuilder);*/

        //构建分页
        if (form == 1) {
            searchSourceBuilder.from();
        } else {
            searchSourceBuilder.from(form);
        }
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search.getHits().getHits();
    }

    /**
     * 转换格式
     *
     * @param searchHits
     * @return
     */
    public List<Map<String, Object>> toList(SearchHits searchHits) {

        List<Map<String, Object>> sourceAsMap = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> orginMap = searchHit.getSourceAsMap();
            Map<String, Object> newMap = new HashMap<>();
            //sourceAsMap1.put("esId", searchHit.getId());
            //sourceAsMap1.put("index", searchHit.getIndex());
            // key是个jsonObject则把对象打平，处理矩阵数据
            for (String parentKey : orginMap.keySet()) {
                Object obj = orginMap.get(parentKey);
                if (obj instanceof Map) {
                    Map<String, Object> childMap = (Map) obj;
                    for (String childKey : childMap.keySet()) {
                        String newKey = parentKey + "." + childKey;
                        Object childValue = childMap.get(childKey);
                        if (childValue instanceof List) {
                            newMap.put(newKey, JSON.toJSONString(childMap.get(childKey)));
                        } else {
                            newMap.put(newKey, childMap.get(childKey));
                        }
                    }
                } else if (obj instanceof List) {
                    newMap.put(parentKey, JSON.toJSONString(obj));
                } else {
                    newMap.put(parentKey, orginMap.get(parentKey));
                }
            }
            sourceAsMap.add(newMap);
        }
        return sourceAsMap;
    }

    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }

}

