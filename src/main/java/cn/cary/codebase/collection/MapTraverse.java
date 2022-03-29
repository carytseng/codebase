package cn.cary.codebase.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Kira
 * @Description:
 * @Date:Create：in 2020/6/15 10:33 下午
 */
@Slf4j
public class MapTraverse {

    public static void main(String[] args) {
        Map<String,Object> m = new HashMap();
        m.put("t1",1);
        m.put("t2",2);
        m.put("t3",3);
        m.put("t4",4);

        log.info("---------------------------------------------------");
        //keySet forEach循环
        for(Object o : m.keySet()){
            log.info("key:{},value:{}",o,m.get(o));
        }

        log.info("---------------------------------------------------");
        //entrySet forEach循环
        for(Map.Entry<String,Object> entry:m.entrySet()){
            log.info("key:{},value:{}",entry.getKey(),entry.getValue());
        }

        log.info("---------------------------------------------------");
        //keySet 迭代器循环
        Iterator it=m.keySet().iterator();
        while(it.hasNext()){
            Object key = it.next();
            log.info("key:{},value:{}",key,m.get(key));
        }

        log.info("---------------------------------------------------");
        //entrySet 迭代器循环
        Iterator<Map.Entry<String,Object>> itt=m.entrySet().iterator();
        while(itt.hasNext()){
            Map.Entry<String,Object> entry = itt.next();
            log.info("key:{},value:{}",entry.getKey(),entry.getValue());
        }

        log.info("---------------------------------------------------");


    }

}

