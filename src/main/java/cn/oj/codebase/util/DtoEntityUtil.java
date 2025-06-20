package cn.oj.codebase.util;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName DtoEntityUtil.java
 * @Description TODO
 * @createTime 2021年08月06日 09:54:00
 */
public class DtoEntityUtil {
    public DtoEntityUtil() {
    }

    public static Object populate(Object src, Object target) {
        if (src == null) {
            return null;
        } else {
            Method[] srcMethods = src.getClass().getMethods();
            Method[] targetMethods = target.getClass().getMethods();
            Method[] var4 = srcMethods;
            int var5 = srcMethods.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method m = var4[var6];
                String srcName = m.getName();
                if (srcName.startsWith("get")) {
                    try {
                        Object result = m.invoke(src);
                        Method[] var10 = targetMethods;
                        int var11 = targetMethods.length;

                        for(int var12 = 0; var12 < var11; ++var12) {
                            Method mm = var10[var12];
                            String targetName = mm.getName();
                            if (targetName.startsWith("set") && targetName.substring(3, targetName.length()).equals(srcName.substring(3, srcName.length()))) {
                                mm.invoke(target, result);
                            }
                        }
                    } catch (Exception var15) {
                    }
                }
            }

            return target;
        }
    }

    public static <S, T> List<T> populateList(List<S> src, List<T> target, Class<?> targetClass) {
        if (src != null && src.size() > 0) {
            for(int i = 0; i < src.size(); ++i) {
                try {
                    Object object = targetClass.newInstance();
                    target.add((T) object);
                    populate(src.get(i), object);
                } catch (Exception var5) {
                }
            }

            return target;
        } else {
            return null;
        }
    }
}
