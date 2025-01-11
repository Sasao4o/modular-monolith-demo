package com.example.demo.doctorAvailability;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DoctorAvailabilitySlotTests {

    @Test
    void AddSlotTest() {
        Slot slot = new Slot(null, new Date(),true, 100.5);
        Slot addedSlot = slotService.addSlot(slot);
        addedSlot.setId(null);
        assertEquals(slot, addedSlot);
    }


}
