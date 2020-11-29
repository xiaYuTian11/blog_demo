package com.sunnyday.snowflake;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TMW
 * @date 2020/10/16 10:36
 */
class SnowFlakeBaseTest {

    private SnowFlakeBase snowFlakeBase;

    @BeforeEach
    void setUp() {
        snowFlakeBase = new SnowFlakeBase(1, 10);
    }

    @Test
    void nextId() {
        final ExecutorService threadPool = Executors.newFixedThreadPool(240);
        int threadSize = 100;
        int IdSize = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        // List<Long> list = new Vector<>();
        List<Long> list = Collections.synchronizedList(new ArrayList<>());
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                try {
                    System.out.println(finalI + " ---等待执行");
                    countDownLatch.await();
                    for (int i1 = 0; i1 < IdSize; i1++) {
                        list.add(snowFlakeBase.nextId());
                    }
                    System.out.println(finalI + " ---执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
        // int size = list.size();
        while (list.size() != threadSize * IdSize) {
            // size = list.size();
        }
        System.out.printf("snowflake is valid： %b", new HashSet<>(list).size() == list.size());
        final long elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        System.out.println("\r\n耗时：" + elapsed);
        threadPool.shutdown();
    }
}