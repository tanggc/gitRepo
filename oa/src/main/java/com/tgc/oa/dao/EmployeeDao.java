package com.tgc.oa.dao;

import com.tgc.oa.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工Dao
 */

@Repository(value = "employee_dao")
public interface EmployeeDao {
     Employee Sel(String sn);
     List<Employee> SelAll();
     void Del(String sn);
     void Add(Employee employee);
     void Update(Employee employee);
}
