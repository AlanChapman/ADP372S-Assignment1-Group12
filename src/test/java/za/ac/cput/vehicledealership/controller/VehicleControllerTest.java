package za.ac.cput.vehicledealership.controller;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import za.ac.cput.vehicledealership.domain.*;
import za.ac.cput.vehicledealership.factory.VehicleFactory;
import za.ac.cput.vehicledealership.factory.NameFactory;
import za.ac.cput.vehicledealership.factory.VehicleFactory;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VehicleControllerTest {


    private static Vehicle vehicle = VehicleFactory.createVehicle("Audi", "A4", VehicleCondition.USED, FuelType.PETROL, BodyType.SEDAN,
            "White", 2019, 23000);

    private final String BASE_URL = "http://localhost:8080/vehicle";
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Vehicle> postResponse = restTemplate.postForEntity(url, vehicle, Vehicle.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());

        Vehicle savedVehicle = postResponse.getBody();

        System.out.println("Saved data: " + savedVehicle);
        assertNotNull(savedVehicle);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + 4;
        System.out.println("URL: " + url);
        ResponseEntity<Vehicle> getResponse = restTemplate.getForEntity(url, Vehicle.class);
        Vehicle readVehicle = getResponse.getBody();
        assertEquals(4, readVehicle.getVehicleId());

        System.out.println("Read: " + readVehicle);
    }

    @Test
    @Order(3)
    void update() {
        String url = BASE_URL + "/update";

        Vehicle updateVehicle = new Vehicle.Builder()
                .copy(vehicle)
                .setMileage(27544)
                .build();


        updateVehicle.setVehicleId(4);
        System.out.println("URL: " + url);
        System.out.println("POST data: " + updateVehicle);
        ResponseEntity<Vehicle> response = restTemplate.postForEntity(url, updateVehicle, Vehicle.class);
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void delete() {
        String url = BASE_URL + "/delete/" + 4;
        System.out.println("URL: " + url);
        restTemplate.delete(url);
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