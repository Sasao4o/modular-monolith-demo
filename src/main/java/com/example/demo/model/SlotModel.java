package com.example.demo.model;


import lombok.Data;

import java.util.Date;

@Data
public class SlotModel {
    private Integer id;
    private Date time;
    private boolean isReserved;
    private double cost;

}
