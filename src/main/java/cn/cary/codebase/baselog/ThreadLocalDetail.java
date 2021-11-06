package cn.cary.codebase.baselog;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName ThreadLocalDetail.java
 * @Description TODO
 * @createTime 2021年09月13日 11:19:00
 */
public class ThreadLocalDetail {

    private ThreadLocal threadLocal = new ThreadLocal();

    public void run(){
        threadLocal.set("modto set into..");
        /**
         * ThreadLocal中有个map，该map是被当前线程持有，因此是物理隔离的。
         * set方法首先获取thread中的map，再已当前threadLocal作为变量key去set值
         * 原理上就是每条线程都有一个独立的threadLocalMap来存储不同的threadLocal变量
         */
    }

}
