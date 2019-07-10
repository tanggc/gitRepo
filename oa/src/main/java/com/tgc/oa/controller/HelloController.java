package com.tgc.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("now",new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
        return "hello";
    }
}
