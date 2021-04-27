package cn.cary.codebase.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @program: codebase
 * @description: 线程池创建使用实例
 * @author: 郑剑锋
 * @create: 2021-04-24 13:16
 **/
@Slf4j
public class UseDemo {

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("searchLog-pool-%d").build();

    ExecutorService scheduledThreadPool = new ThreadPoolExecutor(10, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


    public void dosomething(){
        scheduledThreadPool.execute(() -> {

        });
    }

    public static void main(String[] args) {

    }
}
