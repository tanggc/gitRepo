package com.tgc.oa.controller;

import com.tgc.oa.entity.Employee;
import com.tgc.oa.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Resource(name = "employee_service")
    private EmployeeService eServie;

    @RequestMapping("/getAllList")
    public String getAllEmployee(Map<String,Object> map){
        map.put("list",eServie.getAllEmployee());
        return "employee_list";
    }

    @RequestMapping("/getEmployee/{sn}")
    public Employee getEmployee(@PathVariable String sn){
        return eServie.getEmployee(sn);
    }

    @RequestMapping("/delEmpBySn/{sn}")
    public void delEmployeeBySn(@PathVariable String sn){
        eServie.delEmployee(sn);
    }


}
