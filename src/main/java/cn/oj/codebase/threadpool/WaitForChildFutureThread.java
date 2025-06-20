package cn.oj.codebase.threadpool;

import cn.hutool.core.thread.ThreadUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author 郑剑锋
 * @Description 线程池等待子线程处理实例 future方式
 * @createTime 2022.01.17 11:16:00
 */
public class WaitForChildFutureThread {

    private static final ExecutorService executorPackageService = ThreadUtil.newExecutor(8, 10, 20);

    public void main(String[] args) {
        List<String> jobs = new ArrayList<>();
        List<Future<Boolean>> futureList = new ArrayList<>();
        jobs.forEach(v -> {
            Future<Boolean> future = executorPackageService.submit(() -> runRule(v));
            futureList.add(future);
        });

        while (futureList.size() > 0) {
            Iterator<Future<Boolean>> iterable = futureList.iterator();
            Future<Boolean> future = iterable.next();
            if (future.isDone() && !future.isCancelled()) {
                try {
                    future.get();
                } catch (Exception e) {

                }
                iterable.remove();
            }
        }
    }

    private boolean runRule(String k) {
        System.out.println(k);
        return true;
    }
}
