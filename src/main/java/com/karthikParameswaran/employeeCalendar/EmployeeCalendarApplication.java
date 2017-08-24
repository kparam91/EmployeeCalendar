package com.karthikParameswaran.employeeCalendar;

import com.karthikParameswaran.employeeCalendar.Data.EmployeeData;
import com.karthikParameswaran.employeeCalendar.Entity.Employee;
import com.karthikParameswaran.employeeCalendar.Entity.TimeOffRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EmployeeCalendarApplication implements CommandLineRunner{

	@Autowired
    public EmployeeData empData;
	public static void main(String[] args) {
		SpringApplication.run(EmployeeCalendarApplication.class, args);
	}
    @Override
    public void run(String... args) throws Exception {
        /***************************************
         * Adding dummy data to db on initialize
         ****************************************/
	    empData.deleteAll();
        List<TimeOffRequest> requests= new ArrayList<TimeOffRequest>();
        Employee e1 = new Employee("E01", "KP",0,100,requests);
        Employee e2 = new Employee("E02", "PK",0,100,requests);
        Employee e3 = new Employee("E03", "KPK",0,100,requests);
        List<Employee> el= new ArrayList<Employee>();
        el.add(e1);
        el.add(e2);
        el.add(e3);
        empData.insert(el);
    }

}
