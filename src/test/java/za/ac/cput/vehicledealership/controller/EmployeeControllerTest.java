package za.ac.cput.vehicledealership.controller;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import za.ac.cput.vehicledealership.domain.Employee;
import za.ac.cput.vehicledealership.domain.Name;
import za.ac.cput.vehicledealership.factory.EmployeeFactory;
import za.ac.cput.vehicledealership.factory.NameFactory;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {


    private static Name name = NameFactory.createName("Mary", "", "Anne");
    private static Employee employee = EmployeeFactory.createEmployee(name, "mary@gmail.com", "P@ssword123");

    private final String BASE_URL = "http://localhost:8080/employee";
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(url, employee, Employee.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());

        Employee savedEmployee = postResponse.getBody();

        System.out.println("Saved data: " + savedEmployee);
        assertEquals(1, savedEmployee.getEmployeeNumber());
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + 1;
        System.out.println("URL: " + url);
        ResponseEntity<Employee> getResponse = restTemplate.getForEntity(url, Employee.class);
        Employee readEmployee = getResponse.getBody();
        assertEquals(1, readEmployee.getEmployeeNumber());

        System.out.println("Read: " + readEmployee);


    }

    @Test
    @Order(3)
    void update() {
        String url = BASE_URL + "/update";

        Name middleName = NameFactory.createName("Julian");

        Employee updateEmployee = new Employee.Builder()
                .copy(employee)
                .setName(middleName)
                .build();

        updateEmployee.setEmployeeNumber(1);

        System.out.println("URL: " + url);
        System.out.println("POST data: " + updateEmployee);
        ResponseEntity<Employee> response = restTemplate.postForEntity(url, updateEmployee, Employee.class);
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void delete() {
        String url = BASE_URL + "/delete/" + 1;
        System.out.println("URL: " + url);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // 204 No Content for successful deletion

    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/all";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("Show all");
        System.out.println(response);
        System.out.println(response.getBody());
    }


}