package cn.cary.codebase.reflect;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郑剑锋
 * @Description 反射实例
 * @createTime 2022.05.26 16:47:00
 */
public class ReflectDemo {
    public static Map<String, Object> getParamList(Object vo) {
        Map<String, Object> params = new HashMap<>();
        Field[] vos = vo.getClass().getDeclaredFields();
        for (Field field : vos) {
            field.setAccessible(true);
            try {
                if (!StringUtils.isEmpty(field.get(vo))) {
                    params.put(field.getName(), field.get(vo));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Class<?> superclass = vo.getClass();
        while (superclass != null) {
            superclass = superclass.getSuperclass();
            if (superclass.getName().equals("java.lang.Object")) {
                break;
            }
            //获取父类的属性
            Field[] superField = superclass.getDeclaredFields();
            for (Field field : superField) {
                field.setAccessible(true);
                try {
                    if (!StringUtils.isEmpty(field.get(vo))) {
                        params.put(field.getName(), field.get(vo));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return params;
    }

}
