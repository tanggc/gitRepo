package com.tgc.oa.controller;

import com.tgc.oa.service.OrderThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Tgc
 * @date: 2019/8/14
 */
@Controller
@RequestMapping("/thread")
public class ThreadController {
    @Autowired
    private OrderThreadService orderThreadService;

    @RequestMapping("/send")
    public void showThread(){
        orderThreadService.orderTask();
    }
}
