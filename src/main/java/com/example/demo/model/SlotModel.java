package com.example.demo.model;


import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotModel {
    private Integer id;
    private Date time;
    private boolean isReserved;
    private double cost;


    public SlotModel(Integer id, Date time, boolean isReserved, double cost) {
        this.id = id;
        this.time = time;
        this.isReserved = isReserved;
        this.cost = cost;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
