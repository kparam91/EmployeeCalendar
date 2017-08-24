/*
   a) GET - <base-url>/timeoff/query
       given an employee id, it will tell you the balance time off hours for the employee

   b) POST <base-url>/timeoff/request
      - Given an employee id and hours requested,  this checks the remaining time off hours
        and returns success if there is enough balance else returns failure

   c) GET <base-url>/timeoff/list
      - Given an employee id, this list all the time-offs (both past, current and future)
 */
package com.karthikParameswaran.employeeCalendar.Controller;

import com.karthikParameswaran.employeeCalendar.Data.EmployeeData;
import com.karthikParameswaran.employeeCalendar.Entity.Employee;
import com.karthikParameswaran.employeeCalendar.Entity.TimeOffRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Employee")
public class HolidayController {
    @Autowired
    private EmployeeData emp;

   @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getAllEmployees(){
        return emp.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Employee e){
        emp.save(e);
    }

    @RequestMapping(value = "/{id}")
    public Employee read(@PathVariable String id){
        return emp.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Employee e){
        emp.save(e);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id){
        emp.delete(id);
    }

    @RequestMapping(value="/timeoff/query/{id}",method = RequestMethod.GET)
    public int timeOffBalance(@PathVariable String id){
        return emp.findById(id).getHolidays_available();
    }
    @RequestMapping(value="/timeoff/request/{id}/{hrs}",method = RequestMethod.POST)
    public HttpStatus timeOffRequest(@PathVariable("id") String id, @PathVariable("hrs") int hrs){
        System.out.println(hrs + id);
        int hoursLeftAfterRequest;

        TimeOffRequest t= new TimeOffRequest();
        t.setHoursRequested(hrs);

        Employee e= emp.findById(id);
        hoursLeftAfterRequest= e.getHolidays_available() - hrs;

        if(hoursLeftAfterRequest>=0){
            e.setHolidays_available(hoursLeftAfterRequest);
            e.setHolidays_used(e.getHolidays_used()+hrs);
            e.addRequestToList(t);
            emp.save(e);
            return HttpStatus.OK;
        }
        else{
            return HttpStatus.BAD_REQUEST;
        }
        //return HttpStatus.OK;
    }
    @RequestMapping(value="/timeoff/list/{id}",method = RequestMethod.GET)
    public List<TimeOffRequest> timeOffList(@PathVariable String id){
        return emp.findById(id).getAllRequests();
    }
}
