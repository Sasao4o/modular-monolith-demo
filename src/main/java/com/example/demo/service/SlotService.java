package com.example.demo.service;

import com.example.demo.model.SlotModel;
import com.example.demo.repository.SlotRepository;
import org.springframework.stereotype.Service;

import com.example.demo.model.SlotModel;
@Service
public class SlotService {
    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public SlotModel addSlot(SlotModel slot) {
        SlotModel insertedSlot = slotRepository.insert(slot);
        return insertedSlot;
    }


}
