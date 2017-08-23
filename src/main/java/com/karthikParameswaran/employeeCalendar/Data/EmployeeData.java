package com.karthikParameswaran.employeeCalendar.Data;

import com.karthikParameswaran.employeeCalendar.Entity.Employee;
import com.karthikParameswaran.employeeCalendar.Entity.TimeOffRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeData extends MongoRepository<Employee,String> {

    public Employee findById(String id);
    public List<Employee> findByName(String name);
}

