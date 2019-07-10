package com.tgc.oa.controller;

import com.tgc.oa.entity.Employee;
import com.tgc.oa.service.GloablService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class GloablController {
    @Resource(name = "gloablService")
    private GloablService gloablService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String toLogin(HttpSession session, @RequestParam String sn,@RequestParam String password){
        Employee employee = gloablService.checkLogin(sn,password);
        if(employee == null){
            return "redirect:to_login";
        }
        session.setAttribute("employee",employee);
        return "redirect:self";
    }

    @RequestMapping("/self")
    public String self(){
        return "self";
    }

    @RequestMapping("/quit")
    public String Logout(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old,@RequestParam String new1,@RequestParam String new2){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee.getPassword().equals(old)){
            if(new1.equals(new2)){
                employee.setPassword(new1);
                gloablService.changePassword(employee);
                return "redirect:self";
            }
        }

        return "redirect:to_change_password";
    }
}
