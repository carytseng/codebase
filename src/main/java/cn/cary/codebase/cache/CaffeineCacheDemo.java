package cn.cary.codebase.cache;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName CaffeineCacheDemo.java
 * @Description TODO
 * @createTime 2021年08月04日 09:37:00
 */
public class CaffeineCacheDemo {

    public static void main(String[] args) {
        /*Cache<Key, MyObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build();

// 查找一个缓存元素， 没有查找到的时候返回null
        MyObject graph = cache.getIfPresent(key);
// 查找缓存，如果缓存不存在则生成缓存元素,  如果无法生成则返回null
        graph = cache.get(key, k -> createObject(key));
// 添加或者更新一个缓存元素
        cache.put(key, graph);
// 移除一个缓存元素
        cache.invalidate(key);*/
    }
}
