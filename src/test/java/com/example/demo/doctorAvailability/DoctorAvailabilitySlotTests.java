package com.example.demo.doctorAvailability;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.SlotModel;
import com.example.demo.service.SlotService;

@SpringBootTest
class DoctorAvailabilitySlotTests {
    @Autowired
    SlotService slotService;

    SlotModel createCurrentSlot() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        calendar.setTime(calendar.getTime());
        boolean isReserved = false;
        double cost = 100.5;
        return new SlotModel(null, calendar.getTime(), isReserved, cost);
    }

    SlotModel createSlot(int minutes) {
        minutes += 5;
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, minutes);
        boolean isReserved = false;
        double cost = 100.5;
        return new SlotModel(null, calendar.getTime(), isReserved, cost);
    }

    @BeforeEach
    void clearSlots() {
        slotService.clearSlots();
    }

    @Test
    void AddSlotTest() {
        SlotModel currentSlot = createCurrentSlot();
        SlotModel addedSlot = slotService.addSlot(currentSlot.getId(), currentSlot.getTime(), currentSlot.isReserved(),
                currentSlot.getCost());
        assert (addedSlot.getId() != null);
        assertEquals(addedSlot.getTime(), currentSlot.getTime());
        assertEquals(addedSlot.isReserved(), currentSlot.isReserved());
        assertEquals(addedSlot.getCost(), currentSlot.getCost());
    }

    @Test
    void AddConsecutiveSlot() {
        SlotModel dummySlot = createCurrentSlot();
        SlotModel dummySlot2 = createSlot(30);

        slotService.addSlot(dummySlot.getId(), dummySlot.getTime(), dummySlot.isReserved(), dummySlot.getCost());

        assertDoesNotThrow(() -> {
            slotService.addSlot(dummySlot2.getId(), dummySlot2.getTime(), dummySlot2.isReserved(),
                    dummySlot2.getCost());
        });
    }

    @Test
    void AddConflictingSlot() {
        SlotModel dummySlot = createCurrentSlot();
        SlotModel dummySlot2 = createSlot(15);

        slotService.addSlot(dummySlot.getId(), dummySlot.getTime(), dummySlot.isReserved(), dummySlot.getCost());
        assertThrows(Exception.class, () -> {
            slotService.addSlot(dummySlot2.getId(), dummySlot2.getTime(), dummySlot2.isReserved(),
                    dummySlot2.getCost());
        });
    }

    @Test
    void AddPastSlot() {
        SlotModel dummySlot = createSlot(-5);
        assertThrows(Exception.class, () -> {
            slotService.addSlot(dummySlot.getId(), dummySlot.getTime(), dummySlot.isReserved(), dummySlot.getCost());
        });
    }

    @Test
    void bookSlot() {
        SlotModel dummySlot = createCurrentSlot();
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
