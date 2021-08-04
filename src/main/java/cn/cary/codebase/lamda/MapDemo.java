package cn.cary.codebase.lamda;

import cn.cary.codebase.lamda.entity.Product;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: codebase
 * @description: map实例
 * @author: 郑剑锋
 * @create: 2021-04-20 15:57
 **/
@Slf4j
public class MapDemo {
    private static List<Product> prodList;

    static {
        Product prod1 = new Product(1L, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2L, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3L, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4L, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5L, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        prodList = Lists.newArrayList(prod1, prod2, prod3, prod4, prod5);
    }

    private static void mapToList() {
        log.info("根据某个属性重新构建新的list");
        List<String> products = prodList.stream().map(p -> p.getName()).collect(Collectors.toList());
        log.info(products.toString());
    }

    private static void mapToNewObjectList() {
        log.info("根据某几个属性重新构建新的对象list");
        List<Map> products = prodList.stream().map(p -> {
            Map obj = new HashMap<>();
            obj.put("oName", p.getName());
            obj.put("oCatagory", p.getCategory());
            return obj;
        }).collect(Collectors.toList());
        log.info(products.toString());
    }

    private static void listToMap() {
        log.info("根据某几个属性重新构建新的对象list");
        Map<Long,Long> products = prodList.stream().collect(Collectors.toMap(Product::getId,Product::getId));
        log.info(products.toString());
    }

    public static void main(String[] args) {
        mapToList();
        mapToNewObjectList();
        listToMap();
    }

}
