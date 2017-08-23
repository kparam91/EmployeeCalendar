
package com.karthikParameswaran.employeeCalendar.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;

@Document(collection = "Employees")
public class Employee {
    @Id
    private String id;

    private String name;
    private int holidays_used;
    @Field
    private int holidays_available=100;
    private List<TimeOffRequest> requests;

    @PersistenceConstructor
    public Employee(String id, String name, int holidays_used, List<TimeOffRequest> requests) {
        this.id = id;
        this.name = name;
        this.holidays_used = holidays_used;
        this.requests = requests;
    }



    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHolidays_used() {
        return holidays_used;
    }

    public void setHolidays_used(int holidays_used) {
        this.holidays_used = holidays_used;
    }

    public int getHolidays_available() {
        return holidays_available;
    }

    public void setHolidays_available(int holidays_available) {
        this.holidays_available = holidays_available;
    }
    public List<TimeOffRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<TimeOffRequest> requests) {
        this.requests=requests;
    }
    public void addRequestToList(TimeOffRequest request)
    {
        this.requests.add(request);

    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", holidays_used=" + holidays_used +
                ", holidays_available=" + holidays_available +
                ", requests=" + requests +
                '}';
    }
}

