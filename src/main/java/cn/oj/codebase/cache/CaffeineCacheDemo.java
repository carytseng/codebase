package cn.oj.codebase.cache;

import com.github.benmanes.caffeine.cache.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName CaffeineCacheDemo.java
 * @Description Caffeine使用范例
 * @createTime 2021年08月04日 09:37:00
 */
public class CaffeineCacheDemo {
    /*常见用法*/
    public void normalUse() {
        Cache<String, String> cache = Caffeine.newBuilder()
                //5秒没有读写自动删除
                .expireAfterAccess(5, TimeUnit.SECONDS)
                //最大容量1024个，超过会自动清理空间
                .maximumSize(1024)
                .removalListener(((key, value, cause) -> {
                    //清理通知 key,value ==> 键值对   cause ==> 清理原因
                }))
                .build();

        //添加值
        cache.put("张三", "浙江");
        //获取值
        cache.getIfPresent("张三");
        //remove
        cache.invalidate("张三");
    }

    /*手动填充*/
    public void manual() {
        Cache<String, Integer> cache = Caffeine.newBuilder().build();

        Integer age1 = cache.getIfPresent("张三");
        System.out.println(age1);

        //当key不存在时，会立即创建出对象来返回，age2不会为空
        Integer age2 = cache.get("张三", k -> {
            System.out.println("k:" + k);
            return 18;
        });
        System.out.println(age2);
    }

    /*自动填充*/
    public void loading() {
        //此时的类型是 LoadingCache 不是 Cache
        LoadingCache<String, Integer> cache = Caffeine.newBuilder().build(key -> {
            System.out.println("自动填充:" + key);
            return 18;
        });

        Integer age1 = cache.getIfPresent("张三");
        System.out.println(age1);

        // key 不存在时 会根据给定的CacheLoader自动装载进去
        Integer age2 = cache.get("张三");
        System.out.println(age2);
    }

    /*异步手动*/
    public void asynchronousManual() throws ExecutionException, InterruptedException {
        AsyncCache<String, Integer> cache = Caffeine.newBuilder().buildAsync();

        //会返回一个 future对象， 调用future对象的get方法会一直卡住直到得到返回，和多线程的submit一样
        CompletableFuture<Integer> ageFuture = cache.get("张三", name -> {
            System.out.println("name:" + name);
            return 18;
        });

        Integer age = ageFuture.get();
        System.out.println("age:" + age);
    }

    /*异步自动*/
    public void asynchronouslyLoading() throws ExecutionException, InterruptedException {
        //和1.4基本差不多
        AsyncLoadingCache<String, Integer> cache = Caffeine.newBuilder().buildAsync(name -> {
            System.out.println("name:" + name);
            return 18;
        });
        CompletableFuture<Integer> ageFuture = cache.get("张三");

        Integer age = ageFuture.get();
        System.out.println("age:" + age);
    }

    /*------------------------------------------------------------------------*/

    /*淘汰策略*/

    /*基于容量大小*/
    public void removeStrategyBySize() throws Exception {
        Cache<String, Integer> cache = Caffeine.newBuilder().maximumSize(10)
                .removalListener((key, value, cause) -> {
                    System.out.println(String.format("key %s was removed %s / %s", key, value, cause));
                }).build();

        for (int i = 0; i < 100; i++) {
            cache.put("name" + i, i);
        }
        Thread.currentThread().join();
    }

    /*基于时间*/
    public void removeStrategyByTime() throws Exception {
        //写入后10秒过期,重新写入会刷新过期时间
        Cache<String, Integer> cache1 = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
        //写入或读取后10秒无任务操作会过期，读写都会刷新过期时间
        Cache<String, Integer> cache2 = Caffeine.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS).build();
    }

}
