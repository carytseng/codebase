package cn.oj.codebase.es.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: codebase
 * @description: Elasticsearch 示例文档实体
 * @author: 郑剑锋
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument {

    /**
     * 文档ID
     */
    @JsonProperty("id")
    private Long id;

    /**
     * 商品名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 商品分类
     */
    @JsonProperty("category")
    private String category;

    /**
     * 商品价格
     */
    @JsonProperty("price")
    private Double price;

    /**
     * 库存数量
     */
    @JsonProperty("stock")
    private Integer stock;

    /**
     * 商品描述
     */
    @JsonProperty("description")
    private String description;

    /**
     * 创建时间
     */
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
