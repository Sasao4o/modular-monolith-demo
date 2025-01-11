package com.example.demo.doctorAvailability;

import com.example.demo.model.SlotModel;
import com.example.demo.service.SlotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DoctorAvailabilitySlotTests {
    @Autowired
    SlotService slotService;
    @Test
    void AddSlotTest() {
        SlotModel slot = new SlotModel();

        SlotModel addedSlot = slotService.addSlot(slot);
        addedSlot.setId(12);
        assertEquals(1,2);
        //Not Working
        assertEquals(slot, addedSlot);
    }


}
