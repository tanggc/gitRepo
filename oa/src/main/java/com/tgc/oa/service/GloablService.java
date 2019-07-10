package com.tgc.oa.service;

import com.tgc.oa.dao.EmployeeDao;
import com.tgc.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "gloablService")
public class GloablService {
    @Resource(name = "employee_dao")
    private EmployeeDao employeeDao;
    public Employee checkLogin(String sn, String password){
        Employee employee = employeeDao.Sel(sn);
        if(employee != null && employee.getPassword().equals(password)){
            return employee;
        }

        return null;
    }

    public void changePassword(Employee employee){
        employeeDao.Update(employee);
    }
}
