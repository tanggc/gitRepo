package com.tgc.oa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: Tgc
 * @date: 2019/8/14
 */
@Service
public class ThreadService {
    Logger log = LoggerFactory.getLogger(ThreadService.class);

    @Async("asyncServiceExecutor")
    public void sendMessage1(){
        log.info("发送短信方法---- 1   执行开始");
        try {
            Thread.sleep(5000); // 模拟耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送短信方法---- 1   执行结束");
    }

    @Async("asyncServiceExecutor")
    public void sendMessage2(){
        log.info("发送短信方法---- 2   执行开始");
        try {
            Thread.sleep(5000); // 模拟耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送短信方法---- 2   执行结束");
    }

}
