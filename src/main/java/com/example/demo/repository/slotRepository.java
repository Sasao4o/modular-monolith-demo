package com.example.demo.repository;

import com.example.demo.model.SlotModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class slotRepository {
    private final List<SlotModel> slots = new ArrayList<>();
    private int currentId = 1;

    public SlotModel create(SlotModel slot) {
        slot.setId(currentId++);
        slots.add(slot);
        return slot;
    }


    public List<SlotModel> getAll() {
        return new ArrayList<>(slots);
    }
    
    public Optional<SlotModel> getById(int id) {
        return slots.stream().filter(slot -> slot.getId() == id).findFirst();
    }


    public Optional<SlotModel> update(int id, SlotModel updatedSlot) {
        Optional<SlotModel> existingSlot = getById(id);
        existingSlot.ifPresent(slot -> {
            slot.setTime(updatedSlot.getTime());
            slot.setReserved(updatedSlot.isReserved());
            slot.setCost(updatedSlot.getCost());
        });
        return existingSlot;
    }


    public boolean delete(int id) {
        return slots.removeIf(slot -> slot.getId() == id);
    }
}
