package cn.oj.codebase.log;

import org.springframework.util.StopWatch;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName StopWatchDemo.java
 * @Description TODO
 * @createTime 2021年11月16日 16:11:00
 */
public class StopWatchDemo {
    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sw.stop();
        System.out.println(sw.getTotalTimeSeconds());
        System.out.println(sw.prettyPrint());
    }
}
