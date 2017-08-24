package com.karthikParameswaran.employeeCalendar.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class TimeOffRequest {

    private int hoursRequested;


    public int getHoursRequested() {
        return hoursRequested;
    }

    public void setHoursRequested(int hoursRequested) {
        this.hoursRequested = hoursRequested;
    }
}
