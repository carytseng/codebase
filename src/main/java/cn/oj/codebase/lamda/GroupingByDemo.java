package cn.oj.codebase.lamda;

import cn.oj.codebase.lamda.entity.Product;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: codebase
 * @description: lamda分组例子
 * @author: 郑剑锋
 * @create: 2021-04-14 14:57
 **/
@Slf4j
public class GroupingByDemo {

    private static List<Product> prodList;

    static {
        Product prod1 = new Product(1L, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2L, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3L, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4L, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5L, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        prodList = Lists.newArrayList(prod1, prod2, prod3, prod4, prod5);
    }

    private static void groupingByOnePro() {
        log.info("按照某个属性分组");
        Map<String, List<Product>> prodMap = prodList.stream().collect(Collectors.groupingBy(Product::getCategory));
        log.info(prodMap.toString());
    }

    private static void groupingByMutiPro() {
        log.info("按某几个属性拼接分组");
        Map<String, List<Product>> prodMap = prodList.stream().collect(Collectors.groupingBy(item -> item.getCategory() + "_" + item.getName()));
        log.info(prodMap.toString());
    }

    private static void groupingByCondition() {
        log.info("根据条件分组");
        Map<String, List<Product>> prodMap = prodList.stream().collect(Collectors.groupingBy(item -> {
            if (item.getNum() < 3) {
                return "3";
            } else {
                return "other";
            }
        }));
        log.info(prodMap.toString());
    }

    private static void groupingByCount() {
        log.info("统计各分组的数量总数");
        Map<String, Long> prodMap = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        log.info(prodMap.toString());
    }

    private static void groupingByProSum() {
        log.info("统计各分组的某个属性和");
        Map<String, Integer> prodMap = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingInt(Product::getNum)));
        log.info(prodMap.toString());
    }

    private static void groupingByProToNew() {
        log.info("按属性分组并将分组中的list按照某一属性组成新的容器");
        Map<String, Set<String>> prodMap = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getName, Collectors.toSet())));
        log.info(prodMap.toString());
    }


    public static void main(String[] args) {
        groupingByOnePro();
        groupingByMutiPro();
        groupingByCondition();
        groupingByCount();
        groupingByProSum();
        groupingByProToNew();
    }

}
