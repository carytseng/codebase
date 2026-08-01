package cn.oj.codebase.es;

/**
 * @program: codebase
 * @description: Elasticsearch 常量定义
 * @author: 郑剑锋
 **/
public class ESConstant {

    private ESConstant() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 默认索引名
     */
    public static final String DEFAULT_INDEX = "product_index";

    /**
     * 分页查询默认值
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * 字段名常量
     */
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_STOCK = "stock";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CREATE_TIME = "create_time";

    /**
     * IK 分词器
     */
    public static final String ANALYZER_IK = "ik_max_word";

}
