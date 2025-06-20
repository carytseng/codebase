package cn.oj.codebase.lamda.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-14 15:26
 **/
@Data
public class Product {
    Long id;
    Integer num;
    BigDecimal price;
    String name;
    String category;

    public Product(Long id, Integer num, BigDecimal price, String name, String category) {
        this.id = id;
        this.num = num;
        this.price = price;
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", num=" + num +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
