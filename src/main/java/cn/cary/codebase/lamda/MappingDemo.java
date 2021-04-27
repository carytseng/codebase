package cn.cary.codebase.lamda;

import cn.cary.codebase.lamda.entity.Product;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: codebase
 * @description: mapping例子
 * @author: 郑剑锋
 * @create: 2021-04-14 15:25
 **/
@Slf4j
public class MappingDemo {

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
        log.info("取list每个对象的某个属性组成新的list");
        //写法一
        List<String> proList1 = prodList.stream().collect(Collectors.mapping((product)->{return product.getName();},Collectors.toList()));
        //写法2
        List<String> proList2 = prodList.stream().collect(Collectors.mapping(Product::getName,Collectors.toList()));
        log.info(proList2.toString());
    }

    public static void main(String[] args) {
        groupingByOnePro();
    }
}
