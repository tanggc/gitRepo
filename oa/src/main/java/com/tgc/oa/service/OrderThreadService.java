package com.tgc.oa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author: Tgc
 * @date: 2019/8/14
 */
@Service
public class OrderThreadService {
    private Logger log = LoggerFactory.getLogger(OrderThreadService.class);

    @Autowired
    private ThreadService  threadService;

    public void orderTask(){
        log.error("线程开始执行-------");
        threadService.sendMessage1();
        threadService.sendMessage2();
        log.error("线程执行结束-------");
    }
}
