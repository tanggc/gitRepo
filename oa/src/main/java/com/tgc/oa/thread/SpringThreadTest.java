package com.tgc.oa.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tgc
 * @date: 2019/8/14
 */
public class SpringThreadTest {

    /*@Autowired
    public static ThreadPoolTaskExecutor tpte =  new ThreadPoolTaskExecutor();

    //最大线程数
    tpte.setMaxPoolSize(4);
    //队列容量
    tpte.setQueueCapacity(6);
    //活跃时间
    tpte.setKeepAliveSeconds(300);
    //线程名字前缀
    tpte.setThreadNamePrefix("MyExecutor-");


    public static void testThread() {
        tpte.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前的线程：" + Thread.currentThread().getName());
            }
        });

    }

    public static void main(String[] args) {
        testThread();
    }*/

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 200,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(500));
        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.out.println("当前的线程：" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }


}
