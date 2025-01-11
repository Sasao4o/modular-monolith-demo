package com.example.demo.doctorAvailability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.SlotModel;
import com.example.demo.service.SlotService;

@SpringBootTest
class DoctorAvailabilitySlotTests {
    @Autowired
    SlotService slotService;

    SlotModel createDummySlot() {
        Date currentDate = new Date();
        boolean isReserved = false;
        double cost = 100.5;
        SlotModel slot = new SlotModel(null, currentDate, isReserved, cost);
        return slot;
    }

    @Test
    void AddSlotTest() {
        SlotModel currentSlot = createDummySlot();
        SlotModel addedSlot = slotService.addSlot(currentSlot.getId(), currentSlot.getTime(), currentSlot.isReserved(),
                currentSlot.getCost());
        assert (addedSlot.getId() != null);
        assertEquals(addedSlot.getTime(), currentSlot.getTime());
        assertEquals(addedSlot.isReserved(), currentSlot.isReserved());
        assertEquals(addedSlot.getCost(), currentSlot.getCost());
    }

    @Test
    void AddConsecutiveSlot() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, slotService.getSlotTime());
        Date futureDate = calendar.getTime();
        SlotModel dummySlot = createDummySlot();
        SlotModel dummySlot2 = createDummySlot();
        dummySlot.setTime(currentDate);
        dummySlot2.setTime((futureDate));

        slotService.addSlot(dummySlot.getId(), dummySlot.getTime(), dummySlot.isReserved(), dummySlot.getCost());
        slotService.addSlot(dummySlot2.getId(), dummySlot2.getTime(), dummySlot2.isReserved(), dummySlot2.getCost());

    }

    @Test
    void AddConflictingSlot() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, 1);
        Date futureDate = calendar.getTime();
        SlotModel dummySlot = createDummySlot();
        SlotModel dummySlot2 = createDummySlot();
        dummySlot.setTime(currentDate);
        dummySlot2.setTime((futureDate));

        slotService.addSlot(dummySlot.getId(), dummySlot.getTime(), dummySlot.isReserved(), dummySlot.getCost());
        assertThrows(Exception.class, () -> {
            slotService.addSlot(dummySlot2.getId(), dummySlot2.getTime(), dummySlot2.isReserved(),
                    dummySlot2.getCost());
        });
    }

    @Test
    void AddPastSlot() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, -1);
        Date pastDate = calendar.getTime();
        SlotModel dummySlot = createDummySlot();
        dummySlot.setTime(pastDate);
        assertThrows(Exception.class, () -> {
            slotService.addSlot(dummySlot.getId(), dummySlot.getTime(), dummySlot.isReserved(), dummySlot.getCost());
        });
    }

    @Test
    void bookSlot() {
        SlotModel dummySlot = createDummySlot();
        SlotModel addedSlot = slotService.addSlot(dummySlot.getId(), dummySlot.getTime(), dummySlot.isReserved(),
                dummySlot.getCost());
        slotService.bookSlot(addedSlot.getId());
        SlotModel bookedSlot = slotService.getSlotById(addedSlot.getId());
        assertTrue(bookedSlot.isReserved());
        assertThrows(Exception.class, () -> {
            slotService.bookSlot(addedSlot.getId());
        });
    }

}
