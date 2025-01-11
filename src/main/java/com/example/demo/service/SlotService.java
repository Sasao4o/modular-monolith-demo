package com.example.demo.service;

import com.example.demo.model.SlotModel;
import com.example.demo.repository.SlotRepository;
import org.springframework.stereotype.Service;

import com.example.demo.model.SlotModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SlotService {
    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public SlotModel addSlot(Integer id, Date currentDate, boolean isReserved, double cost) {
        if (currentDate.before(new Date())) {
            throw new RuntimeException("Cannot add slot in the past");
        }
        List<SlotModel> slots = slotRepository.getAll();
        for (SlotModel oldSlot : slots) {
            long slotTimeInMillis = this.getSlotTime() * 60 * 1000;
            long diff = Math.abs(oldSlot.getTime().getTime() - currentDate.getTime());
            if (diff < slotTimeInMillis) {
                throw new RuntimeException("Slot Conflicts");
            }
        }
        SlotModel slot = new SlotModel(null, currentDate, isReserved, cost);
        SlotModel insertedSlot = slotRepository.insert(slot);
        return insertedSlot;
    }

    public int getSlotTime() {
        return 20;
    }
    public boolean bookSlot(Integer id) {
        SlotModel slot = slotRepository.getById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
        if (slot.isReserved()) {
            throw new RuntimeException("Slot already reserved");
        }
        slot.setReserved(true);
        slotRepository.update(id, slot);
        return true;
    }
    public SlotModel getSlotById(Integer id) {
        return slotRepository.getById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
    }

}
