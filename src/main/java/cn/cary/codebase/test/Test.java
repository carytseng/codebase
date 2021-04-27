package cn.cary.codebase.test;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-14 16:32
 **/
@Slf4j
public class Test {
    public static void main(String[] args) {
        Item a1 = new Item(0, "检验明细@#检验项目名称");
        Item a2 = new Item(1, "检验明细@#检验项目名称_2");
        Item a3 = new Item(2, "检验明细@#检验项目名称_3");
        Item a4 = new Item(3, "检验明细@#检验明细项目名称");
        Item a5 = new Item(4, "检验明细@#检验明细项目名称_2");
        Item a6 = new Item(5, "检验明细@#检验明细项目名称_3");
        Item a7 = new Item(6, "检验明细@#检验结果");
        Item a8 = new Item(7, "检验明细@#检验结果_2");
        Item a9 = new Item(8, "检验明细@#检验结果_3");
        List<Item> list = Lists.newArrayList(a1, a2, a3, a4, a5, a6, a7, a8, a9);

        list = list.stream().sorted((x, y) -> {
            String[] col1 = x.getTitle().split("_");
            String[] col2 = y.getTitle().split("_");
            int t1, t2;
            t1 = col1.length > 1 ? Integer.parseInt(col1[1]) : 0;
            t2 = col2.length > 1 ? Integer.parseInt(col2[1]) : 0;
            return t1 < t2 ? -1 : t1 >= t2 ? 1 : 0;
        }).collect(Collectors.toList());
        log.info(list.toString());

        List<String> rowData = Lists.newArrayList("1", "2", "3", "11", "22", "33");

        //Map<String, List<String>> m = list.stream().collect(Collectors.groupingBy(v -> v.split("@#")[0] + (v.split("[_]").length > 1 ? "_" + v.split("[_]")[1] : "")));
      /*  Map<String, List<Item>> m = list.stream().collect(Collectors.groupingBy(v -> {
            return v.getTitle().split("@#")[0] + (v.getTitle().split("[_]").length > 1 ? "_" + v.getTitle().split("[_]")[1] : "");
        }));
        Map<String, List<Item>> map = new TreeMap<String, List<Item>>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        m.forEach((k, v) -> map.put(k, v));
        log.info(map.toString());
        List<Item> nList = new ArrayList<>();
        map.forEach((k, v) -> {
            v.stream().forEach(f -> {
                nList.add(f);
            });
        });
        for (Item item : nList) {
            item.setNewIndex(nList.indexOf(item));
        }
        List<String> rowDataCp = new ArrayList<>();
        nList.stream().forEach(v -> {
            rowDataCp.add(rowData.get(v.getOldIndex()));
        });
        log.info(rowData.toString());
        log.info(rowDataCp.toString());*/
    }

}
