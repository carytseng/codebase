package cn.cary.codebase.lamda;

import cn.cary.codebase.lamda.entity.Product;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: codebase
 * @description: filter实例
 * @author: 郑剑锋
 * @create: 2021-04-20 15:41
 **/
@Slf4j
public class FilterDemo {
    private static List<Product> prodList;

    static {
        Product prod1 = new Product(1L, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2L, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3L, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4L, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5L, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        prodList = Lists.newArrayList(prod1, prod2, prod3, prod4, prod5);
    }

    private static void filter() {
        log.info("根据条件过滤");
        List products=prodList.stream().filter(v->v.getNum().equals(10)).collect(Collectors.toList());
        log.info(products.toString());
    }

    private static void filterByCondition() {
        log.info("根据入参条件过滤");
        List products=prodList.stream().filter(v->{
            if(v.getNum().equals(10)){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        log.info(products.toString());
    }

    public static void main(String[] args) {
        filter();
    }
}
