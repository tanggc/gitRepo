package com.tgc.oa.service;

import com.tgc.oa.dao.EmployeeDao;
import com.tgc.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "employee_service")
public class EmployeeService {
    @Resource(name = "employee_dao")
    private EmployeeDao eDao;

    public Employee getEmployee(String sn){
        return eDao.Sel(sn);
    }

    public List<Employee> getAllEmployee(){
        return eDao.SelAll();
    }

    public void delEmployee(String sn){
        eDao.Del(sn);
    }
    public void addEmployee(Employee employee){
        eDao.Add(employee);
    }

    public void updateEmployee(Employee employee){
        eDao.Update(employee);
    }
}
