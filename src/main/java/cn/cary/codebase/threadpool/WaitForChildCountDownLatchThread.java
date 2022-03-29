package cn.cary.codebase.threadpool;

import cn.hutool.core.thread.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author 郑剑锋
 * @Description 线程池等待子线程处理实例 CountDownLatch方式
 * @createTime 2022.01.17 11:16:00
 */
public class WaitForChildCountDownLatchThread {

    private static final ExecutorService executorPackageService = ThreadUtil.newExecutor(8, 10, 20);

    public void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);
        List<String> jobs = new ArrayList<>();
        jobs.forEach(v -> executorPackageService.submit(() -> runRule(v, latch)));
        latch.await();
    }

    private boolean runRule(String k, CountDownLatch latch) {
        System.out.println(k);
        latch.countDown();
        return true;
    }

}
