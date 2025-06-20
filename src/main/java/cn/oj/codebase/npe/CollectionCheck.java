package cn.oj.codebase.npe;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @program: codebase
 * @description: 集合NPE校验实例
 * @author: 郑剑锋
 * @create: 2021-04-16 09:06
 **/
@Slf4j
public class CollectionCheck {

    //校验Collection接口实现
    private static boolean checkCollection(Collection<?> collection){
        return CollectionUtil.isEmpty(collection);
    }

    private static boolean checkMap(Map map){
        return MapUtil.isEmpty(map);
    }

}
