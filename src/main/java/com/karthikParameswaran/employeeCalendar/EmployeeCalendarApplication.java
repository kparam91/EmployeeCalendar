package com.karthikParameswaran.employeeCalendar;

import com.karthikParameswaran.employeeCalendar.Data.EmployeeData;
import com.karthikParameswaran.employeeCalendar.Entity.Employee;
import com.karthikParameswaran.employeeCalendar.Entity.TimeOffRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

public class EmployeeCalendarApplication{

	@Autowired
    public EmployeeData empData;
	public static void main(String[] args) {
		SpringApplication.run(EmployeeCalendarApplication.class, args);
	}

}
