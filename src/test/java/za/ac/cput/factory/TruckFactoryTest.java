/*  TruckFactoryTest.java
    Factory Test for Truck Entity
    Author: Alan Chapman (220092362)
    Date: 4 April 2023
*/
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Truck;

import static org.junit.jupiter.api.Assertions.*;

class TruckFactoryTest {
    @Test
    void testCreateTruckSuccess() {
        Truck truck = TruckFactory.createTruck(10, 13607.8);
        System.out.println(truck);
        assertNotNull(truck);
    }
}