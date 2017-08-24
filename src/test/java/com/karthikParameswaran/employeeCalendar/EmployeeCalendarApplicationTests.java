package com.karthikParameswaran.employeeCalendar;

import com.karthikParameswaran.employeeCalendar.Data.EmployeeData;
import com.karthikParameswaran.employeeCalendar.Entity.Employee;
import com.karthikParameswaran.employeeCalendar.Entity.TimeOffRequest;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EmployeeCalendarApplicationTests {




    @Autowired
    private EmployeeData testData;
    private List<Employee> el= new ArrayList<Employee>();
    @Before
    public void generateTestData()
    {
        List<TimeOffRequest> requests= new ArrayList<TimeOffRequest>();
        Employee e1 = new Employee("T01", "KP",0,100,requests);
        Employee e2 = new Employee("T02", "PK",0,100,requests);
        Employee e3 = new Employee("T03", "KPK",0,100,requests);

        el.add(e1);
        el.add(e2);
        el.add(e3);
        testData.insert(el);
        this.restTemplate.postForObject("/Employee/timeoff/request/T01/5",null, HttpStatus.class);
    }
    @Test
	public void contextLoads() {

        Employee found = testData.findById("T01");
        assertThat(found.getName()).isEqualTo("KP");
	}

    @Autowired
    private TestRestTemplate restTemplate;

    /******************************
     * Testing basic CRUD endpoints*
     ******************************/
    @Test
    public void getEmployeeTest() {
        String endpoint = "/Employee";
        String actualResponse;
        String expectedResponse= "[" +
                "{" +
                "\"id\":\"T01\"," +
                "\"name\":\"KP\"," +
                "\"holidays_used\":5," +
                "\"holidays_available\":95," +
                "\"allRequests\":[{\"hoursRequested\":5}]" +
                "}," +
                "{" +
                "\"id\":\"T02\"," +
                "\"name\":\"PK\"," +
                "\"holidays_used\":0," +
                "\"holidays_available\":100," +
                "\"allRequests\":[]" +
                "}," +"{" +
                "\"id\":\"T03\"," +
                "\"name\":\"KPK\"," +
                "\"holidays_used\":0," +
                "\"holidays_available\":100," +
                "\"allRequests\":[]" +
                "}" +
                "]";
        actualResponse= this.restTemplate.getForObject(endpoint, String.class);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
    @Test
    public void deleteEmployeeTest() {
        String sendpoint = "/Employee/T03";
        URI endpoint = URI.create(sendpoint);
        String actualResponse=null;
        String expectedResponse= null;
        this.restTemplate.delete(endpoint);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void postEmployeeTest() {

        List<TimeOffRequest> requests= new ArrayList<TimeOffRequest>();
        Employee b = new Employee("T04", "PK",0,100,requests);
        //void method must return null
        String endpoint = "/Employee";
        String actualResponse;
        String expectedResponse= null;
        actualResponse=this.restTemplate.postForObject("/Employee",b, String.class);
        Employee a =testData.findById("T04");

        assertThat(a.getName() ).isEqualTo(b.getName());
        testData.delete("T04");
    }
    @Test
    public void getEmployeeByIdTest() {
        String endpoint = "/Employee/T02";
        String actualResponse;
        String expectedResponse= "{" +
                "\"id\":\"T02\"," +
                "\"name\":\"PK\"," +
                "\"holidays_used\":0," +
                "\"holidays_available\":100," +
                "\"allRequests\":[]" +
                "}";

        actualResponse = this.restTemplate.getForObject(endpoint, String.class);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    /******************************
     * Testing timeOff endpoints*
     ******************************/
    @Test
    public void queryTimeoffTest() {
        String endpoint = "/Employee/timeoff/query/T02";
        String actualResponse;
        String expectedResponse="100";

        actualResponse= this.restTemplate.getForObject(endpoint, String.class);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void postTimeOffRequestPositiveTest() {
        String endpoint = "/Employee/timeoff/request/T01/5";
        HttpStatus actualResponse;
        HttpStatus expectedResponse=HttpStatus.OK;

        actualResponse= this.restTemplate.postForObject(endpoint,null, HttpStatus.class);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void postTimeOffRequestNegativeTest() {
        String endpoint = "/Employee/timeoff/request/T01/105";
        HttpStatus actualResponse;
        HttpStatus expectedResponse=HttpStatus.BAD_REQUEST;

        actualResponse= this.restTemplate.postForObject(endpoint,null, HttpStatus.class);
        assertThat(actualResponse).isEqualTo(expectedResponse);

    }
    @Test
    public void getListTimeOff() {
        String endpoint = "/Employee/timeoff/list/T01";
        String actualResponse;
        String expectedResponse="[{" +
                "\"hoursRequested\":5" +
                "}" +
                "]";

        actualResponse= this.restTemplate.getForObject(endpoint, String.class);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
    @After
    public void destroyData(){
        testData.delete(el);
    }
}
