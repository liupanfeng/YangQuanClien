package com.meishe.yangquan.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllThreadPools {
    /**
     * 有序的
     *
     * @param runnable
     */
    public static void singleThread(Runnable runnable) {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.execute(runnable);
    }

    /**
     * 无序的
     *
     * @param runnable
     */
    public static void fixedThread(Runnable runnable) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(getCPUNum() + 1);
        fixedThreadPool.execute(runnable);
    }

    public static int getCPUNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 自适应线程池
     *
     * @param runnable
     */
    public static void cacheThread(Runnable runnable) {
        ExecutorService cacheThread = Executors.newCachedThreadPool();
        cacheThread.execute(runnable);
    }
}
